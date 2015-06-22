package io.aggreg.app.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.aggreg.app.R;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.provider.publisher.PublisherContentValues;
import io.aggreg.app.provider.publisher.PublisherSelection;
import io.aggreg.app.provider.selectpublisher.SelectPublisherColumns;
import io.aggreg.app.provider.selectpublisher.SelectPublisherContentValues;
import io.aggreg.app.provider.selectpublisher.SelectPublisherSelection;
import io.aggreg.app.ui.widget.CheckableLinearLayout;

/**
 * Created by Timo on 6/3/15.
 */
public class SelectPublisherListCursorAdapter extends CursorRecyclerViewAdapter<SelectPublisherListCursorAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();

    public SelectPublisherListCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
        this.mContext = context;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_publisher_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        final CheckableLinearLayout view = (CheckableLinearLayout)viewHolder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                view.toggle();
            }
        });

        view.setOnCheckedChangeListener(new CheckableLinearLayout.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View checkableView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.checkboxImage.setVisibility(View.VISIBLE);
                    SelectPublisherContentValues selectPublisherContentValues = new SelectPublisherContentValues();
                    selectPublisherContentValues.putSelected(true);
                    SelectPublisherSelection selectPublisherSelection = new SelectPublisherSelection();
                    selectPublisherSelection.id(cursor.getLong(cursor.getColumnIndex(PublisherColumns._ID)));
                    selectPublisherSelection.notify(false);
                    //selectPublisherSelection.notify(false);
                    selectPublisherContentValues.update(mContext.getContentResolver(), selectPublisherSelection);

                    //TODO: insert or update
                    ContentValues publisherContentValues = new ContentValues();
                    publisherContentValues.put(PublisherColumns._ID, cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns._ID)));
                    publisherContentValues.put(PublisherColumns.NAME, cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns.NAME)));
                    publisherContentValues.put(PublisherColumns.TAG_LINE, cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns.TAG_LINE)));
                    publisherContentValues.put(PublisherColumns.COUNTRY, cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns.COUNTRY)));
                    publisherContentValues.put(PublisherColumns.WEBSITE, cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns.WEBSITE)));
                    try {
                        mContext.getContentResolver().insert(PublisherColumns.CONTENT_URI, publisherContentValues);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    viewHolder.checkboxImage.setVisibility(View.INVISIBLE);
                    SelectPublisherContentValues selectPublisherContentValues = new SelectPublisherContentValues();
                    selectPublisherContentValues.putSelected(false);
                    SelectPublisherSelection selectPublisherSelection = new SelectPublisherSelection();
                    selectPublisherSelection.id(cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns._ID)));
                    selectPublisherContentValues.update(mContext.getContentResolver(), selectPublisherSelection);

                    //delete
                    PublisherSelection publisherSelection = new PublisherSelection();
                    publisherSelection.id(cursor.getLong(cursor.getColumnIndex(SelectPublisherColumns._ID)));
                    try {

                        publisherSelection.delete(mContext.getContentResolver());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

        SelectPublisherItem myListItem = SelectPublisherItem.fromCursor(cursor);
        viewHolder.publisherName.setText(myListItem.getPublisherName());
            viewHolder.publisherImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(myListItem.getPublisherLogoUrl()).into(viewHolder.publisherImage);
        if(myListItem.getSelected()){
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(true);
        }
    }
}
