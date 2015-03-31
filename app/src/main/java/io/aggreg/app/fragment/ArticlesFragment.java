package io.aggreg.app.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.ArrayUtils;

import io.aggreg.app.R;
import io.aggreg.app.fragment.dummy.DummyContent;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.newssource.NewsSourceColumns;

public class ArticlesFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks {


    private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter mAdapter;
    int[] VIEW_IDS = {R.id.article_item_title, R.id.article_item_image, R.id.article_item_time_ago, R.id.article_item_source_logo, R.id.article_item_source_name};
    String[] foreignFields = {NewsSourceColumns.NAME, CategoryColumns.NAME};
    String[] COLUMNS = ArrayUtils.addAll(ArticleColumns.ALL_COLUMNS, foreignFields);

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticlesFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.article_item, null,
                COLUMNS, VIEW_IDS);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if(view.getId()==R.id.article_item_image){
                    Picasso.with(getActivity()).load(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE))).into((ImageView)view);
                    return true;
                }
                else if(view.getId()==R.id.article_item_title){
                    ((TextView)view).setText(cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE)));
                    return true;
                }
                else if(view.getId()==R.id.article_item_source_name){
                    ((TextView)view).setText(cursor.getString(cursor.getColumnIndex(NewsSourceColumns.NAME)));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
       // ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }



    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mAdapter.swapCursor((Cursor)data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
