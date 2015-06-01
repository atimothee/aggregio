package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    private TextView articleText;
    private TextView publisherName;
    private TextView articleTitle;
    private ImageView articleImage;
    public static final String ARG_ARTICLE_ID = "article_id";
    private static int ARTICLE_DETAIL_LOADER = 2;
    private Cursor mCursor;
    private static String LOG_TAG = ArticleDetailFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    public static ArticleDetailFragment newInstance(Long articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    public ArticleDetailFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ARTICLE_DETAIL_LOADER, getArguments(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCursor = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        articleText = (TextView) view.findViewById(R.id.article_detail_text);
        articleTitle = (TextView) view.findViewById(R.id.article_detail_title);
        articleImage = (ImageView) view.findViewById(R.id.article_detail_image);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public Loader onCreateLoader(int i, Bundle bundle) {
//        if(i == ARTICLE_DETAIL_LOADER) {
//            ArticleSelection selection = new ArticleSelection();
//
//            selection.id(getArguments().getLong(ARG_ARTICLE_ID));
//            Log.d(LOG_TAG, "arg id is " + getArguments().getLong(ARG_ARTICLE_ID));
//            return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, selection.sel(), selection.args(), null);
//        }
//        return null;
        //TODO: Fix bug, why doesnt it return data??
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.id(bundle.getLong(ARG_ARTICLE_ID));
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, new String[]{ArticleColumns.TEXT}, articleSelection.sel(), articleSelection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


        Cursor mCursor = (Cursor) data;
        Log.d(LOG_TAG, "detail cursor count is "+mCursor.getCount());
        if(mCursor.getCount() != 0){
            mCursor.moveToFirst();
            articleText.setText(mCursor.getString(mCursor.getColumnIndex(ArticleColumns.TEXT)));
            articleTitle.setText(mCursor.getString(mCursor.getColumnIndex(ArticleColumns.TITLE)));
            Picasso.with(getActivity()).load(mCursor.getString(mCursor.getColumnIndex(ArticleColumns.IMAGE))).into(articleImage);
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
