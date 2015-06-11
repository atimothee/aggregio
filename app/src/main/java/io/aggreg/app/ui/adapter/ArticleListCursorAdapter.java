package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.PublisherArticlesActivity;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.utils.References;

public class ArticleListCursorAdapter extends CursorRecyclerViewAdapter<ArticleListCursorAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();

    public ArticleListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle;
        public TextView publisherName;
        public RelativeTimeTextView timeAgo;
        public ImageView articleImage;
        public ImageView publisherImage;
        public ViewHolder(View view) {
            super(view);
            articleTitle = (TextView)view.findViewById(R.id.article_item_title);
            publisherName = (TextView)view.findViewById(R.id.article_item_source_name);
            timeAgo = (RelativeTimeTextView)view.findViewById(R.id.article_item_time_ago);
            articleImage = (ImageView)view.findViewById(R.id.article_item_image);
            publisherImage = (ImageView)view.findViewById(R.id.article_item_source_logo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item__, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        cursor.moveToPosition(viewHolder.getLayoutPosition());
        Log.d(LOG_TAG, "id cursor is " + cursor.getLong(cursor.getColumnIndex(ArticleColumns._ID)));
                Intent i = new Intent(mContext, ArticleDetailActivity.class);
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, cursor.getString(cursor.getColumnIndex(ArticleColumns.LINK)));
                mContext.startActivity(i);
            }
        });
        ArticleItem articleItem = ArticleItem.fromCursor(cursor);
        viewHolder.articleTitle.setText(articleItem.getTitle());
        viewHolder.publisherName.setText(articleItem.getPublisherName());
        viewHolder.timeAgo.setReferenceTime(articleItem.getTimeAgo());
        if(articleItem.getImage()!=null) {
            viewHolder.articleImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(articleItem.getImage()).into(viewHolder.articleImage);
        }else{
            viewHolder.articleImage.setVisibility(View.GONE);
        }
        //Glide.with(mContext).load(myListItem.getPublisherLogoUrl()).into(viewHolder.publisherImage);
        viewHolder.publisherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PublisherArticlesActivity.class);
                i.putExtra(References.ARG_KEY_PUBLISHER_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns.PUBLISHER_ID)));
                mContext.startActivity(i);
            }
        });
    }
}
