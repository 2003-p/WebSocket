package com.objetdirect.gwt.umldrawer.client.helpers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
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
    public native void disconnect() /*-{
        if (this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws) {
            this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws.close();
            this.@com.objetdirect.gwt.umldrawer.client.helpers.WebSocketClient::ws = null;
        }
    }-*/;
 // WebSocketClient.java の onMessage メソッドをこれに置き換える

 // WebSocketClient.java の onMessage メソッドをこれに置き換える
 // ▼▼▼ onMessage メソッド全体を、この正しいコードに置き換える ▼▼▼

    public void onMessage(String message) {
        try {
            JSONValue jsonValue = JSONParser.parseStrict(message);
            JSONObject jsonObject = jsonValue.isObject();

            if (jsonObject != null && jsonObject.containsKey("action")) {

                // --- 1. 送信者IDのチェック ---
                // メッセージに "studentId" が含まれているか確認
                if (!jsonObject.containsKey("studentId")) {
                    return; // studentId がないメッセージは処理しない
                }
                String messageStudentId = jsonObject.get("studentId").isString().stringValue();
                
                // ★★★ 解決策：自分のID (Session.studentId) と比較 ★★★
                if (messageStudentId.equals(Session.studentId)) {
                    // 自分自身が送信したメッセージなので、何もしないで終了
                    // (ローカルでの変更は 'drop' 時に既に行われているため)
                    return; 
                }

                // --- 2. 他人からのメッセージのみ処理 ---
                String action = jsonObject.get("action").isString().stringValue();

                if ("move".equals(action)) {
                    String elementId = jsonObject.get("elementId").isString().stringValue();
                    int x = (int) jsonObject.get("x").isNumber().doubleValue();
                    int y = (int) jsonObject.get("y").isNumber().doubleValue();

                    if (drawerPanel != null) {
                        drawerPanel.moveArtifactById(elementId, x, y);
                    }

                } else if ("editText".equals(action)) {
                    String elementId = jsonObject.get("elementId").isString().stringValue();
                    String partId = jsonObject.get("partId").isString().stringValue();
                    String newText = jsonObject.get("newText").isString().stringValue(); 
                    
                    if (drawerPanel != null) {
                        drawerPanel.applyPatchToArtifactText(elementId, partId, newText);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("WebSocket onMessage Error: " + e.getMessage());
        }
    }
}