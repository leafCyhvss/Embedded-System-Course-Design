package com.example.group11;


import com.hanheng.a53.dip.DipClass;
import com.hanheng.a53.led.LedClass;
import com.hanheng.a53.seg7.Seg7Class;
import com.example.*;

public class AutoCtl extends Thread{
    public float a=0;
    public int speed,angle,visiablity;
    public boolean flag_auto;
    public int segshow=1111;
    //public int auto_open;

    public void updateText(final int arg){
        new Thread(new Runnable() {
            public void run() {
                Seg7Class.Seg7Show(arg);
            }
        }).start();
    }

    public void run()
    {
        segshow=1111;
        updateText(segshow);
        while(flag_auto){
            ChangeStates();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //auto_open=0;
        return;
    }

    public void ChangeStates()
    {
        if(speed<=50)
        {
//			LedClass.IoctlLed(2, 1);
//			LedClass.IoctlLed(3, 0);
            //Seg7Class.Seg7Show(a);
            if((segshow%1000)/100==1)
            {
                segshow+=700;
                //updateText(segshow);
            }


            if(segshow/1000==8)
            {
                segshow-=7000;
                //updateText(segshow);
            }

        }
        else
        {
            if(speed>50 && speed<=100)
            {
//				LedClass.IoctlLed(2, 1);
//				LedClass.IoctlLed(3, 0);

                if((segshow%1000)/100==1)
                    segshow+=700;
                //updateText(segshow);

                if(segshow/1000==1)
                    segshow+=7000;
                //updateText(segshow);

            }

            else
            {
                if(speed>100)
                {
//					LedClass.IoctlLed(2, 1);
//					LedClass.IoctlLed(3, 1);

                    if((segshow%1000)/100==1)
                        segshow+=700;
                    //updateText(segshow);

                    if(segshow/1000==1)
                        segshow+=7000;
                    //updateText(segshow);
                };
            }

        }


        if(visiablity<100)
        {
//			LedClass.IoctlLed(1, 1);

            if(segshow%100/10==1)
                segshow+=70;
            //updateText(segshow);
        }
        else
        {
//			LedClass.IoctlLed(1, 0);
            if(segshow%100/10==8)
                segshow-=70;
            //updateText(segshow);
        }



        if(angle>120)
        {
//			LedClass.IoctlLed(0, 1);
            if(segshow%10==8)
                segshow-=7;
            //updateText(segshow);
        }

        else if(angle<60)
        {
//            LedClass.IoctlLed(0, 0);

            if(segshow%10==1)
                segshow+=7;
            //updateText(segshow);

        }
        else if(angle>=60 && angle<=120)
        {
//            LedClass.IoctlLed(0, 0);

            if(segshow%10==8)
                segshow-=7;

        }
        updateText(segshow);
    }

}

