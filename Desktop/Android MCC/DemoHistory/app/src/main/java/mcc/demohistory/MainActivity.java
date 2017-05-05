package mcc.demohistory;

import android.database.Cursor;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


import android.content.Intent;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    TextView view1;
    List<String> title;
    ListView lv;
    private String TAG="demo";
    ConnectServer cS = new ConnectServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = (TextView) findViewById(R.id.textView);
        title = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv_search);
        fetchHistory("default");

    }
    public void getHistory(View view){
        view1.setText(null);
        fetchHistory("get history");
    }

    public void clearHistory(View view){
        //Disabled so that we do not delete history mistakenly
        //Browser.clearHistory(getContentResolver());
        view1.setText(null);
        fetchHistory("clear history");
    }

    void fetchHistory(String v1){
        //view1.append(v1);
        String[] projection = new String[] {Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL,Browser.BookmarkColumns.DATE,
                Browser.BookmarkColumns.VISITS};

        Cursor mCur = managedQuery(android.provider.Browser.BOOKMARKS_URI,
                projection, Browser.BookmarkColumns.BOOKMARK + " = 0", null, Browser.BookmarkColumns.VISITS + " DESC"
        );

        mCur.moveToFirst();
        Log.d(TAG,mCur.toString());
        int titleIdx = mCur.getColumnIndex(Browser.BookmarkColumns.TITLE);
        int urlIdx = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
        int dateIdx = mCur.getColumnIndex(Browser.BookmarkColumns.DATE);
        int visitsIdx = mCur.getColumnIndex(Browser.BookmarkColumns.VISITS);
        int i=0;
        while (mCur.isAfterLast() == false) {
            Date date = new Date(Long.parseLong(mCur.getString(dateIdx)));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            format.setTimeZone(TimeZone.getTimeZone("EST"));
            String formatted = format.format(date);
            title.add(formatted);
            title.add(mCur.getString(titleIdx));
             view1.append(i + ". " + "\n" + mCur.getString(titleIdx));
            view1.append("\n" + mCur.getString(urlIdx));
            view1.append("\n" + mCur.getString(dateIdx));
            view1.append("\n" + mCur.getString(visitsIdx) + "\n");
            mCur.moveToNext();
            i++;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                title );

        lv.setAdapter(arrayAdapter);
    }
    public void conServer(View view){

        Toast.makeText(this,"Connection Started",Toast.LENGTH_LONG).show();
        cS.connectWebSocket();
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        //mWebSocketClient.send("HI! There");
    }

    //Method to start the service
    public void startService(View view)
    {
        Log.d(TAG,"Going to start the service now");
        startService(new Intent(getBaseContext(),Hdeamon.class));

    }
    //Method to stop the service
     public void stopService(View view)
     {
         stopService(new Intent(getBaseContext(),Hdeamon.class));
     }

    /*public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){
            final ChatMessage chatMessage =(ChatMessage) intent.getExtras().get("chatMessage");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Main","Thread running");
                }
            });
        }
    }*/




}