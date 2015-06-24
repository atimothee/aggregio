package io.aggreg.app.ui.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.adapter.ArticleListCursorAdapter;
import io.aggreg.app.utils.AccountUtils;
import io.aggreg.app.utils.References;

public class ArticlesFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static String LOG_TAG = ArticlesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    public ArticlesFragment() {
    }


    public static ArticlesFragment newInstance(Long categoryId) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putLong(References.ARG_KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.ARTICLES_LOADER, getArguments(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.TITLE, ArticleColumns.IMAGE, ArticleColumns.CATEGORY_ID,ArticleColumns.PUB_DATE, ArticleColumns.LINK, PublisherColumns.NAME, PublisherColumns.IMAGE_URL, PublisherColumns.FOLLOWING};
        ArticleSelection selection = new ArticleSelection();
        //selection.publisherFollowing(false);
        selection.categoryId(args.getLong(References.ARG_KEY_CATEGORY_ID));

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
