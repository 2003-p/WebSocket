package com.objetdirect.gwt.umldrawer.client.helpers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.objetdirect.gwt.umldrawer.client.DrawerPanel;


public class WebSocketClient {

    private JavaScriptObject ws;
    private DrawerPanel drawerPanel;

    public WebSocketClient(DrawerPanel panel) {
        this.drawerPanel = panel;
    }

    public native void connect(String url) /*-{
        var self = this;
        var ws = new WebSocket(url);

        ws.onopen = function() {
            console.log("WebSocket connection opened.");
        };

        ws.onmessage = function(event) {
            // Javaの onMessage メソッドを呼び出す
            self.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::onMessage(Ljava/lang/String;)(event.data);
        };

        ws.onclose = function() {
            console.log("WebSocket connection closed.");
        };

        ws.onerror = function(error) {
            console.error("WebSocket Error: ", error);
        };

        this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws = ws;
    }-*/;

    public native void send(String message) /*-{
        if (this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws &&
            this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws.readyState === WebSocket.OPEN) {
            this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws.send(message);
        } else {
            console.error("WebSocket is not connected.");
        }
    }-*/;
 // WebSocketClient.java の onMessage メソッドをこれに置き換える

    public void onMessage(String message) {
        try {
            JSONValue jsonValue = JSONParser.parseStrict(message);
            JSONObject jsonObject = jsonValue.isObject();

            if (jsonObject != null && jsonObject.containsKey("action")) {
                String action = jsonObject.get("action").isString().stringValue();

                if ("sync".equals(action)) {
                    String url = jsonObject.get("url").isString().stringValue();
                    // DrawerPanelからDrawerBaseへの参照を取得し、syncCanvasFromServerを呼び出す！
                    if (drawerPanel != null && drawerPanel.getDrawerBaseInstance()  != null) {
                         drawerPanel.getDrawerBaseInstance() .syncCanvasFromServer(url);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("受信したメッセージの解析に失敗: " + message);
        }
    }
}