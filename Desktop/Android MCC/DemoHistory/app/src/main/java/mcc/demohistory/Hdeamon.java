package mcc.demohistory;
import java.io.BufferedReader;
import android.util.Log;
import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.widget.Toast;
import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Hdeamon extends Service {

    private String TAG="Custom_Service";
    /** indicates how to behave if the service is killed */
    int mStartMode;
    /** interface for clients that bind */
    IBinder mBinder;
    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    private BufferedReader socketInput;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
    Log.d("Custom_Service","Service Created");
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Service started..");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG,"called");
        URI uri=null;
        try {
            //give the websocket address here
            uri = new URI("http://172.31.64.255:3000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSocketClient mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake data) {
                Log.i("Websocket", "Opened");
                //mWebSocketClient.send("Hello from ");
                send("hello");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                Log.d(TAG, "message recieved");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/
            }
            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }
            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
        Toast.makeText(this, "Connected to the server", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }


    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Service_stopped");
    }
}
