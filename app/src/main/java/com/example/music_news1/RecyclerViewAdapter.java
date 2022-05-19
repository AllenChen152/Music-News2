package com.example.music_news1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Map<String, Object>> arrayList;
    String[] tit;
    Context context;
    Activity activity;
    int[] images=new int[]{R.drawable.toutu1,R.drawable.toutu2,R.drawable.toutu3,R.drawable.toutu4,R.drawable.toutu5};

    public RecyclerViewAdapter(List<Map<String, Object>> arrayList,String[] tit) {
        this.arrayList = arrayList;
        this.tit=tit;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       Log.i("test","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);//加载子布局
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 绑定ViewHolder，每加载一项都会调用一次
     * 对每一项数据进行赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("test","onBindViewHolder");
        //  RecylerBean recylerBean = bookList.get(position);
        // holder.bookname.setText(recylerBean.getFruitName());

        //将获取到的数据为每一项赋值
        //Glide加载RecyclerView图片时，使用holder.itemView.getContext()代表上下文context
        //Glide.with(holder.itemView.getContext()).load(arrayList.get(position).get("photo")).into(holder.newsImage);
        holder.newsImage.setImageResource(images[position]);
       // holder.title.setText(arrayList.get(position).get("title"));
        holder.title.setText(tit[position]);
    }

    /**
     * 获取项数量
     * 告诉RecyclerView一共有多少子项。
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * RecyclerView的持有者类
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView title;


        public ViewHolder(View view){
            super(view);
            //获取子布局view中的控件
            newsImage = view.findViewById(R.id.newsImage);
            title = view.findViewById(R.id.title);
        }

    }

}
