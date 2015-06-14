package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.analytics.Tracker;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleContentValues;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.utils.References;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    private TextView articleText;
    private TextView articleTitle;
    private TextView publisherName;
    private RelativeTimeTextView timeAgo;
    private ImageView articleImage;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton bookmarkFab;
    private static String LOG_TAG = ArticleDetailFragment.class.getSimpleName();
    private Cursor articlesCursor;
    Tracker tracker;


    private OnFragmentInteractionListener mListener;
    public static ArticleDetailFragment newInstance(String articleLink) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(References.ARG_KEY_ARTICLE_LINK, articleLink);
        fragment.setArguments(args);
        return fragment;
    }

    public ArticleDetailFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.ARTICLE_DETAIL_LOADER, getArguments(), this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        articleText = (TextView) view.findViewById(R.id.article_detail_text);
        publisherName = (TextView) view.findViewById(R.id.article_detail_publisher_name);
        timeAgo = (RelativeTimeTextView) view.findViewById(R.id.article_detail_time_ago);
        articleTitle = (TextView) view.findViewById(R.id.article_detail_title);
        articleImage = (ImageView) view.findViewById(R.id.article_detail_image);
        bookmarkFab = (FloatingActionButton)view.findViewById(R.id.bookmark_fab);
        bookmarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Bookmark article, then notify dataset changed
                toggleBookmark();
            }
        });
        return view;
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

        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(bundle.getString(References.ARG_KEY_ARTICLE_LINK));
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, articleSelection.sel(), articleSelection.args(), null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {



        articlesCursor = (Cursor) data;

        if(articlesCursor.getCount() != 0){
            articlesCursor.moveToFirst();
            articleText.setText(Html.fromHtml(articlesCursor.getString(articlesCursor.getColumnIndex(ArticleColumns.TEXT))));
            Log.d(LOG_TAG, "html is " + articlesCursor.getString(articlesCursor.getColumnIndex(ArticleColumns.TEXT)));
            articleText.setMovementMethod(LinkMovementMethod.getInstance());
            String title = articlesCursor.getString(articlesCursor.getColumnIndex(ArticleColumns.TITLE));
            articleTitle.setText(title);
            collapsingToolbar.setTitle(title);
            String imageUrl = articlesCursor.getString(articlesCursor.getColumnIndex(ArticleColumns.IMAGE));
            if(imageUrl!=null) {
                articleImage.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(imageUrl).into(articleImage);
            }else {
                articleImage.setVisibility(View.GONE);
            }
            publisherName.setText(articlesCursor.getString(articlesCursor.getColumnIndex(PublisherColumns.NAME)));
            timeAgo.setReferenceTime(articlesCursor.getLong(articlesCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
            int bookmarked = articlesCursor.getInt(articlesCursor.getColumnIndex(ArticleColumns.BOOK_MARKED));
            if(bookmarked == 1){
                bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
            }else if(bookmarked == 0){
                bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_outline_white_24dp));
            }
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void toggleBookmark(){
        ArticleContentValues articleContentValues;
        ArticleSelection articleSelection = new ArticleSelection();
        ArticleCursor articleCursor = articleSelection.query(getActivity().getContentResolver());
        articleCursor.moveToFirst();
        Boolean bookMarked = articleCursor.getBookMarked();
        if(bookMarked == null) {
            bookMark();
        }
        else if(bookMarked) {
            unBookMark();

        }else {
            bookMark();
        }
    }

    private void bookMark(){
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
        ArticleContentValues articleContentValues = new ArticleContentValues();
        articleContentValues.putBookMarked(true);
        articleContentValues.update(getActivity().getContentResolver(), articleSelection);
        Toast.makeText(getActivity(), "The article was bookmarked", Toast.LENGTH_SHORT).show();
    }

    private void unBookMark(){
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
        ArticleContentValues articleContentValues = new ArticleContentValues();
        articleContentValues = new ArticleContentValues();
        articleContentValues.putBookMarked(false);
        articleContentValues.update(getActivity().getContentResolver(), articleSelection);
        Toast.makeText(getActivity(), "The bookmark was removed", Toast.LENGTH_SHORT).show();
    }

}
