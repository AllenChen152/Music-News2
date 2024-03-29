package com.example.music_news1.Fragement;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.InputType;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.music_news1.Follow;
import com.example.music_news1.HotList;
import com.example.music_news1.LoginActivity;
import com.example.music_news1.MainActivity;
import com.example.music_news1.MediaPlayerCallback;
import com.example.music_news1.MediaService;
import com.example.music_news1.NewsDetailActivity;
import com.example.music_news1.R;
import com.example.music_news1.RecyclerViewAdapter;
import com.example.music_news1.SearchActivity;
import com.example.music_news1.favorite;
import com.example.music_news1.tools.ActivityCollector;
import com.example.music_news1.tools.DataCleanManager;
import com.example.music_news1.tools.MyReceiver;
import com.google.android.material.navigation.NavigationView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    /*private FragmentHomeBinding binding;*/
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private TextView textView3,textView1,textView2,textview0;
    private ImageButton  imageButton,imageButton3;
    private NavigationView navigationView;

    private MyReceiver myReceiver;
    private ConstraintLayout news1, news2;
    List<Integer> list = new ArrayList();

    private SeekBar seekBar;
    private boolean hasStart = false;
    private SQLiteDatabase db;
    private RecyclerView recyclerView;

    private ImageButton imageButtonSearch;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaService service = (MediaService) ((MediaService.MyBinder) iBinder).getService();
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (service.getMediaPlayerStatus()) {
                        service.toPause();
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    } else {
                        if (hasStart) service.toStart();
                        else service.toPlay(new MediaPlayerCallback() {
                            @Override
                            public void onPrepared(int max) {
                                hasStart = true;
                                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                                seekBar.setMax(max);
                            }

                            @Override
                            public void onUpdate(int current) {
                                //注意更新ui界面需要切换到主线程
                                handler.sendEmptyMessage(1);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.arg1 = current;
                                handler.sendMessage(msg);
                            }
                        });
                        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, null, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        /*String database_path= getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);*/
        mDrawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) getView().findViewById(R.id.nav_design);
        imageButton=(ImageButton)getView().findViewById(R.id.imageButton);
        ImageView imageView_zan=(ImageView) getView().findViewById(R.id.imageView_zan);
        seekBar = getView().findViewById(R.id.seekbar);

        imageButtonSearch = getView().findViewById(R.id.imageButton_search);

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

        //主页轮播
        initDate();
        Banner mbanner=(Banner) getView().findViewById(R.id.imageView2);
        Banner banner = mbanner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }

        });
        //是否允许自动轮播
        mbanner.isAutoLoop(true);
        //设置指示器
        mbanner.setIndicator(new CircleIndicator(getContext()));
        //开始轮播
        mbanner.start();
        //主页显示新闻内容和头图
        String database_path= getContext().getDatabasePath("UserStore.db").toString();
        db= SQLiteDatabase.openDatabase(database_path,null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        Cursor cursor=db.rawQuery("select * from news",null);
        recyclerView=getView().findViewById(R.id.recycler_view);
        ArrayList<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
        String[] tit=new String[cursor.getCount()];
        String[] author=new String[cursor.getCount()];
        String[] date=new String[cursor.getCount()];
        int[] id=new int[cursor.getCount()];
        if(cursor.moveToFirst()){
             for(int j=0;j<cursor.getCount();j++){
                 HashMap<String, Object> map = new HashMap<String, Object>();
                 tit[j]=cursor.getString(2);
                 author[j]=cursor.getString(1);
                 date[j]=cursor.getString(3);
                 id[j]=cursor.getInt(0);
                 map.put("title",tit[j]);
                 cursor.moveToNext();
                 list.add(map);
             }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewAdapter mAdapter=new RecyclerViewAdapter(list,tit);
        recyclerView.setAdapter(mAdapter);

        //新闻具体内容
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(view.getContext(), NewsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id[position]);
                bundle.putString("title",tit[position]);
                bundle.putString("author",author[position]);
                bundle.putString("date",date[position]);
                intent.putExtras(bundle);
                startActivity(intent);
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

        //电量显示
        textview0=(TextView) getView().findViewById(R.id.textview0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);
        myReceiver.setMyListener(this::onListener);

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        getActivity().bindService(new Intent(getActivity(), MediaService.class), conn, Context.BIND_AUTO_CREATE);
    }
    public void onListener(String level) {
        textview0.setText(level + "%");
    }
    private void initDate() {
        list.add(R.drawable.ic_tu1);
        list.add(R.drawable.bb);
        list.add(R.drawable.cc);
        list.add(R.drawable.dd);
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
                            Intent loveIntent = new Intent(getActivity(), favorite.class);
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

    //主线程更新ui界面
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    seekBar.setProgress(msg.arg1);
                    break;
            }
        }
    };
/*    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(conn);
    }
}
