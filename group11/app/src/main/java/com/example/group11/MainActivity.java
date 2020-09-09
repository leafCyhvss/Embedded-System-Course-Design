package com.example.group11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanheng.a53.seg7.Seg7Class;
import com.hanheng.a53.led.LedClass;
import com.hanheng.a53.beep.BeepClass;
import com.hanheng.a53.dotarray.FontClass;
import com.hanheng.a53.dip.DipClass;

public class MainActivity extends AppCompatActivity {
    public static final int BEEP_ON = 0;
    public static final int BEEP_OFF = 1;
    public static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int err = Seg7Class.Init();
        System.out.println("测试:"+err);
        LedClass.Init();
        BeepClass.Init();
        FontClass.getInstance();
        DipClass.Init();
        final AssetManager am = this.getAssets();
        Button btn1 = (Button) findViewById(R.id.button_main);
        Button btn2=(Button)findViewById(R.id.button_g_s);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i= new Intent(MainActivity.this , air_conditioner.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent(MainActivity.this , seat.class);
                startActivity(i);
            }
        });
    }
}