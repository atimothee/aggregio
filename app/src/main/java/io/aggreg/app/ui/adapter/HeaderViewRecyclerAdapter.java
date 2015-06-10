package io.aggreg.app.ui.adapter;

/**
 * Created by Timo on 6/10/15.
 */
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * An adapter supports headers and footers for
 * {@link android.support.v7.widget.RecyclerView.Adapter}
 *
 * @author xubowen92@gmail.com
 */
public abstract class HeaderViewRecyclerAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> {
    private Context mContext;

    private Cursor mCursor;

    private boolean mDataValid;

    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    private static final int VIEW_TYPE_NORMAL = 0;
    // Headers viewType from MAX_VALUE decrease, Footers viewType from MIN_VALUE increase.
    private static final int VIEW_TYPE_HEADER = Integer.MAX_VALUE;
    private static final int VIEW_TYPE_FOOTER = -Integer.MAX_VALUE;

    // mapping < viewType -> View >
    private SparseArray<View> headers;
    private SparseArray<View> footers;

    public HeaderViewRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
        this.headers = new SparseArray<View>();
        this.footers = new SparseArray<View>();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public HeaderViewRecyclerAdapter(Context context, Cursor cursor, List<View> headers, List<View> footers) {
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
        this.headers = new SparseArray<View>();
        this.footers = new SparseArray<View>();
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                this.headers.put(VIEW_TYPE_HEADER - i, headers.get(i));
            }
        }
        if (footers != null) {
            for (int i = 0; i < footers.size(); i++) {
                this.footers.put(VIEW_TYPE_FOOTER + i, footers.get(i));
            }
        }
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    /**
     * Add a header to adapter.
     *
     * @param view header view.
     */
    public void addHeader(View view) {
        headers.put(VIEW_TYPE_HEADER - headers.size(), view);
        notifyDataSetChanged();
    }

    /**
     * Add a footer to adapter.
     *
     * @param view footer view.
     */
    public void addFooter(View view) {
        footers.put(VIEW_TYPE_FOOTER + footers.size(), view);
        notifyDataSetChanged();
    }

    /**
     * Remove a header from adapter.
     *
     * @param view header view.
     */
    public void removeHeader(View view) {
        for (int i = 0; i < headers.size(); i++) {
            int key = headers.keyAt(i);
            if (view == headers.get(key)) {
                headers.remove(key);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Remove a footer from adapter.
     *
     * @param view footer view.
     */
    public void removeFooter(View view) {
        for (int i = 0; i < footers.size(); i++) {
            int key = footers.keyAt(i);
            if (view == footers.get(key)) {
                footers.remove(key);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public final int getItemViewType(int position) {
        if (position < getHeadersCount()) {
            return headers.keyAt(position);
        }
        int adjPosition = position - getHeadersCount();
        if (adjPosition < getBasicItemsCount()) {
            return VIEW_TYPE_NORMAL;
        }
        return footers.keyAt(adjPosition - getBasicItemsCount());
    }

    @Override
    public final T onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NORMAL) {
            return onCreateBasicViewHolder(parent, viewType);
        }

        View headerView = headers.get(viewType);
        if (headerView != null) {
            return newHolderInstance(headerView);
        }

        View footerView = footers.get(viewType);
        if (footerView != null) {
            return newHolderInstance(footerView);
        }

        return null;
    }

    @Override
    public final void onBindViewHolder(T holder, int position) {

        int adjPosition = position - getHeadersCount();
        mCursor.moveToPosition(adjPosition);
        if (adjPosition >= 0 && adjPosition < getBasicItemsCount()) {
            onBindBasicViewHolder(holder, adjPosition, mCursor);
        }
    }

    @Override
    public final int getItemCount() {
        return getBasicItemsCount() + getHeadersCount() + getFootersCount();
    }

    protected int getHeadersCount() {
        return headers == null ? 0 : headers.size();
    }

    protected int getFootersCount() {
        return footers == null ? 0 : footers.size();
    }


    /**
     * Override this to create new holder instance.
     *
     * @param view content view.
     * @return instance of HeaderViewHolder
     */
    protected abstract T newHolderInstance(View view);

    /**
     * Override to create viewHolder for basic items.
     *
     * @param parent parent view.
     * @param viewType view type.
     * @return instance of HeaderViewHolder for basic Items.
     */
    protected abstract T onCreateBasicViewHolder(ViewGroup parent, int viewType);

    /**
     * Override to bind basic items.
     *
     * @param holder basic item holder.
     * @param position item position.
     */
    protected abstract void onBindBasicViewHolder(T holder, int position, Cursor cursor);

    /**
     * Override to calculate basic items count.
     *
     * @return basic items count.
     */

    protected int getBasicItemsCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     */
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            Log.d(HeaderViewRecyclerAdapter.class.getSimpleName(), "data changed");
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
    }

}
