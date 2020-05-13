package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_item_title);
        titleTextView.setText(currentNews.getTitle());
        TextView sectionNameTextView = (TextView) listItemView.findViewById(R.id.news_item_section_name);
        sectionNameTextView.setText(currentNews.getSectionName());
        TextView authorNameTextView = (TextView) listItemView.findViewById(R.id.news_item_author_name);
        if (currentNews.hasAuthorName()) {
            authorNameTextView.setVisibility(View.VISIBLE);
            authorNameTextView.setText("Written by " + currentNews.getAuthorName());
        } else {
            authorNameTextView.setVisibility(View.GONE);
        }
        TextView datePublishedTextView = (TextView) listItemView.findViewById(R.id.news_item_date_published);
        if (currentNews.hasDatePublished()) {
            datePublishedTextView.setVisibility(View.VISIBLE);
            datePublishedTextView.setText(getFormattedDate(currentNews.getDatePublished()));
        } else {
            datePublishedTextView.setVisibility(View.GONE);
        }

        return listItemView;
    }

    private String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(date);
    }
}
