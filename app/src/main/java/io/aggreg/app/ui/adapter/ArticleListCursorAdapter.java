package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/3/15.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.MainActivity;
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
        int adInterval = mContext.getResources().getInteger(R.integer.ad_interval_count);
        int adViewCount = originalCount/adInterval;
        return originalCount+adViewCount;

    }




    @Override
    public int getItemViewType(int position) {
        position = position+1;
        int adInterval = mContext.getResources().getInteger(R.integer.ad_interval_count);
        if(position > 1 && position % adInterval == 0) {

                return AD_VIEW_TYPE;
            }
        else {
            return ARTICLE_VIEW_TYPE;
        }

    }

    public static class AdViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView adTitle;
        public TextView adBody;
        public MediaView adMedia;
        public RatingBar adRatingBar;
        public Button adCallToAction;

        public AdViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            adTitle = (TextView)itemView.findViewById(R.id.nativeAdTitle);
            adBody = (TextView)itemView.findViewById(R.id.nativeAdBody);
            adMedia = (MediaView)itemView.findViewById(R.id.nativeAdMedia);
            adCallToAction = (Button)itemView.findViewById(R.id.nativeAdCallToAction);
            adRatingBar = (RatingBar)itemView.findViewById(R.id.nativeAdStarRating);
        }
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
        public TextView articleTitle;
        public TextView publisherName;
        public RelativeTimeTextView timeAgo;
        public SimpleDraweeView articleImage;
        private CardView cardView;
        public ArticleViewHolder(View view) {
            super(view);
            articleTitle = (TextView)view.findViewById(R.id.article_item_title);
            publisherName = (TextView)view.findViewById(R.id.article_item_publisher_name);
            timeAgo = (RelativeTimeTextView)view.findViewById(R.id.article_item_time_ago);
            articleImage = (SimpleDraweeView)view.findViewById(R.id.article_item_image);
            cardView = (CardView)view.findViewById(R.id.cardview);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
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
                    .inflate(R.layout.ad_unit, parent, false);
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
            int adInterval = mContext.getResources().getInteger(R.integer.ad_interval_count);
            int oldPosition = viewHolder.getLayoutPosition();
            final int position = oldPosition - ((oldPosition+1)/adInterval);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cursor.moveToPosition(position);
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
                        Uri uri = Uri.parse(articleItem.getImage() + "=s" + imageWidth);

                        viewHolder.articleImage.setImageURI(uri);

                        }else {

                        viewHolder.articleImage.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.articleImage.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse(articleItem.getImage() + "=s" + imageWidth);

                    viewHolder.articleImage.setImageURI(uri);

                }


            } else {

                viewHolder.articleImage.setVisibility(View.GONE);

            }
            viewHolder.cardView.setPreventCornerOverlap(false);
        }else if(viewType == AD_VIEW_TYPE){
            final AdViewHolder viewHolder = (AdViewHolder) viewHolder1;
            try {
                NativeAd nativeAd = ((MainActivity) mContext).getNativeAd();

                viewHolder.adTitle.setText(nativeAd.getAdTitle());
                viewHolder.adBody.setText(nativeAd.getAdBody());
                viewHolder.adCallToAction.setText(nativeAd.getAdCallToAction());
                viewHolder.adCallToAction.setVisibility(View.VISIBLE);
                NativeAd.Image adCoverImage = nativeAd.getAdCoverImage();
                int bannerWidth = adCoverImage.getWidth();
                int bannerHeight = adCoverImage.getHeight();
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                int screenWidth = metrics.widthPixels;
                int screenHeight = metrics.heightPixels;
                viewHolder.adMedia.setLayoutParams(new LinearLayout.LayoutParams(
                        screenWidth,
                        Math.min((int) (((double) screenWidth / (double) bannerWidth) * bannerHeight), screenHeight / 3)
                ));
                NativeAd.Rating rating = nativeAd.getAdStarRating();
                if (rating != null) {
                    viewHolder.adRatingBar.setVisibility(View.VISIBLE);
                    viewHolder.adRatingBar.setNumStars((int) rating.getScale());
                    viewHolder.adRatingBar.setRating((float) rating.getValue());
                } else {
                    viewHolder.adRatingBar.setVisibility(View.GONE);
                }
                viewHolder.adMedia.setNativeAd(nativeAd);
                viewHolder.cardView.setVisibility(View.VISIBLE);
                nativeAd.registerViewForInteraction(viewHolder.itemView);
            }catch (Exception e){
                e.printStackTrace();
                viewHolder.cardView.setVisibility(View.GONE);
            }
        }
    }

}

/*
* java.lang.NullPointerException: Attempt to read from field 'java.util.ArrayList android.support.v7.widget.StaggeredGridLayoutManager$Span.mViews' on a null object reference*/