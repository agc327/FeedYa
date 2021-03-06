package com.jbelmaro.feedya;

import java.util.List;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbelmaro.feedya.util.ArticleItemBean;
import com.squareup.picasso.Picasso;

public class ArticleListItemAdapter extends ArrayAdapter<ArticleItemBean> {

    private final List<ArticleItemBean> list;
    private final Activity context;
    private int lastPosition = -1;

    public ArticleListItemAdapter(Activity context, List<ArticleItemBean> list) {
        super(context, R.layout.article_row, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected ImageView icon;
        protected TextView time;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        try {
            if (list.get(position).getIconURL() != "") {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.article_row, null);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) view.findViewById(R.id.article_title);
                viewHolder.icon = (ImageView) view.findViewById(R.id.article_icon);
                viewHolder.time = (TextView) view.findViewById(R.id.article_time);
                view.setTag(viewHolder);

                final ViewHolder holder = (ViewHolder) view.getTag();
                holder.text.setText(Html.fromHtml(list.get(position).getTitle()));
                Picasso.with(context).setDebugging(true);
                Picasso.with(context).load(list.get(position).getIconURL()).resize(300, 300).centerCrop().into(holder.icon);
                holder.time.setText(Html.fromHtml(list.get(position).getTime()));

            } else {

                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.article_row, null);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) view.findViewById(R.id.article_title);
                viewHolder.time = (TextView) view.findViewById(R.id.article_time);
                view.setTag(viewHolder);

                final ViewHolder holder = (ViewHolder) view.getTag();
                holder.text.setText(Html.fromHtml(list.get(position).getTitle()));
                holder.time.setText(Html.fromHtml(list.get(position).getTime()));

            }
            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            view.startAnimation(animation);

            lastPosition = position;
        } catch (NullPointerException e) {
            Log.e("ArticleListItemAdapter", "la lista esta a null");
        }

        return view;
    }

}