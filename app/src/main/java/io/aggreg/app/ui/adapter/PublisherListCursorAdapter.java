package io.aggreg.app.ui.adapter;

import android.content.ContentResolver;
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
import io.aggreg.app.ui.widget.CheckableLinearLayout;

/**
 * Created by Timo on 6/3/15.
 */
public class PublisherListCursorAdapter extends CursorRecyclerViewAdapter<PublisherListCursorAdapter.ViewHolder>{
    private Context mContext;
    private static String LOG_TAG = ArticleListCursorAdapter.class.getSimpleName();

    public PublisherListCursorAdapter(Context context,Cursor cursor){
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
                .inflate(R.layout.publisher_list_item, parent, false);
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
            PublisherSelection publisherSelection = new PublisherSelection();
            PublisherContentValues contentValues = new PublisherContentValues();

            @Override
            public void onCheckedChanged(View checkableView, boolean isChecked) {
                publisherSelection.id(cursor.getLong(cursor.getColumnIndex(PublisherColumns._ID)));
                if (isChecked) {
                    viewHolder.checkboxImage.setVisibility(View.VISIBLE);
                    //contentValues.putFollowing(true);
//                    contentValues.putCountry("Uganda");
//                    contentValues.update(mContext.getContentResolver(), publisherSelection);

                } else {
                    viewHolder.checkboxImage.setVisibility(View.INVISIBLE);
//                    contentValues.putCountry("Uganda");
//                    //contentValues.putFollowing(false);
//                    contentValues.update(mContext.getContentResolver(), publisherSelection);

                }
            }
        });
        PublisherItem myListItem = PublisherItem.fromCursor(cursor);
        viewHolder.publisherName.setText(myListItem.getPublisherName());
        if(myListItem.getPublisherLogo()!=null) {
            viewHolder.publisherImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(myListItem.getPublisherLogo()).into(viewHolder.publisherImage);
        }else{
            Glide.with(mContext).load(R.drawable.new_vision_logo_square).into(viewHolder.publisherImage);
            //viewHolder.publisherImage.setVisibility(View.GONE);
        }
        if(myListItem.getFollowing()){
            ((CheckableLinearLayout) viewHolder.itemView).setChecked(true);
        }
    }
}
