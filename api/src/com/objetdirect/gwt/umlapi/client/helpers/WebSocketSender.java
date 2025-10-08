package com.objetdirect.gwt.umlapi.client.helpers;

/**
 * サーバーにメッセージを送る、という責務だけを定義した"契約書"だ。
 * drawerプロジェクトが、この契約を実装することになる。
 */
public interface WebSocketSender {
    void send(String message);
}