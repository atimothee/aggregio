package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
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
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;

public class ArticlesFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks {

    private SimpleCursorAdapter mAdapter;
    private int[] VIEW_IDS = {R.id.article_item_title, R.id.article_item_image, R.id.article_item_time_ago, R.id.article_item_source_logo, R.id.article_item_source_name};
    private String[] foreignFields = {PublisherColumns.NAME, CategoryColumns.NAME};
    private String[] COLUMNS = ArrayUtils.addAll(ArticleColumns.ALL_COLUMNS, foreignFields);
    public static String ARG_PARAM_CATEGORY_ID = "category_id";
    public static int ARTICLE_LOADER = 1;
    private AbsListView mListView;
    private OnFragmentInteractionListener mListener;
    public ArticlesFragment() {
    }


    public static ArticlesFragment newInstance(Long categoryId) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        public void openArticleDetail(Long articleId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ARTICLE_LOADER, getArguments(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.article_item_, null,
                COLUMNS, VIEW_IDS, 0);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if (view.getId() == R.id.article_item_image) {
                    Picasso.with(getActivity()).load(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE))).into((ImageView) view);
                    return true;
                } else if (view.getId() == R.id.article_item_title) {
                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE)));
                    return true;
                } else if (view.getId() == R.id.article_item_source_name) {
                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex(PublisherColumns.NAME)));
                    return true;
                }
                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        //View view = inflater.inflate(R.layout.article_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        //mListView = (ListView) view.findViewById(R.id.listview);

        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        //TODO: get only required columns

        ArticleSelection selection = new ArticleSelection();
        selection.categoryId(args.getLong(ARG_PARAM_CATEGORY_ID));
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, selection.sel(), selection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        mAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = mAdapter.getCursor();
        mListener.openArticleDetail(cursor.getLong(cursor.getColumnIndex(ArticleColumns._ID)));
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
}
