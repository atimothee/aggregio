package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import io.aggreg.app.R;
import io.aggreg.app.provider.selectpublisher.SelectPublisherColumns;
import io.aggreg.app.provider.selectpublisher.SelectPublisherCursor;
import io.aggreg.app.ui.adapter.SelectPublisherListCursorAdapter;
import io.aggreg.app.ui.adapter.SelectPublishersAdapter;
import io.aggreg.app.utils.References;

public class SelectPublishersFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    private OnFragmentInteractionListener mListener;
    private static String LOG_TAG = SelectPublishersFragment.class.getSimpleName();
    RecyclerView gridView;
    private FloatingActionButton doneFab;
    private Cursor mCursor;
    View headerView;

    public static SelectPublishersFragment newInstance(String activityType) {
        SelectPublishersFragment selectPublishersFragment = new SelectPublishersFragment();
        Bundle args = new Bundle();
        args.putString(References.ARG_KEY_PUBLISHER_ACTIVITY_TYPE, activityType);
        selectPublishersFragment.setArguments(args);
        return selectPublishersFragment;
    }

    public SelectPublishersFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.PUBLISHER_LOADER, getArguments(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_publishers, container, false);
        doneFab = (FloatingActionButton)rootView.findViewById(R.id.done_fab);
        if(getArguments().getString(References.ARG_KEY_PUBLISHER_ACTIVITY_TYPE).equalsIgnoreCase(References.ACTIVITY_TYPE_SETUP_PUBLISHERS)){
            doneFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp));
        }else if(getArguments().getString(References.ARG_KEY_PUBLISHER_ACTIVITY_TYPE).equalsIgnoreCase(References.ACTIVITY_TYPE_MANAGE_PUBLISHERS)){
            doneFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_done_white_24dp));
        }

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFabClicked();
            }
        });
        gridView = (RecyclerView)rootView.findViewById(android.R.id.list);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        gridView.setLayoutManager(manager);
        //gridView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //gridView.setLayoutManager(new LinearLayoutManager(getActivity()));

        headerView = inflater.inflate(R.layout.publisher_grid_header, null);

        return rootView;
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
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), SelectPublisherColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(LOG_TAG, "pub cursor load finished");
        int mScrollPosition = 0;
        RecyclerView.LayoutManager layoutManager = gridView.getLayoutManager();
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            Log.d(LOG_TAG, "pub cursor position "+mScrollPosition);
        }


        if(data!=null) {
            Log.d(LOG_TAG, "publishers size is " + ((Cursor) data).getCount());
            mCursor = (Cursor) data;
            SelectPublishersAdapter adapter = new SelectPublishersAdapter(getActivity(), mCursor);
            adapter.addHeader(headerView);
            //adapter.addFooter(footerView);
            gridView.setAdapter(adapter);

            if (layoutManager != null) {
//                int count = layoutManager.getChildCount();
//                if (mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count) {
                    layoutManager.scrollToPosition(mScrollPosition);
                    Log.d(LOG_TAG, "pub cursor scrolled to position " + mScrollPosition);
                //}
            }
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

        Log.d(LOG_TAG, "pub cursor load reset");
    }


    public interface OnFragmentInteractionListener {
        void onFabClicked();
    }



}
