//package io.aggreg.app.ui.adapter;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DataSetObserver;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * Created by Timo on 6/10/15.
// */
//public abstract class CursorHeaderRecyclerViewAdapter extends HeaderViewRecyclerAdapter{
//    private Context mContext;
//
//    private Cursor mCursor;
//
//    private boolean mDataValid;
//
//    private int mRowIdColumn;
//
//    private DataSetObserver mDataSetObserver;
//
////    public CursorHeaderRecyclerViewAdapter(Context context, Cursor cursor) {
////        mContext = context;
////        mCursor = cursor;
////        mDataValid = cursor != null;
////        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
////        mDataSetObserver = new NotifyingDataSetObserver();
////        if (mCursor != null) {
////            mCursor.registerDataSetObserver(mDataSetObserver);
////        }
////    }
//
//    public Cursor getCursor() {
//        return mCursor;
//    }
//
//
//    @Override
//    public long getItemId(int position) {
//        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
//            return mCursor.getLong(mRowIdColumn);
//        }
//        return 0;
//    }
//
//    @Override
//    public void setHasStableIds(boolean hasStableIds) {
//        super.setHasStableIds(true);
//    }
//
//    @Override
//    protected RecyclerView.ViewHolder newHolderInstance(View view) {
//        return null;
//    }
//
//    @Override
//    protected RecyclerView.ViewHolder onCreateBasicViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//
//    public abstract void onBindViewHolder(T viewHolder, Cursor cursor);
//
//    @Override
//    protected void onBindBasicViewHolder(T holder, int position) {
//
//        if (!mDataValid) {
//            throw new IllegalStateException("this should only be called when the cursor is valid");
//        }
//        if (!mCursor.moveToPosition(position)) {
//            throw new IllegalStateException("couldn't move cursor to position " + position);
//        }
//        onBindViewHolder(holder, mCursor);
//    }
//
//
//    @Override
//    protected int getBasicItemsCount() {
//        if (mDataValid && mCursor != null) {
//            return mCursor.getCount();
//        }
//        return 0;
//    }
//
//    /**
//     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
//     * closed.
//     */
//    public void changeCursor(Cursor cursor) {
//        Cursor old = swapCursor(cursor);
//        if (old != null) {
//            old.close();
//        }
//    }
//
//    /**
//     * Swap in a new Cursor, returning the old Cursor.  Unlike
//     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
//     * closed.
//     */
//    public Cursor swapCursor(Cursor newCursor) {
//        if (newCursor == mCursor) {
//            return null;
//        }
//        final Cursor oldCursor = mCursor;
//        if (oldCursor != null && mDataSetObserver != null) {
//            oldCursor.unregisterDataSetObserver(mDataSetObserver);
//        }
//        mCursor = newCursor;
//        if (mCursor != null) {
//            if (mDataSetObserver != null) {
//                mCursor.registerDataSetObserver(mDataSetObserver);
//            }
//            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
//            mDataValid = true;
//            notifyDataSetChanged();
//        } else {
//            mRowIdColumn = -1;
//            mDataValid = false;
//            notifyDataSetChanged();
//            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
//        }
//        return oldCursor;
//    }
//
//    private class NotifyingDataSetObserver extends DataSetObserver {
//        @Override
//        public void onChanged() {
//            super.onChanged();
//            mDataValid = true;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public void onInvalidated() {
//            super.onInvalidated();
//            mDataValid = false;
//            notifyDataSetChanged();
//            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
//        }
//    }
//}
