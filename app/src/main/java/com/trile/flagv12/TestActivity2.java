package com.trile.flagv12;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

public class TestActivity2 extends Activity {

    ImageButton A,B,C,D;
    TextView q;
    int IDA,IDB,IDC,IDD;
    ImageButton btnx;
    data db;
    SQLiteDatabase database;
    Animation dia;
    ArrayList<String> ans=new ArrayList<>();
    ArrayList<Integer> ID=new ArrayList<>();
    ArrayList<byte[]> hinh=new ArrayList<>();
    ArrayList<Integer> p = new ArrayList<>();
    ArrayList<Integer> already;
    ArrayList<Integer> IDscore = new ArrayList<>();
    ArrayList<Integer> fin = new ArrayList<>();
    int number=0,kq=0,hour,minute,second,date,month,sl;
    pl.droidsonroids.gif.GifImageView progress,chooseA,chooseB,chooseC,chooseD;
    CountDownTimer change,delay;
    FrameLayout fA,fB,fC,fD;
    LinearLayout loutfin;
    int ktA = 0,ktB=0,ktC=0,ktD=0;
    String level;
    Typeface chalk,chalk1;
    Boolean twoclick=false;
    MediaPlayer test,e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        AnhXa();

        A.setBackgroundColor(Color.TRANSPARENT);
        B.setBackgroundColor(Color.TRANSPARENT);
        C.setBackgroundColor(Color.TRANSPARENT);
        D.setBackgroundColor(Color.TRANSPARENT);

        //nhac
        p.add(R.raw.test1);
        p.add(R.raw.test2);
        p.add(R.raw.test3);
        p.add(R.raw.test4);


        //them nhac
        Random ranmusic = new Random();
        int pos = ranmusic.nextInt(p.size());
        test = MediaPlayer.create(this,p.get(pos));
        test.start();
        test.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                test.start();
            }
        });

        //animation
        dia = AnimationUtils.loadAnimation(this,R.anim.dialog);


        //set thoi gian sau khi chon dap an man hinh dung lai xiu
        delay = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                display();
            }
        };

        //set thoi gian cho chuyen cau hoi
        change= new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() { //neu chay het thoi gian ma chua chon thi show dap an va chuyen cau
                if (IDA==number)
                {
                    check(fB, fC, fD);
                }

                else if (IDC==number)
                {
                    check(fB,fD,fA);
                }

                else if (IDB==number)
                {
                    check(fD, fC, fA);
                }

                else if(IDD==number)
                {
                    check(fB,fC,fA);
                }
                delay.start();
            }
        };

        //set font
        chalk = Typeface.createFromAsset(getAssets(),"font/chalk1.ttf");

        chalk1 = Typeface.createFromAsset(getAssets(),"font/PWChalk.ttf");
        q.setTypeface(chalk1);


        db = new data(this);
        database = db.getdb();

        readdb();

        Calendar c = Calendar.getInstance();
        hour=c.get(Calendar.HOUR);
        minute=c.get(Calendar.MINUTE);
        second=c.get(Calendar.SECOND);
        date=c.get(Calendar.DATE);
        month=c.get(Calendar.MONTH);



        display();


    }

    @Override
    public void onBackPressed() {
        if (twoclick) {
            super.onBackPressed();
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);

            startActivity(exit);
        }

        e.stop();
        test.stop();
        twoclick =true;
        Toast.makeText(this ,"please click BACK again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twoclick=false; //qua hai giay reset lai twoclick
            }
        },2000);
    }

    private void AnhXa() {
        A=(ImageButton) findViewById(R.id.btnA);
        B=(ImageButton) findViewById(R.id.btnB);
        C=(ImageButton) findViewById(R.id.btnC);
        D=(ImageButton) findViewById(R.id.btnD);
        q=(TextView) findViewById(R.id.img);
        btnx = (ImageButton) findViewById(R.id.btncancel);
        progress = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.progress) ;
        chooseA = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.chooseA) ;
        chooseB = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.chooseB) ;
        chooseC = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.chooseC) ;
        chooseD = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.chooseD) ;
        fA = (FrameLayout) findViewById(R.id.flbtnA) ;
        fB = (FrameLayout) findViewById(R.id.flbtnB) ;
        fC = (FrameLayout) findViewById(R.id.flbtnC) ;
        fD = (FrameLayout) findViewById(R.id.flbtnD) ;

    }


    void readdb()
    {
        Intent caller = getIntent();
        Bundle fromcaller = caller.getBundleExtra("package");
        String query = fromcaller.getString("query");
        sl = fromcaller.getInt("sl");

        if (query.compareTo("select * from easy")==0) level = "easy";
        else if (query.compareTo("select * from medium")==0) level = "medium";
        else level = "hard";
        Cursor select = database.rawQuery(query,null);

        int dem=0;
        while (select.moveToNext())
        {
            ans.add(dem,select.getString(1));
            ID.add(dem,select.getInt(0));
            hinh.add(dem,select.getBlob(2));
        }
    }

    void display()
    {
        byte[] hinhanswer;
        Bitmap bitmap;
        Animation run = AnimationUtils.loadAnimation(this,R.anim.progress);
        progress.setAnimation(run);
        progress.startAnimation(run);

        //xoa hieu ung tren answer
        fA.clearAnimation();
        fB.clearAnimation();
        fC.clearAnimation();
        fD.clearAnimation();

        //xoa het mui ten
        chooseA.setBackgroundColor(Color.TRANSPARENT);
        chooseC.setBackgroundColor(Color.TRANSPARENT);
        chooseB.setBackgroundColor(Color.TRANSPARENT);
        chooseD.setBackgroundColor(Color.TRANSPARENT);

        Random r = new Random();

        //neu cau hoi du sl thi dung game
        if (fin.size()==sl)
            endgame();

        //kiem tra id co trung trong 4 cau duoc xuat ra hay khong
        int chck;
        do {
            chck=0;
            //random cau id cau dung
            number = r.nextInt(ID.size());
            if(!fin.contains(number))
            {
                fin.add(number);
                chck=1;
            }
        }while(chck==0);

        //mang chua 4 cau trong mot round
        already = new ArrayList<>();

        already.add(number);

        //set cau hoi
        q.setText(ans.get(number));

        //them id cua 3 cau con lai
        for (int i = 0 ; i<3 ;)
        {
            //random 3 cau con lai
            int other = r.nextInt(ID.size());
            if (!already.contains(other))
            {
                already.add(other);
                i++;
            }
        }

        //tron mang chua id 4 cau
        Collections.shuffle(already);

        //set hinh vao cac o tra loi
        hinhanswer = hinh.get(already.get(0));
        IDA = already.get(0);
        bitmap = BitmapFactory.decodeByteArray(hinhanswer,0,hinhanswer.length);
        A.setImageBitmap(bitmap);

        hinhanswer = hinh.get(already.get(1));
        IDB = already.get(1);
        bitmap = BitmapFactory.decodeByteArray(hinhanswer,0,hinhanswer.length);
        B.setImageBitmap(bitmap);

        hinhanswer = hinh.get(already.get(2));
        IDC = already.get(2);
        bitmap = BitmapFactory.decodeByteArray(hinhanswer,0,hinhanswer.length);
        C.setImageBitmap(bitmap);

        hinhanswer = hinh.get(already.get(3));
        IDD = already.get(3);
        bitmap = BitmapFactory.decodeByteArray(hinhanswer,0,hinhanswer.length);
        D.setImageBitmap(bitmap);



        act();
    }

    void act()
    {
        ktA = 0;
        ktB = 0;
        ktC = 0;
        ktD = 0;
        final int kqd = kq;
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseB.setBackgroundColor(Color.TRANSPARENT);
                chooseC.setBackgroundColor(Color.TRANSPARENT);
                chooseD.setBackgroundColor(Color.TRANSPARENT);
                chooseA.setBackgroundResource(R.drawable.choose);
                ktA++;
                if(IDA==number)
                    kq++;
                else if(IDA!=number && kq!=kqd) {
                    kq = kqd;
                    ktA = 0;
                    ktB = 0;
                    ktC = 0;
                    ktD = 0;
                }
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseA.setBackgroundColor(Color.TRANSPARENT);
                chooseC.setBackgroundColor(Color.TRANSPARENT);
                chooseD.setBackgroundColor(Color.TRANSPARENT);
                chooseB.setBackgroundResource(R.drawable.choose);
                ktB++;
                if (IDB==number)
                    kq++;
                else if(IDB!=number && kq!=kqd) {
                    kq = kqd;
                    ktA = 0;
                    ktB = 0;
                    ktC = 0;
                    ktD = 0;
                }
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseB.setBackgroundColor(Color.TRANSPARENT);
                chooseA.setBackgroundColor(Color.TRANSPARENT);
                chooseD.setBackgroundColor(Color.TRANSPARENT);
                chooseC.setBackgroundResource(R.drawable.choose);
                ktC++;
                if (IDC==number)
                    kq++;
                else if(IDC!=number && kq!=kqd) {
                    kq = kqd;
                    ktA = 0;
                    ktB = 0;
                    ktC = 0;
                    ktD = 0;
                }
            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseB.setBackgroundColor(Color.TRANSPARENT);
                chooseC.setBackgroundColor(Color.TRANSPARENT);
                chooseA.setBackgroundColor(Color.TRANSPARENT);
                chooseD.setBackgroundResource(R.drawable.choose);
                ktD++;
                if (IDD==number)
                    kq++;
                else if(IDD!=number && kq!=kqd) {
                    kq = kqd;
                    ktA = 0;
                    ktB = 0;
                    ktC = 0;
                    ktD = 0;
                }
            }
        });

        btnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        change.start();

    }

    void endgame()
    {
        int idnow;
        final Intent fintest = new Intent(TestActivity2.this,MainActivity.class);

        //ghi vao database diem moi dat duoc

        Cursor select = database.rawQuery("select id from score",null);
        if (select==null)
            idnow = 1;
        else {
            while (select.moveToNext())
                IDscore.add(0, select.getInt(0));
            idnow = IDscore.size() + 1;
        }

        database.execSQL("INSERT INTO score VALUES('"+idnow+"','"+kq+"','"+date+"','"+month+"','"+hour+"','"+minute+"','"+second+"','"+level+"','"+sl+"')");
        dialogfinish();
        kq=0;

        CountDownTimer delay = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                e.stop();
                startActivity(fintest);
            }
        };

        delay.start();
    }


    //check dap an va chuyen man hinh
    void check(FrameLayout a,FrameLayout b,FrameLayout c) //truyen vao 3 button sai
    {
        Animation wrong = AnimationUtils.loadAnimation(this,R.anim.wrong);
        a.setAnimation(wrong);
        b.setAnimation(wrong);
        c.setAnimation(wrong);
        a.startAnimation(wrong);
        b.startAnimation(wrong);
        c.startAnimation(wrong);

    }

    //thong bao sau khi lam xong
    @SuppressLint("DefaultLocale")
    void dialogfinish ()
    {
        TextView thongbao,txtkq,txtthank;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_finish);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loutfin = (LinearLayout) dialog.findViewById(R.id.loutfin);
        thongbao = (TextView) dialog.findViewById(R.id.txtthongbao);
        txtkq = (TextView) dialog.findViewById(R.id.txtkq);
        txtthank = (TextView) dialog.findViewById(R.id.txtthank);

        //nhac
        e = MediaPlayer.create(this,R.raw.finish1);
        test.stop();
        e.start();

        thongbao.setTypeface(chalk);
        txtkq.setTypeface(chalk);
        txtthank.setTypeface(chalk);

        txtkq.setText(String.format("You just finish %d questions from %s level with result: %d/%d",sl,level,kq,sl));

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                loutfin.startAnimation(dia);
            }
        });

        dialog.show();
    }

    void cancel()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.xacnhancancel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView accept = (TextView) dialog.findViewById(R.id.accept);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView titlecancel = (TextView) dialog.findViewById(R.id.titlecancel);
        TextView titlephu=(TextView) dialog.findViewById(R.id.titlephu);

        accept.setTypeface(chalk);
        cancel.setTypeface(chalk);
        titlecancel.setTypeface(chalk);
        titlephu.setTypeface(chalk);

        accept.isClickable();
        cancel.isClickable();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent fintest = new Intent(TestActivity2.this,MainActivity.class);
                kq=0;
                test.stop();
                startActivity(fintest);
            }
        });
        dialog.show();
    }






}
