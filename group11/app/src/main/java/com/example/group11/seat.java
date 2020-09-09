package com.example.group11;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;


import com.hanheng.a53.beep.BeepClass;
import com.hanheng.a53.dip.DipClass;
import com.hanheng.a53.dotarray.FontClass;
import com.hanheng.a53.led.LedClass;
import com.hanheng.a53.seg7.Seg7Class;

public class seat extends Activity {

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    private TextView text01;
    private TextView text02;
    private TextView text03;
    private TextView text04;
    private int stated=3;
    private int pre_i=0;

    private int dir_count=0;//初始方向设为北
    private int turn=0;
    private String str_dir;

    AssetManager amd;
    private boolean flag;

    public AutoCtl autocontrol;
    public int segshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        initView();
        amd = this.getAssets();

        LedClass.Init();
        Seg7Class.Init();
        DipClass.Init();
        BeepClass.Init();
        FontClass.getInstance();
        FontClass.getInstance().setContent("北",amd);
    }

    private boolean auto_flag;
    private int auto_open;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void initView(){

        text1=(TextView)findViewById(R.id.textView1);
        text2=(TextView)findViewById(R.id.textView2);
        text3=(TextView)findViewById(R.id.textView3);
        text4=(TextView)findViewById(R.id.textView4);

        text01=(TextView)findViewById(R.id.textView01);
        text02=(TextView)findViewById(R.id.textView02);
        text03=(TextView)findViewById(R.id.textView03);
        text04=(TextView)findViewById(R.id.textView04);

        auto_open=0;
        segshow=1111;
        openThread();
    }

    public String dir_change(int dir_count2,int turn){
        String dir ="北";
        Log.i("new_dir_count", String.valueOf(dir_count));
        int new_dir=(dir_count2+turn)%4;
        Log.i("new_dir", String.valueOf(new_dir));
        dir_count=new_dir;
        Log.i("new_dir_count2", String.valueOf(dir_count));
        switch (dir_count){
            case 0:
                dir="北";
                break;
            case 1:
                dir="东";
                break;
            case 2:
                dir="南";
                break;
            case 3:
                dir="西";
                break;
            default:
                dir="北";
                break;
        }
        return dir;
    }


    public String addZero(int b){
        String val = Integer.toBinaryString(b&0xFF);
        String str="";
        if(val.length()<8){
            for(int i=0;i<8-val.length();i++){
                str+=0;
            }
            return str+=val;
        }
        return val;
    }


    public void computed(int val){
        String str=addZero(val);
        if (val!=stated)
        {
            char[] cr=str.toCharArray();
            int tag;
            int j=0;
            for(int i=0;i<cr.length;i++){
                if(cr[i]=='1')
                {
                    tag=1;
                    changeState(i,tag);
                    pre_i=i;
                    break;
                }
                if (cr[i]=='0'&& i==pre_i&&j==0) {
                        tag = 0;
                        changeState(i, tag);
                        j+=1;
                }
            }
            stated=val;
        }
    }

    public void changeState(int i,int tag){
        if(tag==1)
        {
            switch (i) {
                case 0:
                    LedClass.IoctlLed(i, 1);
                    text01.setText("ON");
                    break;
                case 1:
                    LedClass.IoctlLed(i, 1);
                    text02.setText("ON");
                    break;
                case 2:
                    LedClass.IoctlLed(i, 1);
                    text03.setText("ON");
                    break;
                case 3:
                    LedClass.IoctlLed(i, 1);
                    text04.setText("ON");
                    break;
                case 4:
                    LedClass.IoctlLed(i-4, 1);
                    FontClass.getInstance().setContent("直行",amd);
                    break;
                case 5:
                    LedClass.IoctlLed(i-4, 1);
                    FontClass.getInstance().setContent("左转",amd);
                    break;
                case 6:
                    LedClass.IoctlLed(i-4, 1);
                    FontClass.getInstance().setContent("右转",amd);
                    break;
                case 7:
                    LedClass.IoctlLed(i-4, 1);
                    FontClass.getInstance().setContent("倒车",amd);
                    break;
                default:
                    break;
            }
        }
        else{
            switch (i) {
                case 0:
                    LedClass.IoctlLed(i, 0);
                    text01.setText("OFF");
                    break;
                case 1:
                    LedClass.IoctlLed(i, 0);
                    text02.setText("OFF");
                    break;
                case 2:
                    LedClass.IoctlLed(i, 0);
                    text03.setText("OFF");
                    break;
                case 3:
                    LedClass.IoctlLed(i, 0);
                    text04.setText("OFF");
                    break;
                case 4:
                    turn=0;
                    LedClass.IoctlLed(i-4, 0);
                    str_dir=dir_change(dir_count,turn);
                    FontClass.getInstance().setContent(str_dir,amd);
                    break;
                case 5:
                    turn=3;
                    LedClass.IoctlLed(i-4, 0);
                    str_dir=dir_change(dir_count,turn);
                    FontClass.getInstance().setContent(str_dir,amd);
                    break;
                case 6:
                    turn=1;
                    LedClass.IoctlLed(i-4, 0);
                    str_dir=dir_change(dir_count,turn);
                    FontClass.getInstance().setContent(str_dir,amd);
                    break;
                case 7:
                    turn=0;
                    LedClass.IoctlLed(i-4, 0);
                    str_dir=dir_change(dir_count,turn);
                    FontClass.getInstance().setContent(str_dir,amd);
                    break;

                default:
                    break;
            }
        }
    }

    private Handler uiHandler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                Log.i("????????", ""+msg.arg1);
                computed(msg.arg1);

            }
        }
    };

    public void openThread(){

            MyThread thread=new MyThread();
            this.flag=true;
            thread.start();
    }

    class MyThread extends Thread{
        public void run(){
            while(flag){
                Message msgMessage=new Message();
                int value=DipClass.ReadValue();
                msgMessage.what=1;
                msgMessage.arg1=value;
                uiHandler.sendMessage(msgMessage);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}