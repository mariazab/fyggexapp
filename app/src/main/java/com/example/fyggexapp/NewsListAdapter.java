package com.example.fyggexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private OnItemListener mOnItemListener;

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        private final TextView titleTextView;
        private final TextView dateTextView;

        OnItemListener onItemListener;

        private NewsViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    private final LayoutInflater inflater;
    private List<NewsItem> newsList;

    public NewsListAdapter(Context context, OnItemListener onItemListener) {
        inflater = LayoutInflater.from(context);
        this.mOnItemListener = onItemListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(itemView, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (newsList != null) {
            NewsItem current = newsList.get(position);

            //Load image from url
            Picasso.get().load(current.getImage()).into(holder.imageView);

            holder.titleTextView.setText(current.getTitle());
            holder.dateTextView.setText(current.getDate());

        }
    }

    void setNewsList(List<NewsItem> newsItems) {
        newsList = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size();
        }
        else {
            return 0;
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    NewsItem getNewsItemByPosition(int position) {
        return newsList.get(position);
    }
}