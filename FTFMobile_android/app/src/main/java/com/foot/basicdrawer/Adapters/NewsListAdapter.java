package com.foot.basicdrawer.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.foot.basicdrawer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.foot.basicdrawer.ToolBox.AppSettings.*;

/**
 * Created by riadh on 2/14/2017.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{
    private ArrayList<String> NewsTitles;
    private ArrayList<String> NewsPreviews;
    private ArrayList<String> NewsDates;


    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    private int firstVisibleItem;

    private int listType=0;


    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    int visibleItemCount;
    int totalItemCount;


    public NewsListAdapter(ArrayList<String> titles,ArrayList<String> Dates,ArrayList<String> imgs){
        this.NewsTitles=titles;
        this.NewsPreviews=imgs;
        this.NewsDates=Dates;
    }

    public NewsListAdapter(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener=onLoadMoreListener;
        this.NewsTitles =new ArrayList<>();
        this.NewsDates =new ArrayList<>();
        this.NewsPreviews =new ArrayList<>();
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }


    private void addAll(ArrayList<String> titles,ArrayList<String> Dates,ArrayList<String> imgs){

        NewsTitles.addAll(titles);
        NewsPreviews.addAll(imgs);
        NewsDates.addAll(Dates);

        notifyDataSetChanged();
    }

    public void UpdateRecyclerViewer(ArrayList<String> titles,ArrayList<String> Dates,ArrayList<String> imgs){
        NewsTitles.clear();
        NewsPreviews.clear();
        NewsDates.clear();

        addAll(titles,Dates,imgs);
        notifyItemRangeChanged(0,NewsTitles.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        listType=getListType();
        if(listType==0)
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_new_item_1,parent,false);
        else
        view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_new_item_2,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;

    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    public boolean getMoreLoading() {
       return this.isMoreLoading;
    }


/*
    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {

                    notifyItemInserted(NewsPreviews.size() - 1);
                    notifyItemInserted(NewsTitles.size() - 1);
                    notifyItemInserted(NewsDates.size() - 1);
                }
            });
        }
        else {

            NewsPreviews.remove(NewsPreviews.size() - 1);
            notifyItemRemoved(NewsPreviews.size());

            NewsTitles.remove(NewsTitles.size() - 1);
            notifyItemRemoved(NewsTitles.size());

            NewsDates.remove(NewsDates.size() - 1);
            notifyItemRemoved(NewsDates.size());
        }
    }
*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        Bitmap b =NewsPreviews.get(position);

//        if(b!=null)
//        holder.image.setImageBitmap(b);
//        BindImage(holder.image,"file:///android_asset/new_preload.html");
        holder.bind(position);


    }

    @Override
    public int getItemCount() {
//        int p=NewsPreviews.size(),t=NewsTitles.size();
//        if(p>t)
//            return p;
//        else return t;
    return  NewsPreviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.new_title);
            date=(TextView)itemView.findViewById(R.id.new_date);
            image=(ImageView)itemView.findViewById(R.id.new_img);
        }

        public void bind(int index){
            String str = NewsTitles.get(index);

            if(listType==1){
                if(str.length()>56)
                    str= str.substring(0,55)+" ...";
            }else{
                if(str.length()>80)
                    str= str.substring(0,77)+"...";
            }

            title.setText(str);
            date.setText(NewsDates.get(index));
            String ImgUrl=NewsPreviews.get(index);
            Context ctxt = image.getContext();
//            if(ImgUrl!=null)
//                    BindImage(image,ImgUrl);
            Picasso.with(ctxt).load(ImgUrl).placeholder(R.drawable.preloadimg).into(image);

        }


    }

    private void BindImage(WebView webview, String link){

        webview.setInitialScale(1);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(false);
        webview.loadUrl(link);
    }

}
