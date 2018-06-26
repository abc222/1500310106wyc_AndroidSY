package com.wangyuchao.a1500310106wyc_androidsy;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPersistenceActivity extends AppCompatActivity implements View.OnClickListener{

    EditText text1;
    EditText text2;
    EditText text3;
    EditText text4;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    Date date;
    String str;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_persistence);
        Button button1 = (Button)findViewById(R.id.save_Data1);
        Button button2 = (Button)findViewById(R.id.restore_data1);
        Button button3 = (Button)findViewById(R.id.save_Data2);
        Button button4 = (Button)findViewById(R.id.restore_data2);
        Button button5 = (Button)findViewById(R.id.save_Data3);
        Button button6 = (Button)findViewById(R.id.restore_data3);
        Button button7 = (Button)findViewById(R.id.save_Data4);
        Button button8 = (Button)findViewById(R.id.restore_data4);
        text1 = (EditText)findViewById(R.id.text1);
        text2 = (EditText)findViewById(R.id.text2);
        text3 = (EditText)findViewById(R.id.text3);
        text4 = (EditText)findViewById(R.id.text4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        dbHelper = new MyDatabaseHelper(this,"TimeStore.db",null,1);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.save_Data1:
                date = new Date(System.currentTimeMillis());
                str = simpleDateFormat.format(date);
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("time",str);
                editor.commit();
                break;
            case R.id.restore_data1:
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String time = pref.getString("time","");
                text1.setText(time);
                break;
            case R.id.save_Data2:
                date = new Date(System.currentTimeMillis());
                str = simpleDateFormat.format(date);
                FileOutputStream out = null;
                BufferedWriter writer = null;
                try{
                    out = openFileOutput("data", Context.MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(str);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    try{
                        if(writer != null)
                            writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.restore_data2:
                FileInputStream in = null;
                BufferedReader reader = null;
                StringBuilder content = new StringBuilder();
                try{
                    in = openFileInput("data");
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while((line = reader.readLine()) != null)
                        content.append(line);
                    text2.setText(content.toString());
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.save_Data3:
                break;
            case R.id.restore_data3:
                break;
            case R.id.save_Data4:
                date = new Date(System.currentTimeMillis());
                str = simpleDateFormat.format(date);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("time",str);
                db.insert("Time",null,values);
                break;
            case R.id.restore_data4:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                Cursor cursor = db2.query("Time",null,null,
                        null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String tt = cursor.getString(cursor.getColumnIndex("time"));
                        text4.setText(tt);
                    }while(cursor.moveToNext());
                }
                cursor.close();
                break;
                default:
                    break;
        }

    }
}
