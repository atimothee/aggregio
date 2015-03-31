package io.aggreg.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.aggreg.app.provider.article.ArticleColumns;

/**
 * Created by Timo on 3/31/15.
 */
public class ArticlesAdapter extends CursorAdapter{

    private LayoutInflater inflater;
    public ArticlesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArticlesAdapter(Context context, Cursor c) {
        super(context, c);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArticlesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private    static  class   ViewHolder  {
        TextView title;
        ImageView image;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =   inflater.inflate(R.layout.article_item, null);
        ViewHolder holder  =   new ViewHolder();
        holder.title    =   (TextView)  view.findViewById(R.id.article_item_title);
        holder.image    =   (ImageView)  view.findViewById(R.id.article_item_image);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE)));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE))).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
//                final Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();// Ew!
//                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//                    public void onGenerated(Palette palette) {
//
//                        if (palette != null) {
//
//                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//
//                            if (vibrantSwatch != null) {
//                                holder.title.setBackgroundColor(vibrantSwatch.getRgb());
//                                holder.title.setTextColor(vibrantSwatch.getTitleTextColor());
//                            }
//                        }
//                    }
//                });
            }

            @Override
            public void onError() {

            }
        });
    }
}
