package com.objetdirect.gwt.umlapi.server.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocketの通信拠点となるサーバーエンドポイントだ。
 * "/diagram" というアドレスでクライアントからの接続を待ち受けるぞ。
 */
@ServerEndpoint("/diagram")
public class DiagramSocketServer {

    // 接続中の全クライアントのセッションを管理するリスト
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("新しい接続: " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("メッセージ受信 from " + session.getId() + ": " + message);
        // 受け取ったメッセージを、送信者以外の全員にブロードキャストする
        broadcast(message, session);
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