package com.wangyuchao.a1500310106wyc_androidsy.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wangyuchao.a1500310106wyc_androidsy.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditText;
    private TextView mTextView;
    private Button connect,send,disconnect;
    private static final String TAG = "TAG";
    private static final String HOST = "169.254.120.137";
    private static final int PORT = 9999;
    private PrintWriter printWriter;
    private BufferedReader in;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        connect = (Button)findViewById(R.id.connect);
        send = (Button)findViewById(R.id.send);
        disconnect = (Button)findViewById(R.id.disconnect);
        connect.setOnClickListener(this);
        send.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect:
                connect(v);
            case R.id.send:
                send(v);
            case R.id.disconnect:
                disconnect(v);
                default:break;
        }
    }

    public void connect(View view) {
        mExecutorService.execute(new connectService());
    }

    public void send(View view) {
        String sendMsg = mEditText.getText().toString();
        mExecutorService.execute(new sendService(sendMsg));
    }

    public void disconnect(View view) {
        mExecutorService.execute(new sendService("0"));
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if(printWriter != null){
                printWriter.println(this.msg);
            }
        }
    }

    private class connectService implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(HOST, PORT);
                socket.setSoTimeout(60000);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "UTF-8")), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                receiveMsg();
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));
            }
        }
    }

    private void receiveMsg() {
        try {
            while (true) {
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(receiveMsg + "\n\n" + mTextView.getText());
                        }
                    });
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            e.printStackTrace();
        }
    }
}