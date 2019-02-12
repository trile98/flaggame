package com.trile.flagv12;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;


public class MainActivity extends Activity {

    pl.droidsonroids.gif.GifImageButton easy,medium,hard;
    //ImageView high; loi dat ten bien khong mang y nghia ro rang
    ImageView highscorebtn;
    String query;
    Animation begintest,beginhigh;
    Bundle bundle;
    Intent intent;
    Typeface hand1,hand2;
    Dialog direction;
    MediaPlayer effect,music;
    Boolean twoclick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       AnhXa();


        //set animation cho nut
        final Animation fly = AnimationUtils.loadAnimation(this,R.anim.maybay);
        easy.setAnimation(fly);
        medium.setAnimation(fly);
        hard.setAnimation(fly);

        effect.start();
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                music.start();
            }
        });

        Animation balloon = AnimationUtils.loadAnimation(this,R.anim.balloon);
        highscorebtn.setAnimation(balloon);
        begintest = AnimationUtils.loadAnimation(this,R.anim.choosetest);
        beginhigh =AnimationUtils.loadAnimation(this,R.anim.choosehigh);

        //set font
        hand1 = Typeface.createFromAsset(getAssets(),"font/hand.ttf");
        hand2 = Typeface.createFromAsset(getAssets(),"font/note.ttf");


        choose();
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
            music.stop();
            Toast.makeText(this ,"please click BACK again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twoclick=false; //qua hai giay reset lai twoclick
            }
        },2000);
    }

    public void AnhXa() {
        easy = (pl.droidsonroids.gif.GifImageButton) findViewById(R.id.easy);
        medium = (pl.droidsonroids.gif.GifImageButton) findViewById(R.id.medium);
        hard =(pl.droidsonroids.gif.GifImageButton) findViewById(R.id.hard);
        highscorebtn=(ImageView) findViewById(R.id.high);

        effect = MediaPlayer.create(this,R.raw.plane);
        music = MediaPlayer.create(this,R.raw.play1);
    }

    public void choose(){

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                query = "select * from easy";
                bundle.putString("query", query);
                show_direction(bundle);
                effect.start();

            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                query = "select * from medium";
                bundle.putString("query", query);
                show_direction(bundle);
                effect.start();

            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle = new Bundle();
                query = "select * from hard";
                bundle.putString("query", query);
                show_direction(bundle);
                effect.start();
            }
        });


        highscorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();

                intent = new Intent(MainActivity.this,Highscore.class);
                query="select * from score";
                bundle.putString("query",query);
                intent.putExtra("package",bundle);

                CountDownTimer delay = new CountDownTimer(2000,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        music.stop();
                        startActivity(intent);
                        overridePendingTransition(R.anim.highopen,R.anim.highclose);
                    }
                };

                highscorebtn.startAnimation(beginhigh);
                delay.start();
            }
        });

    }


    @SuppressLint("DefaultLocale")
    //void direction(final Bundle a) ten ham nen de la dong tu, ten bien nen dung tu co nghia
    void show_direction(final  Bundle transformed_data)
    {
        direction = new Dialog(this);
        direction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        direction.setContentView(R.layout.huongdan);
        direction.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //nen nhom cac bien theo tu loai
        //TextView play =(TextView) direction.findViewById(R.id.play); ten bien nen la danh tu
        TextView btnplay =(TextView) direction.findViewById(R.id.play);

        TextView title = (TextView) direction.findViewById(R.id.txttitle);

        //TextView hd1 = (TextView) direction.findViewById(R.id.txthd1); ten bien khong nen viet tat
        TextView txtdirection1 = (TextView) direction.findViewById(R.id.txthd1);

        TextView txtprogress = (TextView) direction.findViewById(R.id.txtprogress);
        TextView txtchoose = (TextView) direction.findViewById(R.id.txtchoose);
        TextView txtcancel = (TextView) direction.findViewById(R.id.txthd2);

        //TextView txtgt = (TextView) direction.findViewById(R.id.txtgt); ten bien phai co y nghia
        TextView txtintroducton = (TextView) direction.findViewById(R.id.txtgt);

        TextView txttype1 = (TextView) direction.findViewById(R.id.txttype1);
        TextView txttype2 = (TextView) direction.findViewById(R.id.txttype2);
        TextView space = (TextView) direction.findViewById(R.id.space);

        //TextView txtsl = (TextView) direction.findViewById(R.id.txtsl); ten bien khong viet tat
        TextView txtquality = (TextView) direction.findViewById(R.id.txtsl);

        FrameLayout layouthuongdan = (FrameLayout) direction.findViewById(R.id.Flhuongdan);

        final CheckBox check1= (CheckBox) direction.findViewById(R.id.checktype1);
        final CheckBox check2=(CheckBox) direction.findViewById(R.id.checktype2);

        //final EditText editsl = (EditText) direction.findViewById(R.id.editsl); ten bien phai co nghia
        final EditText QualityBox = (EditText) direction.findViewById(R.id.editsl);

        ImageButton type1 = (ImageButton) direction.findViewById(R.id.type1);
        ImageButton type2 = (ImageButton) direction.findViewById(R.id.type2);

        final String level;

        //final int[] sl = new int[1]; bien khong nen de la chu viet tat
        final int[] quality = new int[1];
        final int max;

        ImageButton cancel = (ImageButton)direction.findViewById(R.id.canceldirec);

        btnplay.setTypeface(hand2);
        title.setTypeface(hand1);
        txtdirection1.setTypeface(hand1);
        txtprogress.setTypeface(hand1);
        txtchoose.setTypeface(hand1);
        txtcancel.setTypeface(hand1);
        space.setTypeface(hand1);
        txtquality.setTypeface(hand1);
        QualityBox.setTypeface(hand1);
        txtintroducton.setTypeface(hand1);
        txttype1.setTypeface(hand1);
        txttype2.setTypeface(hand1);

        check1.setVisibility(INVISIBLE);
        check2.setVisibility(INVISIBLE);

        level = transformed_data.getString("query");
        QualityBox.setHintTextColor(Color.GRAY);

        if(level.compareTo("select * from easy")==0) {
            max=16;
        }
        else if(level.compareTo("select * from medium")==0) {
            max=53;
        }
        else
            max=129;

        QualityBox.setHint(String.format("1->%d",max));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direction.cancel();
            }
        });

        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2.setChecked(false);
                check1.setChecked(true);
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(INVISIBLE);
                intent = new Intent(MainActivity.this,TestActivity.class);
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1.setChecked(false);
                check2.setChecked(true);
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(INVISIBLE);
                intent = new Intent(MainActivity.this,TestActivity2.class);
            }
        });


        btnplay.isClickable();

        final int finalMax = max;
        btnplay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                if (!check1.isChecked() && !check2.isChecked())
                    Toast.makeText(MainActivity.this,"Please choose type of game",Toast.LENGTH_SHORT).show();

                else {
                    if(QualityBox.getText().toString().compareTo("")==0)
                        Toast.makeText(MainActivity.this,"Please insert number of questions",Toast.LENGTH_SHORT).show();

                    else
                        {
                            quality[0] =Integer.parseInt(QualityBox.getText().toString());
                            if (quality[0]<1 || quality[0] > max)
                                Toast.makeText(MainActivity.this,"Please insert right number",Toast.LENGTH_SHORT).show();

                            else {
                                transformed_data.putInt("sl", quality[0]);
                                intent.putExtra("package", transformed_data);
                                music.stop();
                                startActivity(intent);
                                overridePendingTransition(R.anim.testopen, R.anim.testclose);
                            }
                        }
                }
            }
        });

        direction.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
