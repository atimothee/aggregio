package io.aggreg.app.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.adapter.ArticleListCursorAdapter;
import io.aggreg.app.utils.References;

public class ArticlesFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static String LOG_TAG = ArticlesFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ViewSwitcher viewSwitcher;
    public ArticlesFragment() {
    }

    public static ArticlesFragment newInstance(Bundle bundle) {
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
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
        if(getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE)){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        else if (isTablet) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);

        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        viewSwitcher = (ViewSwitcher)view.findViewById(R.id.article_list_view_switcher);
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
        if(viewSwitcher.getCurrentView() != recyclerView){
            viewSwitcher.showNext();
        }
        Parcelable state = recyclerView.getLayoutManager().onSaveInstanceState();
//        Bundle savedState;
//        if(getActivity().getResources().getBoolean(R.bool.isTablet)){
//            savedState = new Bundle();
//            savedState.putIntArray(References.ARG_KEY_SCROLL_POSTIONS, getScrollPositions());
//
//        }
//        else{
//            savedState = new Bundle();
//            savedState.putInt(References.ARG_KEY_SCROLL_POSTION, getScrollPosition());
//
//        }
        ArticleListCursorAdapter adapter = new ArticleListCursorAdapter(getActivity(), (Cursor)data, getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE, false));

        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(state);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private int getScrollPosition(){
        int mScrollPosition = -1;
        if(recyclerView != null){
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
                mScrollPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                Log.d(LOG_TAG, "scroll position is "+mScrollPosition);
            }else if(layoutManager != null && layoutManager instanceof GridLayoutManager){
                mScrollPosition = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
            }
        }
        return mScrollPosition;
    }

    private int[] getScrollPositions(){
        int positions[] = {-1,-1, -1};
        if(recyclerView != null){
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
            if(layoutManager != null){
                int[] mPositions = new int[3];
                layoutManager.findFirstVisibleItemPositions(mPositions);
                return mPositions;
            }
        }

        return positions;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        try {
//            outState.putInt(References.ARG_KEY_SCROLL_POSTION, getScrollPosition());
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if(recyclerView != null) {
            outState.putParcelable(References.ARG_KEY_PARCEL, recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            if (recyclerView != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(References.ARG_KEY_PARCEL));
            }
        }
//        try {
//            if (savedInstanceState != null) {
//                if (recyclerView != null) {
//                if(getActivity().getResources().getBoolean(R.bool.isTablet)){
//                    int[] savedScrollPositions = savedInstanceState.getIntArray(References.ARG_KEY_SCROLL_POSTIONS);
//                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
//
//                    if (layoutManager != null) {
//                        int count = layoutManager.getItemCount();
//                        int itemCount = layoutManager.getItemCount();
//                        Log.d(LOG_TAG, "scroll position count is " + count);
//                        Log.d(LOG_TAG, "scroll position item count is " + itemCount);
//                        int scrollposition = -1;
//                        for(int i = 0; i<savedScrollPositions.length; i++){
//                            if(i < scrollposition || i==0){
//                                scrollposition = i;
//                            }
//                        }
//                        if (scrollposition != RecyclerView.NO_POSITION && scrollposition < count) {
//                            layoutManager.scrollToPosition(scrollposition);
//
//                        }
//                    }
//                }else {
//
//
//                int savedScrollPosition = savedInstanceState.getInt(References.ARG_KEY_SCROLL_POSTION);
//                Log.d(LOG_TAG, "saved scroll position is " + savedScrollPosition);
//
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    if (layoutManager != null) {
//                        int count = layoutManager.getItemCount();
//                        int itemCount = layoutManager.getItemCount();
//                        Log.d(LOG_TAG, "scroll position count is " + count);
//                        Log.d(LOG_TAG, "scroll position item count is " + itemCount);
//                        if (savedScrollPosition != RecyclerView.NO_POSITION && savedScrollPosition < count) {
//                            layoutManager.scrollToPosition(savedScrollPosition);
//
//                        }
//
//                    }
//                }
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}
