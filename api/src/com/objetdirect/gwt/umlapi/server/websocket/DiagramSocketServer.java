package com.objetdirect.gwt.umlapi.server.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
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

/**
 * 演習ID（{exerciseId}）ごとに部屋を管理する、純粋なブロードキャストサーバー
 */
@ServerEndpoint("/diagram/{exerciseId}")
public class DiagramSocketServer {

    // 演習IDごとのセッションを管理する"宿屋"
    private static Map<String, Set<Session>> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("新しい接続: " + session.getId() + " が演習 '" + exerciseId + "' に参加しました。");
        rooms.computeIfAbsent(exerciseId, key -> Collections.synchronizedSet(new HashSet<>())).add(session);
    }

    /**
     * メッセージを受け取ったら、何も解析せず、
     * そのまま同じ演習室の自分以外全員にブロードキャストする
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("メッセージ受信 from " + session.getId() + " in 演習 '" + exerciseId + "'");
        broadcast(message, session, exerciseId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("exerciseId") String exerciseId) {
        System.out.println("接続が切れた: " + session.getId() + " が演習 '" + exerciseId + "' から退出しました。");
        Set<Session> room = rooms.get(exerciseId);
        if (room != null) {
            room.remove(session);
            if (room.isEmpty()) {
                rooms.remove(exerciseId);
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    /**
     * 同じ演習室の、送信者以外の全員にメッセージを送る
     */
    private void broadcast(String message, Session fromSession, String exerciseId) {
        Set<Session> room = rooms.get(exerciseId);
        if (room != null) {
            // ブロードキャスト中にSetが変更される可能性に備え、スナップショットをコピーする
            Set<Session> roomSnapshot = new HashSet<>(room);
            for (Session s : roomSnapshot) {
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