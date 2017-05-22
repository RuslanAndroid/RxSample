package ru.ruslankhusaenov.supercool.fragments.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import ru.ruslankhusaenov.supercool.R;
import ru.ruslankhusaenov.supercool.models.NewsItem;


/**
 * Created by Ruslan on 10.11.2016.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    List<NewsItem> rows;
    NewsListAdapter.ItemclickListener mListener;

    Context mContext;
    public void setOnClickListener(NewsListAdapter.ItemclickListener click) {
        this.mListener = click;
    }

    public NewsListAdapter(Context context){
        mContext=context;
    }

    public interface ItemclickListener {
        void onClick(View view, int position);
    }

    public void setInfo(List<NewsItem> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addInfo(List<NewsItem> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(v);
    }

    public NewsItem getItem(int position) {
        return rows.get(position);
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {

        if( getItem(position).publishedAt != null){

            holder.date.setText(getItem(position).publishedAt);

        }

        if( getItem(position).title != null){

            holder.title.setText(getItem(position).title);

        }

        holder.setClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        if (rows == null) return 0;
        return rows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,date;

        private NewsListAdapter.ItemclickListener clickListener;

        public ViewHolder(final View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.news_text);
            date = (TextView) v.findViewById(R.id.news_date);
            v.setOnClickListener(this);
        }


        public void setClickListener(NewsListAdapter.ItemclickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }
}