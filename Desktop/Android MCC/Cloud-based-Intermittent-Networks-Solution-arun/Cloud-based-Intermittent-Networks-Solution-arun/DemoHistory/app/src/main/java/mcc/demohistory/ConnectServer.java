package mcc.demohistory;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import mcc.demohistory.MainActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by aruno on 5/5/2017.
 */

public class ConnectServer {

    public ConnectServer() {

    }
    private WebSocketClient mWebSocketClient;

    public void connectWebSocket() {
        URI uri;

        try {
            uri = new URI("ws://10.0.2.2:9060");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

         mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.d("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                Log.d("Websocket", "Message Received is " + s);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView textView = (TextView) findViewById(R.id.messages);
//                        textView.setText(textView.getText() + "\n" + message);
//                    }
//                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}
