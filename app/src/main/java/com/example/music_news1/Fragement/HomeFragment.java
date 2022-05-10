package com.example.music_news1.Fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.music_news1.Creatsqlite;
import com.example.music_news1.Follow;
import com.example.music_news1.HotList;
import com.example.music_news1.LoginActivity;
import com.example.music_news1.NewsDetailActivity;
import com.example.music_news1.R;
import com.example.music_news1.Register;
import com.example.music_news1.tools.ActivityCollector;
import com.example.music_news1.tools.DataCleanManager;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    /*private FragmentHomeBinding binding;*/
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private TextView textView3,textView1,textView2;
    private ImageButton  imageButton,imageButton3;
    private NavigationView navigationView;

    private ConstraintLayout news1, news2;

    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private boolean hasStart = false;
    private Creatsqlite dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, null, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        dbHelper = new Creatsqlite(getActivity(), "UserStore.db", null, 1);
        mDrawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) getView().findViewById(R.id.nav_design);
        imageButton=(ImageButton)getView().findViewById(R.id.imageButton);
        ImageView imageView_zan=(ImageView) getView().findViewById(R.id.imageView_zan);
        seekBar = getView().findViewById(R.id.seekbar);
        news1 = getView().findViewById(R.id.layout_news_1);
        news2 = getView().findViewById(R.id.layout_news_2);
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
        //点赞切换
        final int[] i = {0};
        imageView_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(i[0] == 0);
                if(i[0] ==0) i[0] = 1;
                else i[0] =0;

            }
        });

        news1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewsDetailActivity.class));
            }
        });

        news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewsDetailActivity.class));
            }
        });

        textView3=(TextView)getView().findViewById(R.id.textview3);
        textView1=(TextView)getView().findViewById(R.id.textview1);
        textView2=(TextView)getView().findViewById(R.id.textview2);
        textView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), HotList.class);
                startActivity(intent1);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String user=sp.getString("username","");
                if(user.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), Follow.class);
                    startActivity(intent);
                }
            }
        });


    }
    @SuppressLint("RestrictedApi")
    public void onStart() {

        super.onStart();
        toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setTitle("爱乐");
        SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user=sp.getString("username","");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        if (actionBar != null) {
            //通过HomeAsUp来让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置Indicator来添加一个点击图标（默认图标是一个返回的箭头）
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_edit);
        //设置菜单项的监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //逻辑页面处理
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_edit:
                        //每个菜单项的点击事件，通过Intent实现点击item简单实现活动页面的跳转。

                        if (!user.equals("")) {
                            Intent editIntent = new Intent(getActivity(), NewsDetailActivity.class);
                            startActivity(editIntent);
                        } else {
                            Toast.makeText(getActivity(), "暂无", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_articles:
                        // 我发布的文章
                        /*if (!TextUtils.isEmpty(currentUserId)) {
                            Intent editIntent = new Intent(MainActivity.this, ArticleActivity.class);
                            editIntent.putExtra("user_article_id", currentUserId);
                            startActivityForResult(editIntent, 6);
                        } else {
                            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                            loginIntent.putExtra("loginStatus", "请先登录后才能操作！");
                            startActivityForResult(loginIntent, 1);
                        }
                        */
                        Toast.makeText(getActivity(), "暂无", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_favorite:

                        if (!user.equals("")) {
                            Intent loveIntent = new Intent(getActivity(), Follow.class);
                            startActivity(loveIntent);
                        } else {
                            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(loginIntent);
                        }
                        break;
                    case R.id.nav_clear_cache:
                        // 清除缓存
                        // Toast.makeText(MainActivity.this,"你点击了清除缓存，下步实现把",Toast.LENGTH_SHORT).show();
                       // clearCacheData();
                        clearCacheData();
                        break;
                    case R.id.nav_switch:
                        // 切换账号
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        // 登录请求码是1
                        startActivity(intent);
                        break;
                    default:
                }
                return true;
            }
        });

    }
    public void clearCacheData() {
        // 缓存目录为
        File file = new File(getActivity().getCacheDir().getPath());
        //System.out.println("缓存目录为：" + getActivity().getCacheDir().getPath());
        String cacheSize = null;
        try {
            cacheSize = DataCleanManager.getCacheSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("缓存大小为：" + cacheSize);
        new MaterialDialog.Builder(getActivity())
                .title("提示")
                .content("当前缓存大小一共为" + cacheSize + "。确定要删除所有缓存？离线内容及其图片均会被清除。")
                .positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        DataCleanManager.cleanInternalCache(getActivity());
                        Toast.makeText(getActivity(), "成功清除缓存。", Toast.LENGTH_SHORT).show();
                    }
                })
                .negativeText("取消")
                .show();

    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //R.id.home修改导航按钮的点击事件为打开侧滑
            case android.R.id.home:
                //打开侧滑栏，注意要与xml中保持一致START
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.userFeedback:
                //填写用户反馈
                new MaterialDialog.Builder(getActivity())
                        .title("用户反馈")
                        .inputRangeRes(1, 50, R.color.colorBlack)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                System.out.println("反馈的内容为：" + input);
                                Toast.makeText(getActivity(), "反馈成功！反馈内容为：" + input, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
            case R.id.userExit:
                SweetAlertDialog mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
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
/*    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}
