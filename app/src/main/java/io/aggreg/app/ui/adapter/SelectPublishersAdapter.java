package io.aggreg.app.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publisher.PublisherContentValues;
import io.aggreg.app.provider.publisher.PublisherSelection;
import io.aggreg.app.ui.widget.CheckableLinearLayout;

/**
 * Created by Timo on 6/10/15.
 */
public class SelectPublishersAdapter extends HeaderViewRecyclerAdapter<SelectPublishersAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();
    private int imageWidth;
    private Tracker tracker;

    public SelectPublishersAdapter(Context context, Cursor cursor){
        super(context,cursor);
        this.mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        this.imageWidth = (int)(150*dpWidth);
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(mContext);
        tracker = analytics.newTracker(mContext.getString(R.string.analytics_tracker_id));tracker.setScreenName("intro screen");
    }

    @Override
    protected ViewHolder newHolderInstance(View view) {
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView publisherName;
        public ImageView publisherImage;
        public ImageView checkboxImage;
        public ViewHolder(View view) {
            super(view);
            publisherName = (TextView)view.findViewById(R.id.publisher_item_name);
            publisherImage = (ImageView)view.findViewById(R.id.publisher_item_logo);
            checkboxImage = (ImageView)view.findViewById(R.id.publisher_item_checkbox);
        }
    }

    @Override
    protected ViewHolder onCreateBasicViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_publisher_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    protected void onBindBasicViewHolder(final ViewHolder viewHolder, int position, Cursor cursor) {
        SelectPublisherItem myListItem = SelectPublisherItem.fromCursor(cursor);
        if(myListItem.getFollowing()){
            viewHolder.checkboxImage.setVisibility(View.VISIBLE);
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(true);

        }else{
            viewHolder.checkboxImage.setVisibility(View.INVISIBLE);
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(false);
            viewHolder.publisherName.setTextColor(mContext.getResources().getColor(android.R.color.tertiary_text_light));
            viewHolder.publisherImage.setAlpha(90);
        }
        viewHolder.publisherName.setText(myListItem.getPublisherName());
        viewHolder.publisherImage.setVisibility(View.VISIBLE);
        Picasso.with(mContext).load(myListItem.getPublisherLogoUrl()).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(viewHolder.publisherImage);
        final Long publisherId = cursor.getLong(cursor.getColumnIndex(PublisherColumns._ID));
        final String publisherName = cursor.getString(cursor.getColumnIndex(PublisherColumns.NAME));
        CheckableLinearLayout view = (CheckableLinearLayout)viewHolder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                ((CheckableLinearLayout)view1).toggle();
            }
        });

        view.setOnCheckedChangeListener(new CheckableLinearLayout.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View checkableView, boolean isChecked) {
                //Log.d(LOG_TAG, "checked changed");
                if (isChecked) {
                    PublisherContentValues publisherContentValues = new PublisherContentValues();
                    publisherContentValues.putFollowing(true);
                    PublisherSelection publisherSelection = new PublisherSelection();
                    publisherSelection.id(publisherId);
                    try {
                        publisherContentValues.update(mContext.getContentResolver(), publisherSelection);
                        tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("UX")
                                .setAction("click")
                                .setLabel("publisher "+publisherName+" followed")
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else{
                    PublisherContentValues publisherContentValues = new PublisherContentValues();
                    publisherContentValues.putFollowing(false);
                    PublisherSelection publisherSelection = new PublisherSelection();
                    publisherSelection.id(publisherId);
                    try {
                        publisherContentValues.update(mContext.getContentResolver(), publisherSelection);
                        tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("UX")
                                .setAction("click")
                                .setLabel("publisher " + publisherName+" unfollowed")
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });




    }
}
