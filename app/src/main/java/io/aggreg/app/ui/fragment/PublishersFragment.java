package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.aggreg.app.R;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.PublisherListCursorAdapter;

public class PublishersFragment extends Fragment implements LoaderManager.LoaderCallbacks{

    private OnFragmentInteractionListener mListener;
    private static String[] COLUMNS = {PublisherColumns.NAME, PublisherColumns.IMAGE_URL};
    private static int[] VIEW_IDS = {R.id.publisher_item_name, R.id.publisher_item_logo};
    private static int PUBLISHER_LOADER  = 6;
    private static String LOG_TAG = PublishersFragment.class.getSimpleName();
    RecyclerView gridView;

    public static PublishersFragment newInstance() {
        return new PublishersFragment();
    }

    public PublishersFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(PUBLISHER_LOADER, getArguments(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_publisher_grid, container, false);
        gridView = (RecyclerView)rootView.findViewById(android.R.id.list);
        gridView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        gridView.hea
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
        return new CursorLoader(getActivity(), PublisherColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(LOG_TAG, "publishers size is " + ((Cursor) data).getCount());
        PublisherListCursorAdapter adapter = new PublisherListCursorAdapter(getActivity(), (Cursor)data);
        gridView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public interface OnFragmentInteractionListener {

    }

}
