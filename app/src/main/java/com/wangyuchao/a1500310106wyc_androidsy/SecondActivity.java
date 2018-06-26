package com.wangyuchao.a1500310106wyc_androidsy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return","Hello TestActivity");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello TestActivity");
        setResult(RESULT_OK,intent);
        finish();
    }
}
