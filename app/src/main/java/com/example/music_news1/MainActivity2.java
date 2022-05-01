package com.example.music_news1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.music_news1.tools.ActivityCollector;
import com.example.music_news1.tools.BaseActivity;

import java.io.IOException;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity2 extends BaseActivity  {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private TextView textView3,textView1,textView2;
    private ImageButton imageButton5,imageButton6,imageButton7, imageButton;

    private ConstraintLayout news1, news2;

    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private boolean hasStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        textView3=(TextView)findViewById(R.id.textview3);
        textView1=(TextView)findViewById(R.id.textview1);
        textView2=(TextView)findViewById(R.id.textview2);
        textView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity2.this, HotList.class);
                startActivity(intent1);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
                String user=sp.getString("username","");
                if(user.equals("")) {
                    Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity2.this, Follow.class);
                    startActivity(intent);
                }
            }
        });

        //现在findViewById已经不需要强制转换了噢 :)
        imageButton=(ImageButton) findViewById(R.id.imageButton);
        imageButton5=(ImageButton) findViewById(R.id.imageButton5);
        imageButton6=(ImageButton) findViewById(R.id.imageButton6);
        imageButton7=(ImageButton) findViewById(R.id.imageButton7);

        seekBar = findViewById(R.id.seekbar);
        news1 = findViewById(R.id.layout_news_1);
        news2 = findViewById(R.id.layout_news_2);

        imageButton6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity2.this, quanzi.class);
                startActivity(intent1);
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity2.this, PersonActivity.class);
                startActivity(intent1);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                } else {
                    if (hasStart) mediaPlayer.start();
                    else toPlay();
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });

        news1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, NewsDetailActivity.class));
            }
        });

        news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, NewsDetailActivity.class));
            }
        });

        /*Button button1=(Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this, newsdetail.class);
                startActivity(intent1);
            }
        });*/
        /*WebView webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://yue.ifeng.com/shanklist/26-35261-/");
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(false);*/

    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("当前MainActivity活动又被加载onStart");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //通过HomeAsUp来让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置Indicator来添加一个点击图标（默认图标是一个返回的箭头）
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //R.id.home修改导航按钮的点击事件为打开侧滑
            case android.R.id.home:
                //打开侧滑栏，注意要与xml中保持一致START
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.userFeedback:
                //填写用户反馈
                new MaterialDialog.Builder(MainActivity2.this)
                        .title("用户反馈")
                        .inputRangeRes(1, 50, R.color.colorBlack)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                System.out.println("反馈的内容为：" + input);
                                Toast.makeText(MainActivity2.this, "反馈成功！反馈内容为：" + input, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
            case R.id.userExit:
                SweetAlertDialog mDialog = new SweetAlertDialog(MainActivity2.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("提示")
                        .setContentText("您是否要退出？")
                        .setCustomImage(null)
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                ActivityCollector.finishAll();
                            }
                        });
                mDialog.show();
                break;
            default:
        }
        return true;
    }

    /**
     * 播放组件
     */
    private void toPlay(){

        try {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(getResources().openRawResourceFd(R.raw.music1));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    hasStart = true;
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    seekBar.setMax(mediaPlayer.getDuration());
                }
            });

        }catch (Exception e){ }

        //开启新线程获取实时播放位置
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while (true){
                        sleep(1000);    //为了防止内存占用过多，需要休眠
                        if (mediaPlayer.isPlaying()){
                            //注意更新ui界面需要切换到主线程
                            handler.sendEmptyMessage(1);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    //主线程更新ui界面
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (0 != mediaPlayer.getCurrentPosition()){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                    break;
            }
        }
    };

    /*public class WebViewClientDemo extends WebViewClient {
        @Override
        // 在WebView中而不是默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/

}