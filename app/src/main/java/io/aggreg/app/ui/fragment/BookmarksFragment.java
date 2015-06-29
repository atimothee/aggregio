package io.aggreg.app.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.adapter.ArticleListCursorAdapter;
import io.aggreg.app.utils.References;

public class BookmarksFragment extends Fragment implements LoaderManager.LoaderCallbacks {
    //TODO: if list is empty, display text or card you havent yet bookmarked

    private static String LOG_TAG = BookmarksFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    public BookmarksFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.BOOKMARKS_LOADER, getArguments(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.CATEGORY_ID, ArticleColumns.TITLE, ArticleColumns.IS_READ, ArticleColumns.IMAGE, ArticleColumns.PUB_DATE, ArticleColumns.LINK, ArticleColumns.TEXT,PublisherColumns.NAME, PublisherColumns.IMAGE_URL, PublisherColumns.FOLLOWING};
        ArticleSelection selection = new ArticleSelection();
        selection.bookMarked(true);
        selection.and();
        selection.publisherFollowing(true);
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, COLUMNS, selection.sel(), selection.args(), ArticleColumns.TABLE_NAME+"."+ArticleColumns.PUB_DATE+" DESC");
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        ArticleListCursorAdapter adapter = new ArticleListCursorAdapter(getActivity(), (Cursor)data);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

}
