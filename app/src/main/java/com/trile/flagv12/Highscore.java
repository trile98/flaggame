package com.trile.flagv12;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Highscore extends Activity {
    ImageButton back,direc;
    ListView lvscore;
    ArrayList<Score> score;
    data db;
    ArrayList<Integer> kq;
    TextView diem,time,level;
    ImageView img;
    MediaPlayer nh;
    Boolean twoclick=false;
    SQLiteDatabase database;
    scoreAdapter arrayAdapter;
    int test=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        lvscore = (ListView) findViewById(R.id.lvscore);
        kq  = new ArrayList<Integer>();
        score =new ArrayList<>();
        img =(ImageView) findViewById(R.id.high) ;
        back = (ImageButton) findViewById(R.id.btnback);
        direc = (ImageButton) findViewById(R.id.btndire);


        //chen nhac

        nh = MediaPlayer.create(this, R.raw.high1);
        nh.start();

        //animation
        Animation show = AnimationUtils.loadAnimation(this,R.anim.highshow);
        img.startAnimation(show);
        lvscore.startAnimation(show);
        Animation backbtn = AnimationUtils.loadAnimation(this,R.anim.back);
        back.startAnimation(backbtn);
        direc.startAnimation(backbtn);


        direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(Highscore.this,MainActivity.class);
                CountDownTimer delay = new CountDownTimer(2000,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.highbackopen,R.anim.highbackclose);
                    }
                };
                Animation balloonback = AnimationUtils.loadAnimation(Highscore.this,R.anim.balloonback);
                img.startAnimation(balloonback);
                nh.stop();
                delay.start();
            }
        });
        lvscore.getEmptyView();
        db=new data(this);
        readdata();

        show();


    }

    @Override
    public void onBackPressed() {
        if (twoclick) {
            super.onBackPressed();
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            startActivity(exit);
        }

        twoclick =true;
        nh.stop();
        Toast.makeText(this ,"please click BACK again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twoclick=false; //qua hai giay reset lai twoclick
            }
        },2000);
    }

    private void show() {
        if (score.size() > 0) {

            //chen cac score vao mot mang
            for (int i = 0; i < score.size(); i++)
                kq.add(i, score.get(i).getKq());
        } else {
            Toast.makeText(this, "You don't have any scores. Please play game", Toast.LENGTH_SHORT);
        }

        arrayAdapter = new scoreAdapter(this, R.layout.scorerow, kq);

        //set mang tren vao listview
        lvscore.setAdapter(arrayAdapter);

        lvscore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                thongtin(i);
            }
        });
    }

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    private void thongtin(final int i) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        diem = (TextView) dialog.findViewById(R.id.txtdiem);
        time = (TextView) dialog.findViewById(R.id.txtdiem);
        level = (TextView) dialog.findViewById(R.id.txtlevel);
        final LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.LLoutinfo);
        ImageButton off = (ImageButton) dialog.findViewById(R.id.btnoff);

        diem.setText(score.get(i).getKq()+"/"+score.get(i).getSl());
        time.setText(score.get(i).getDate()+"/"+score.get(i).getMonth()+" "+score.get(i).getHour()+":"+score.get(i).getMinute()+":"+score.get(i).getSecond());
        level.setText(score.get(i).getLevel());

        final Animation info = AnimationUtils.loadAnimation(this,R.anim.dialog);



        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                layout.startAnimation(info);
            }
        });

        dialog.show();

    }

    private void dialog ()
    {
        final Dialog dialog = new Dialog(this);
        ImageButton cancel = (ImageButton) dialog.findViewById(R.id.btnoff);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void readdata()
    {
        Intent caller = getIntent();
        Bundle fromcaller = caller.getBundleExtra("package");
        String query = fromcaller.getString("query");

        database = db.getdb();
        Cursor select = database.rawQuery(query,null);

        while (select.moveToNext()) {
                        score.add(new Score(
                                select.getInt(0),
                                select.getInt(1),
                                select.getInt(2),
                                select.getInt(3),
                                select.getInt(4),
                                select.getInt(5),
                                select.getInt(6),
                                select.getString(7),
                                select.getInt(8)
                        ));
                }
    }
}
