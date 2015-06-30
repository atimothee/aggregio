package io.aggreg.app.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.adapter.ArticleListCursorAdapter;
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
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            //staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.IS_READ, ArticleColumns.TITLE, ArticleColumns.IMAGE, ArticleColumns.CATEGORY_ID,ArticleColumns.PUB_DATE, ArticleColumns.LINK, ArticleColumns.TEXT, PublisherColumns.NAME, PublisherColumns.IMAGE_URL, PublisherColumns.FOLLOWING};
        ArticleSelection selection = new ArticleSelection();

        selection.categoryId(args.getLong(References.ARG_KEY_CATEGORY_ID));
        selection.and();
        selection.publisherFollowing(true);

        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, COLUMNS, selection.sel(), selection.args(), ArticleColumns.TABLE_NAME+"."+ArticleColumns.PUB_DATE+" DESC");
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        int mScrollPosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //TODO: What if its grid layout
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            Log.d(LOG_TAG, "pub cursor position " + mScrollPosition);
        }
        ArticleListCursorAdapter adapter = new ArticleListCursorAdapter(getActivity(), (Cursor)data);
        if (layoutManager != null) {
//                int count = layoutManager.getChildCount();
//                if (mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count) {
            layoutManager.scrollToPosition(mScrollPosition);
            Log.d(LOG_TAG, "pub cursor scrolled to position " + mScrollPosition);
            //}
        }

        recyclerView.setAdapter(adapter);
        Cursor c = (Cursor)data;
        if(c!=null){
            Log.d(LOG_TAG, "article cursor count is "+c.getCount());
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


}
