package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    private TextView articleText;
    private TextView articleTitle;
    private TextView publisherName;
    private RelativeTimeTextView timeAgo;
    private ImageView articleImage;
    private CollapsingToolbarLayout collapsingToolbar;
    public static final String ARG_ARTICLE_ID = "article_id";
    private static int ARTICLE_DETAIL_LOADER = 5;
    private static String LOG_TAG = ArticleDetailFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    public static ArticleDetailFragment newInstance(String articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTICLE_ID, articleId);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_article_detail_, container, false);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        articleText = (TextView) view.findViewById(R.id.article_detail_text);
        publisherName = (TextView) view.findViewById(R.id.article_detail_publisher_name);
        timeAgo = (RelativeTimeTextView) view.findViewById(R.id.article_detail_time_ago);
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
        articleSelection.link(bundle.getString(ARG_ARTICLE_ID));
        Log.d(LOG_TAG, "arg id is " + getArguments().getString(ARG_ARTICLE_ID));
        Log.d(LOG_TAG, "bundle id is " + bundle.getString(ARG_ARTICLE_ID));
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, articleSelection.sel(), articleSelection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


        Cursor c = (Cursor) data;
        Log.d(LOG_TAG, "detail cursor count is " + c.getCount());
        if(c.getCount() != 0){
            c.moveToFirst();
            articleText.setText(c.getString(c.getColumnIndex(ArticleColumns.TEXT)));
            String title = c.getString(c.getColumnIndex(ArticleColumns.TITLE));
            articleTitle.setText(title);
            collapsingToolbar.setTitle(title);
            String imageUrl = c.getString(c.getColumnIndex(ArticleColumns.IMAGE));
            if(imageUrl!=null) {
                articleImage.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(imageUrl).into(articleImage);
            }else {
                articleImage.setVisibility(View.GONE);
            }
            publisherName.setText(c.getString(c.getColumnIndex(PublisherColumns.NAME)));
            timeAgo.setReferenceTime(c.getLong(c.getColumnIndex(ArticleColumns.PUB_DATE)));
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
