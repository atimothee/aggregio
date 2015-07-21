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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.aggreg.app.R;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.adapter.SelectPublishersAdapter;
import io.aggreg.app.utils.References;

public class PublishersFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    private OnFragmentInteractionListener mListener;
    private static String LOG_TAG = PublishersFragment.class.getSimpleName();
    RecyclerView recyclerView;
    private FloatingActionButton doneFab;
    private Cursor mCursor;
    View headerView;
    private GridLayoutManager layoutManager;
    private Parcelable mListState;

    public static PublishersFragment newInstance() {
        PublishersFragment publishersFragment = new PublishersFragment();
        Bundle args = new Bundle();
        publishersFragment.setArguments(args);
        return publishersFragment;
    }

    public PublishersFragment() {
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
        doneFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_done_white_24dp));

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFabClicked();
            }
        });
        recyclerView = (RecyclerView)rootView.findViewById(android.R.id.list);
        final int publisherSpanCount = getActivity().getResources().getInteger(R.integer.publisher_span_count);
        layoutManager = new GridLayoutManager(getActivity(), publisherSpanCount, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? publisherSpanCount : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

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
        return new CursorLoader(getActivity(), PublisherColumns.CONTENT_URI, null, null, null, PublisherColumns.ORDER+" ASC");
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(data!=null) {
            mCursor = (Cursor) data;
            SelectPublishersAdapter adapter = new SelectPublishersAdapter(getActivity(), mCursor);
            adapter.addHeader(headerView);
            mListState = layoutManager.onSaveInstanceState();
            recyclerView.setAdapter(adapter);
            layoutManager.onRestoreInstanceState(mListState);
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public interface OnFragmentInteractionListener {
        void onFabClicked();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if(recyclerView != null) {
            mListState = layoutManager.onSaveInstanceState();

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
}
