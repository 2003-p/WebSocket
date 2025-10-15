package com.objetdirect.gwt.umlapi.server.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
// LinkedListも忘れずにな！
/**
 * WebSocketの通信拠点となるサーバーエンドポイントだ。
 * "/diagram" というアドレスでクライアントからの接続を待ち受けるぞ。
 */
@ServerEndpoint("/diagram")
public class DiagramSocketServer {

    // 接続中の全クライアントのセッションを管理するリスト
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static Map<String, String> documentStates = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("新しい接続: " + session.getId());
        sessions.add(session);
    }

 // DiagramSocketServer.java の onMessage メソッドをこれに置き換える
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("メッセージ受信 from " + session.getId() + ": " + message);

        try {
            // 簡単な文字列処理でJSONを解析する
            String action = getValue(message, "action");

            if ("textEditOT".equals(action)) {
                String elementId = getValue(message, "elementId");
                String partId = getValue(message, "partId");
                String originalText = getValue(message, "originalText");
                String newText = getValue(message, "newText");
                String docKey = elementId + "/" + partId;

                // サーバーに保存されている、このテキストの現在の状態を取得する
                String serverText = documentStates.getOrDefault(docKey, originalText);

                // --- ここからがdiff-match-patchの魔法だ！ ---
                DiffMatchPatch dmp = new DiffMatchPatch();
                // サーバーのテキストと、クライアントが編集した結果の差分からパッチ（変更指示書）を作成
                LinkedList<DiffMatchPatch.Patch> patches = dmp.patchMake(serverText, newText);
                // パッチをテキスト形式に変換する
                String patchText = dmp.patchToText(patches);

                // パッチをサーバーのテキストに適用して、新しい状態を計算
                Object[] results = dmp.patchApply(patches, serverText);
                String updatedServerText = (String) results[0];

                // サーバーの記憶を更新する
                documentStates.put(docKey, updatedServerText);

                // 他の全員に、適用すべきパッチをブロードキャストする！
                String broadcastMessage = "{\"action\":\"applyPatch\", \"elementId\":\"" + elementId + "\", \"partId\":\"" + partId + "\", \"patch\":\"" + patchText.replace("\"", "\\\"") + "\"}";
                broadcast(broadcastMessage, session);

            } else {
                // "sync" のような、他のアクションはそのままブロードキャスト
                broadcast(message, session);
            }

        } catch (Exception e) {
            e.printStackTrace();
            broadcast(message, session); // 何かエラーが起きても、とりあえずそのまま送っておく
        }
    }

    // JSON文字列から値を取り出す簡単なヘルパーメソッド（これも追加するんだ！）
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
    public void onClose(Session session) {
        System.out.println("接続が切れた: " + session.getId());
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        System.err.println("エラーが発生しました: " + error.getMessage());
        error.printStackTrace();
    }

    // 全員にメッセージを送信するためのヘルパーメソッド
    private void broadcast(String message, Session fromSession) {
        for (Session session : sessions) {
            if (session.isOpen() && !session.equals(fromSession)) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}