package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.PublisherArticlesActivity;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.utils.References;

public class ArticleListCursorAdapter extends CursorRecyclerViewAdapter<ArticleListCursorAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();
    private int imageWidth;
    private boolean isTablet;

    public ArticleListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        this.imageWidth = (int)(displayMetrics.widthPixels);
        this.isTablet = mContext.getResources().getBoolean(R.bool.isTablet);
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
            publisherName = (TextView)view.findViewById(R.id.article_item_source_name);
            timeAgo = (RelativeTimeTextView)view.findViewById(R.id.article_item_time_ago);
            articleImage = (ImageView)view.findViewById(R.id.article_item_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (isTablet) {
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
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, cursor.getString(cursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                mContext.startActivity(i);
            }
        });
        ArticleItem articleItem = ArticleItem.fromCursor(cursor);
        viewHolder.articleTitle.setText(articleItem.getTitle());
//        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
//
//        if(viewHolder.articleTitle.getLineCount()>2){
//            layoutParams.setFullSpan(true);
//        }
//        else{
//           // layoutParams.setFullSpan(false);
//        }

        viewHolder.publisherName.setText(articleItem.getPublisherName());
        viewHolder.timeAgo.setReferenceTime(articleItem.getTimeAgo());
        if(articleItem.getImage()!=null) {
            viewHolder.articleImage.setVisibility(View.VISIBLE);
            viewHolder.articleText.setVisibility(View.GONE);
            //Glide.with(mContext).load(articleItem.getImage()+"=s"+imageWidth).placeholder(R.drawable.no_img_placeholder).fitCenter().into(viewHolder.articleImage);
            Picasso.with(mContext).load(articleItem.getImage()+"=s"+imageWidth).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(viewHolder.articleImage);

        }else{
            if(!isTablet){
                viewHolder.articleText.setVisibility(View.VISIBLE);
                viewHolder.articleText.setText(Html.fromHtml(articleItem.getText()).toString());
            }

            viewHolder.articleImage.setVisibility(View.GONE);

        }

    }
}
