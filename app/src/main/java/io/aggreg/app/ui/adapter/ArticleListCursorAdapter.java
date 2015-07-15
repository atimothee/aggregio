package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.fragment.ArticleDetailFragment;
import io.aggreg.app.utils.NetworkUtils;
import io.aggreg.app.utils.References;

public class ArticleListCursorAdapter extends CursorRecyclerViewAdapter{
    private static final int ARTICLE_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();
    private int imageWidth;
    private boolean isTablet;
    private boolean isTwoPane;
    private boolean isBookmarks;

    public ArticleListCursorAdapter(Context context,Cursor cursor, @Nullable Boolean isTwoPane, @Nullable Boolean isBookmarks){
        super(context,cursor);
        this.mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        this.imageWidth = (int)(displayMetrics.widthPixels);
        this.isTablet = mContext.getResources().getBoolean(R.bool.isTablet);
        if(isTablet){
            int spanCount = mContext.getResources().getInteger(R.integer.span_count);
            this.imageWidth = (int)((displayMetrics.widthPixels)/spanCount);
        }
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
    }

    @Override
    public int getItemCount() {
        int originalCount = super.getItemCount();
        int newCount = originalCount + (originalCount/5);
        return newCount;

    }

    @Override
    public int getItemViewType(int position) {
        if(position > 0) {
            if (position % 5 == 0) {
                return AD_VIEW_TYPE;
            }
        }
            return ARTICLE_VIEW_TYPE;

    }

    public static class AdViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView subTitle;

        public AdViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            subTitle = (TextView)itemView.findViewById(R.id.sub_title);
        }
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
        public TextView articleTitle;
        public TextView articleText;
        public TextView publisherName;
        public RelativeTimeTextView timeAgo;
        public SelectableRoundedImageView articleImage;
        private CardView cardView;
        public ArticleViewHolder(View view) {
            super(view);
            articleTitle = (TextView)view.findViewById(R.id.article_item_title);
            articleText = (TextView)view.findViewById(R.id.article_item_text);
            publisherName = (TextView)view.findViewById(R.id.article_item_publisher_name);
            timeAgo = (RelativeTimeTextView)view.findViewById(R.id.article_item_time_ago);
            articleImage = (SelectableRoundedImageView)view.findViewById(R.id.article_item_image);
            cardView = (CardView)view.findViewById(R.id.cardview);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == ARTICLE_VIEW_TYPE) {


            if (isTwoPane) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_item__mini, parent, false);
            } else if (isTablet) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_item__grid, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_item__, parent, false);
            }
            ArticleViewHolder vh = new ArticleViewHolder(itemView);
            return vh;
        }else if(viewType == AD_VIEW_TYPE){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ad_view_item, parent, false);
            AdViewHolder vh = new AdViewHolder(itemView);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder1, final Cursor cursor) {
        int viewType = viewHolder1.getItemViewType();
        if (viewType == ARTICLE_VIEW_TYPE) {
            final ArticleViewHolder viewHolder = (ArticleViewHolder) viewHolder1;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldPosition = viewHolder.getLayoutPosition();

                    int newPosition = oldPosition-(oldPosition/5);
                    cursor.moveToPosition(newPosition);
                    Intent i = new Intent(mContext, ArticleDetailActivity.class);
                    String imageUrl = cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE));
                    boolean hasImage = false;
                    if (imageUrl != null) {
                        hasImage = true;
                    }
                    i.putExtra(References.ARG_KEY_ARTICLE_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns._ID)));
                    i.putExtra(References.ARG_KEY_CATEGORY_ID, cursor.getLong(cursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                    i.putExtra(References.ARG_KEY_ARTICLE_LINK, cursor.getString(cursor.getColumnIndex(ArticleColumns.LINK)));
                    i.putExtra(References.ARG_KEY_ARTICLE_HAS_IMAGE, hasImage);
                    i.putExtra(References.ARG_KEY_CURSOR_POSITION, viewHolder.getLayoutPosition());
                    i.putExtra(References.ARG_KEY_IS_BOOKMARKS, isBookmarks);

                    if (isTwoPane) {
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.article_detail_container, ArticleDetailFragment.newInstance(i.getExtras())).commit();
                    } else {
                        mContext.startActivity(i);
                    }
                }
            });
            ArticleItem articleItem = ArticleItem.fromCursor(cursor);
            viewHolder.articleTitle.setText(articleItem.getTitle());
            if (cursor.getInt(cursor.getColumnIndex(ArticleColumns.IS_READ)) == 0) {
                viewHolder.articleTitle.setTextColor(mContext.getResources().getColor(R.color.primary_text_default_material_light));

            } else if (cursor.getInt(cursor.getColumnIndex(ArticleColumns.IS_READ)) == 1) {
                viewHolder.articleTitle.setTextColor(mContext.getResources().getColor(R.color.secondary_text_default_material_light));
            }
            viewHolder.publisherName.setText(articleItem.getPublisherName());

            viewHolder.timeAgo.setReferenceTime(articleItem.getTimeAgo());
            if (articleItem.getImage() != null) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                Boolean shouldShowImageOnlyOnWifi = settings.getBoolean(mContext.getString(R.string.key_images_on_wifi_only), false);
                Boolean isOnWifi = new NetworkUtils(mContext).isWIFIAvailable();
                if (shouldShowImageOnlyOnWifi) {
                    if (isOnWifi) {

                        viewHolder.articleImage.setVisibility(View.VISIBLE);
                        if (!isTwoPane) {
                            viewHolder.articleText.setVisibility(View.GONE);
                        }
                        Picasso.with(mContext).load(articleItem.getImage() + "=s" + imageWidth).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(viewHolder.articleImage);
                        viewHolder.articleImage.setCornerRadiiDP(4, 4, 0, 0);
                    } else {
                        if (!isTablet) {
                            viewHolder.articleText.setVisibility(View.VISIBLE);
                            if (!isTwoPane) {
                                viewHolder.articleText.setText(Html.fromHtml(articleItem.getText()).toString());
                            }
                        }

                        viewHolder.articleImage.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.articleImage.setVisibility(View.VISIBLE);
                    if (!isTwoPane) {
                        viewHolder.articleText.setVisibility(View.GONE);
                    }
                    Picasso.with(mContext).load(articleItem.getImage() + "=s" + imageWidth).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(viewHolder.articleImage);
                    viewHolder.articleImage.setCornerRadiiDP(4, 4, 0, 0);
                    if (isTwoPane) {
                        viewHolder.articleImage.setCornerRadiiDP(4, 0, 4, 0);
                    }

                }


            } else {
                if (!isTablet) {
                    viewHolder.articleText.setVisibility(View.VISIBLE);
                    if (!isTwoPane) {
                        viewHolder.articleText.setText(Html.fromHtml(articleItem.getText()).toString());
                    }
                }

                viewHolder.articleImage.setVisibility(View.GONE);

            }
            viewHolder.cardView.setPreventCornerOverlap(false);
        }else if(viewType == AD_VIEW_TYPE){
            AdViewHolder adViewHolder = (AdViewHolder) viewHolder1;
            adViewHolder.title.setText("Advert");
            adViewHolder.subTitle.setText("Sponsored");
        }
    }

}

/*
* java.lang.NullPointerException: Attempt to read from field 'java.util.ArrayList android.support.v7.widget.StaggeredGridLayoutManager$Span.mViews' on a null object reference*/