package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.sql.Ref;

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
    private Boolean isTablet;
    private Parcelable mListState;
    private RecyclerView.LayoutManager layoutManager;
    private TextView noArticlesMessage;
    private FrameLayout articleListFrameLayout;

    private OnFragmentInteractionListener mListener;
    private Boolean switched;

    public ArticlesFragment() {
    }

    public static ArticlesFragment newInstance(Bundle bundle) {
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isTablet = getResources().getBoolean(R.bool.isTablet);
        this.switched = false;
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
            layoutManager = new LinearLayoutManager(getActivity());
        }
        else if (isTablet) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(getActivity().getResources().getInteger(R.integer.span_count), StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            layoutManager = staggeredGridLayoutManager;

        }else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recyclerView.setLayoutManager(layoutManager);
        viewSwitcher = (ViewSwitcher)view.findViewById(R.id.article_list_view_switcher);
        noArticlesMessage = (TextView)view.findViewById(R.id.text_view_no_article_message);
        if(getArguments().getBoolean(References.ARG_KEY_IS_BOOKMARKS)){
            noArticlesMessage.setText("You haven't yet bookmarked any articles!");
        }else {
            noArticlesMessage.setText("No articles to show! Hit the refresh button at the top to get the latest news");
        }
        articleListFrameLayout = (FrameLayout)view.findViewById(R.id.article_list_frame_layout);

        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.IS_READ, ArticleColumns.TITLE, ArticleColumns.IMAGE, ArticleColumns.CATEGORY_ID,ArticleColumns.PUB_DATE, ArticleColumns.LINK, ArticleColumns.TEXT, PublisherColumns.NAME, PublisherColumns.IMAGE_URL, PublisherColumns.FOLLOWING};
        ArticleSelection selection = new ArticleSelection();
        if(getArguments().getBoolean(References.ARG_KEY_IS_BOOKMARKS)){
            selection.bookMarked(true);
        }else {
            selection.categoryId(args.getLong(References.ARG_KEY_CATEGORY_ID));
            selection.and();
            selection.publisherFollowing(true);
        }
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, COLUMNS, selection.sel(), selection.args(), ArticleColumns.TABLE_NAME+"."+ArticleColumns.PUB_DATE+" DESC");
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        //switching logic
        Cursor c = (Cursor) data;
        if(c!= null){
            if(c.getCount() != 0){
                recyclerView.setVisibility(View.VISIBLE);
                noArticlesMessage.setVisibility(View.GONE);
                if (switched==false) {
                    viewSwitcher.showNext();
                    switched = true;
                }
            }else {
                if (getArguments().getBoolean(References.ARG_KEY_IS_BOOKMARKS, false)) {
                    if (switched==false) {
                        viewSwitcher.showNext();
                        switched = true;
                    }
                        recyclerView.setVisibility(View.GONE);
                        noArticlesMessage.setVisibility(View.VISIBLE);



                }else {
                    if(mListener.checkSyncStatus()){
                        //leave progress
                        Log.d(LOG_TAG, "is syncing");
                    }else {
                        Log.d(LOG_TAG, "is not syncing");
                        if (switched==false) {
                            viewSwitcher.showNext();
                            switched = true;
                        }
                        recyclerView.setVisibility(View.GONE);
                        noArticlesMessage.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


        if(getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE)){
            mListState = ((LinearLayoutManager)layoutManager).onSaveInstanceState();
        }
        else if (isTablet) {
            mListState = ((StaggeredGridLayoutManager)layoutManager).onSaveInstanceState();

        }else {
            mListState = ((LinearLayoutManager)layoutManager).onSaveInstanceState();
        }

        ArticleListCursorAdapter adapter = new ArticleListCursorAdapter(getActivity(),
                (Cursor)data, getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE, false),
                getArguments().getBoolean(References.ARG_KEY_IS_BOOKMARKS, false));

        recyclerView.setAdapter(adapter);
        layoutManager.onRestoreInstanceState(mListState);
        if(getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE)){
            try {
                recyclerView.scrollToPosition(getArguments().getInt(References.ARG_KEY_CURSOR_POSITION));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Log.d(LOG_TAG, "load finished");

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if(recyclerView != null) {
            if(getArguments().getBoolean(References.ARG_KEY_IS_TAB_TWO_PANE)){
                mListState = ((LinearLayoutManager)layoutManager).onSaveInstanceState();
            }
            else if (isTablet) {
                mListState = ((StaggeredGridLayoutManager)layoutManager).onSaveInstanceState();

            }else {
                mListState = ((LinearLayoutManager)layoutManager).onSaveInstanceState();
            }
            outState.putParcelable(References.ARG_KEY_PARCEL, mListState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {

                mListState = savedInstanceState.getParcelable(References.ARG_KEY_PARCEL);

        }
    }

    @Override
    public void onResume() {

        super.onResume();
        if (recyclerView != null) {
            if(mListState!=null) {
                layoutManager.onRestoreInstanceState(mListState);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        Boolean checkSyncStatus();
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
