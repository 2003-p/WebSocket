package com.objetdirect.gwt.umlapi.server.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList; // 忘れずに追加
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch; // 忘れずに追加

@ServerEndpoint("/diagram/{exerciseId}")
public class DiagramSocketServer {

    // 演習IDごとのセッションを管理するMap
    private static Map<String, Set<Session>> rooms = new ConcurrentHashMap<>();
    
    // ★★変更点★★: ドキュメントの状態も演習IDごとに管理する必要がある
    // Map<演習ID, Map<ドキュメントキー, テキスト内容>> という構造にする
    private static Map<String, Map<String, String>> roomDocumentStates = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("新しい接続: " + session.getId() + " が演習 '" + exerciseId + "' に参加しました。");

        // 演習IDに対応する部屋を取得、なければ新しく作る
        rooms.computeIfAbsent(exerciseId, key -> Collections.synchronizedSet(new HashSet<>())).add(session);
        // ドキュメント保存用のMapもなければ新しく作る
        roomDocumentStates.computeIfAbsent(exerciseId, key -> new ConcurrentHashMap<>());
    }

    // ★★変更点★★: 1つ目のコードのロジックをここに統合
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("メッセージ受信 from " + session.getId() + " in 演習 '" + exerciseId + "': " + message);

        try {
            String action = getValue(message, "action");

            if ("textEditOT".equals(action)) {
                String elementId = getValue(message, "elementId");
                String partId = getValue(message, "partId");
                String originalText = getValue(message, "originalText");
                String newText = getValue(message, "newText");
                String docKey = elementId + "/" + partId;
                
                // ★★変更点★★: 演習IDに対応するドキュメント状態マップを取得
                Map<String, String> documentStates = roomDocumentStates.get(exerciseId);

                // サーバーに保存されている、このテキストの現在の状態を取得する
                String serverText = documentStates.getOrDefault(docKey, originalText);

                DiffMatchPatch dmp = new DiffMatchPatch();
                LinkedList<DiffMatchPatch.Patch> patches = dmp.patchMake(serverText, newText);
                String patchText = dmp.patchToText(patches);

                Object[] results = dmp.patchApply(patches, serverText);
                String updatedServerText = (String) results[0];

                // サーバーの記憶を更新する
                documentStates.put(docKey, updatedServerText);

                // 他の全員に、適用すべきパッチをブロードキャストする
                String broadcastMessage = "{\"action\":\"applyPatch\", \"elementId\":\"" + elementId + "\", \"partId\":\"" + partId + "\", \"patch\":\"" + patchText.replace("\"", "\\\"") + "\"}";
                broadcast(broadcastMessage, session, exerciseId);

            } else {
                // 他のアクションはそのまま同じ演習内のメンバーにブロードキャスト
                broadcast(message, session, exerciseId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 何かエラーが起きても、とりあえずそのまま送っておく
            broadcast(message, session, exerciseId); 
        }
    }

    // ★★追加★★: 1つ目のコードから持ってくる
    private String getValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int start = json.indexOf(searchKey);
        if (start == -1) return null;
        start += searchKey.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return null;
        return json.substring(start, end).replace("\\\"", "\"");
    }

    @OnClose
    public void onClose(Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("接続が切れた: " + session.getId() + " が演習 '" + exerciseId + "' から退出しました。");

        Set<Session> room = rooms.get(exerciseId);
        if (room != null) {
            room.remove(session);
            if (room.isEmpty()) {
                rooms.remove(exerciseId);
                // ★★変更点★★: 部屋が空になったらドキュメントの状態も削除
                roomDocumentStates.remove(exerciseId);
                System.out.println("演習 '" + exerciseId + "' の部屋とデータは削除されました。");
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
    
    private void broadcast(String message, Session fromSession, String exerciseId) {
        Set<Session> room = rooms.get(exerciseId);
        if (room != null) {
            for (Session s : room) {
                if (s.isOpen() && !s.equals(fromSession)) {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}