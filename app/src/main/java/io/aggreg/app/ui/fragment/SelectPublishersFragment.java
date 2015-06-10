package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import io.aggreg.app.ui.adapter.SelectPublisherListCursorAdapter;
import io.aggreg.app.ui.adapter.SelectPublishersAdapter;
import io.aggreg.app.utils.References;

public class SelectPublishersFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    private OnFragmentInteractionListener mListener;
    private static String LOG_TAG = SelectPublishersFragment.class.getSimpleName();
    RecyclerView gridView;

    public static SelectPublishersFragment newInstance() {
        return new SelectPublishersFragment();
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
        gridView = (RecyclerView)rootView.findViewById(android.R.id.list);
        gridView.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        //gridView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //gridView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        if(data!=null) {
            Log.d(LOG_TAG, "publishers size is " + ((Cursor) data).getCount());
            SelectPublishersAdapter adapter = new SelectPublishersAdapter(getActivity(), (Cursor) data);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View headerView = inflater.inflate(R.layout.publisher_grid_header, null);
            View footerView = inflater.inflate(R.layout.publisher_grid_footer, null);
            adapter.addHeader(headerView);
            //adapter.addFooter(footerView);
            gridView.setAdapter(adapter);
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public interface OnFragmentInteractionListener {

    }

    private void togglePublisher(){

    }

}
