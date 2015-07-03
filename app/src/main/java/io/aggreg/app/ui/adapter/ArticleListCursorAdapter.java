package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.utils.References;

public class ArticleListCursorAdapter extends CursorRecyclerViewAdapter<ArticleListCursorAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();
    private int imageWidth;
    private boolean isTablet;
    private boolean isTwoPane;
    private boolean isBookmarks;
    //private Tracker tracker;

    public ArticleListCursorAdapter(Context context,Cursor cursor, @Nullable Boolean isTwoPane, @Nullable Boolean isBookmarks){
        super(context,cursor);
        this.mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        this.imageWidth = (int)(displayMetrics.widthPixels);
        this.isTablet = mContext.getResources().getBoolean(R.bool.isTablet);
        if(isTwoPane != null) {
            this.isTwoPane = isTwoPane;
        }else {
            this.isTwoPane =false;
        }
        if(isBookmarks != null) {
            this.isBookmarks = isBookmarks;
        }else {
            this.isBookmarks =false;
        }
//        GoogleAnalytics analytics = GoogleAnalytics.getInstance(mContext);
//        tracker = analytics.newTracker(mContext.getString(R.string.analytics_tracker_id));
//        if(isTablet) {
//            tracker.setScreenName("article grid");
//        }else {
//            tracker.setScreenName("article list");
//        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle;
        public TextView articleText;
        public TextView publisherName;
        public RelativeTimeTextView timeAgo;
        public ImageView articleImage;
        public ViewHolder(View view) {
            super(view);
            articleTitle = (TextView)view.findViewById(R.id.article_item_title);
            articleText = (TextView)view.findViewById(R.id.article_item_text);
            publisherName = (TextView)view.findViewById(R.id.article_item_publisher_name);
            timeAgo = (RelativeTimeTextView)view.findViewById(R.id.article_item_time_ago);
            articleImage = (ImageView)view.findViewById(R.id.article_item_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if(isTwoPane){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item__mini, parent, false);
        }
        else if (isTablet) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item__grid, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item__, parent, false);
        }
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
                String imageUrl = cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE));
                boolean hasImage = false;
                if(imageUrl != null){
                    hasImage = true;
                }
                i.putExtra(References.ARG_KEY_ARTICLE_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, cursor.getString(cursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_ARTICLE_HAS_IMAGE, hasImage);
                i.putExtra(References.ARG_KEY_CURSOR_POSITION, viewHolder.getLayoutPosition());
                i.putExtra(References.ARG_KEY_IS_BOOKMARKS, isBookmarks);

                if(isTwoPane){
                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.article_detail_container, ArticleDetailFragment.newInstance(i.getExtras())).commit();
                }
                else{
                    mContext.startActivity(i);
                }
//                tracker.send(new HitBuilders.EventBuilder()
//                        .setCategory("UX")
//                        .setAction("click")
//                        .setLabel("article " + cursor.getString(cursor.getColumnIndex(ArticleColumns.LINK)) + " opened")
//                        .build());
            }
        });
        ArticleItem articleItem = ArticleItem.fromCursor(cursor);
        viewHolder.articleTitle.setText(articleItem.getTitle());
        if(cursor.getInt(cursor.getColumnIndex(ArticleColumns.IS_READ)) == 0){
            viewHolder.articleTitle.setTextColor(mContext.getResources().getColor(R.color.primary_text_default_material_light));

        }else if(cursor.getInt(cursor.getColumnIndex(ArticleColumns.IS_READ)) == 1){
            viewHolder.articleTitle.setTextColor(mContext.getResources().getColor(R.color.secondary_text_default_material_light));
        }
        viewHolder.publisherName.setText(articleItem.getPublisherName());

        viewHolder.timeAgo.setReferenceTime(articleItem.getTimeAgo());
        if(articleItem.getImage()!=null) {
            viewHolder.articleImage.setVisibility(View.VISIBLE);
            if(!isTwoPane) {
                viewHolder.articleText.setVisibility(View.GONE);
            }
            Picasso.with(mContext).load(articleItem.getImage()+"=s"+imageWidth).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(viewHolder.articleImage);

        }else{
            if(!isTablet){
                viewHolder.articleText.setVisibility(View.VISIBLE);
                if(!isTwoPane) {
                    viewHolder.articleText.setText(Html.fromHtml(articleItem.getText()).toString());
                }
            }

            viewHolder.articleImage.setVisibility(View.GONE);

        }

    }
}

/*
* java.lang.NullPointerException: Attempt to read from field 'java.util.ArrayList android.support.v7.widget.StaggeredGridLayoutManager$Span.mViews' on a null object reference*/