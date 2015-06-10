package io.aggreg.app.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.aggreg.app.R;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publisher.PublisherSelection;
import io.aggreg.app.provider.selectpublisher.SelectPublisherColumns;
import io.aggreg.app.provider.selectpublisher.SelectPublisherContentValues;
import io.aggreg.app.provider.selectpublisher.SelectPublisherSelection;
import io.aggreg.app.ui.widget.CheckableLinearLayout;

/**
 * Created by Timo on 6/10/15.
 */
public class SelectPublishersAdapter extends HeaderViewRecyclerAdapter<SelectPublishersAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();

    public SelectPublishersAdapter(Context context, Cursor cursor){
        super(context,cursor);
        this.mContext = context;
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
        if(myListItem.getSelected()){
            viewHolder.checkboxImage.setVisibility(View.VISIBLE);
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(true);
        }else{
            viewHolder.checkboxImage.setVisibility(View.INVISIBLE);
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(false);
        }
        viewHolder.publisherName.setText(myListItem.getPublisherName());
        if(myListItem.getPublisherLogoUrl()!=null) {
            viewHolder.publisherImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(myListItem.getPublisherLogoUrl()).into(viewHolder.publisherImage);
        }else{
            Glide.with(mContext).load(R.drawable.new_vision_logo_square).into(viewHolder.publisherImage);
            //viewHolder.publisherImage.setVisibility(View.GONE);
        }
        final Long publisherId = cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns._ID));
        final String publisherName = cursor.getString(cursor.getColumnIndex(SelectPublisherColumns.NAME));
        CheckableLinearLayout view = (CheckableLinearLayout)viewHolder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Log.d(LOG_TAG, "checkable clicked");
                ((CheckableLinearLayout)view1).toggle();
            }
        });

        view.setOnCheckedChangeListener(new CheckableLinearLayout.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View checkableView, boolean isChecked) {
                Log.d(LOG_TAG, "checked changed");
                if (isChecked) {
                    SelectPublisherContentValues selectPublisherContentValues = new SelectPublisherContentValues();
                    selectPublisherContentValues.putSelected(true);
                    SelectPublisherSelection selectPublisherSelection = new SelectPublisherSelection();
                    selectPublisherSelection.id(publisherId);
                    try {
                        selectPublisherContentValues.update(mContext.getContentResolver(), selectPublisherSelection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //TODO: insert or update
                    ContentValues publisherContentValues = new ContentValues();
                    publisherContentValues.put(PublisherColumns._ID, publisherId);
                    publisherContentValues.put(PublisherColumns.NAME, publisherName);
                    try {
                        mContext.getContentResolver().insert(PublisherColumns.CONTENT_URI, publisherContentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else{
                    SelectPublisherContentValues selectPublisherContentValues = new SelectPublisherContentValues();
                    selectPublisherContentValues.putSelected(false);
                    SelectPublisherSelection selectPublisherSelection = new SelectPublisherSelection();
                    selectPublisherSelection.id(publisherId);
                    selectPublisherContentValues.update(mContext.getContentResolver(), selectPublisherSelection);

                    //delete
                    PublisherSelection publisherSelection = new PublisherSelection();
                    publisherSelection.id(publisherId);
                    try {
                        publisherSelection.delete(mContext.getContentResolver());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });




    }
}
