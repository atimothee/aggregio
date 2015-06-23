package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.analytics.Tracker;

import java.sql.Ref;

import io.aggreg.app.R;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleContentValues;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.SettingsActivity;
import io.aggreg.app.utils.References;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks, View.OnClickListener{
    private TextView articleText;
    private TextView articleTitle;
    private TextView publisherName;
    private RelativeTimeTextView timeAgo;
    private ImageView articleImage;
    private FrameLayout articleImageFrame;
    private ImageView publisherLogo;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton bookmarkFab;
    private static String LOG_TAG = ArticleDetailFragment.class.getSimpleName();
    private Cursor articleCursor;
    private Cursor relatedCursor;
    private Button viewOnWeb;
    Tracker tracker;
    private ImageView related1Image;
    private ImageView related2Image;
    private ImageView related3Image;
    private TextView related1Title;
    private TextView related2Title;
    private TextView related3Title;
    private RelativeLayout articleRelated1;
    private RelativeLayout articleRelated2;
    private RelativeLayout articleRelated3;
    private RelativeTimeTextView timeAgoRelated1;
    private RelativeTimeTextView timeAgoRelated2;
    private RelativeTimeTextView timeAgoRelated3;
    private ShareActionProvider mShareActionProvider;
    private String mShareString;


    private OnFragmentInteractionListener mListener;
    public static ArticleDetailFragment newInstance(String articleLink, Long categoryId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(References.ARG_KEY_ARTICLE_LINK, articleLink);
        args.putLong(References.ARG_KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public ArticleDetailFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.ARTICLE_DETAIL_LOADER, getArguments(), this);
        getLoaderManager().initLoader(References.ARTICLE_RELATED_LOADER, getArguments(), this);
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
        articleImageFrame = (FrameLayout) view.findViewById(R.id.article_detail_image_frame);
        publisherLogo = (ImageView) view.findViewById(R.id.article_item_publisher_logo);
        bookmarkFab = (FloatingActionButton)view.findViewById(R.id.bookmark_fab);
        viewOnWeb = (Button)view.findViewById(R.id.btn_view_on_web);
        bookmarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBookmark();
            }
        });
        viewOnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInBrowser();
            }
        });
        related1Title = (TextView)view.findViewById(R.id.article_related1_title);
        related2Title = (TextView)view.findViewById(R.id.article_related2_title);
        related3Title = (TextView)view.findViewById(R.id.article_related3_title);
        related1Image = (ImageView)view.findViewById(R.id.article_related1_image);
        related2Image = (ImageView)view.findViewById(R.id.article_related2_image);
        related3Image = (ImageView)view.findViewById(R.id.article_related3_image);
        articleRelated1 = (RelativeLayout)view.findViewById(R.id.article_related1);
        articleRelated2 = (RelativeLayout)view.findViewById(R.id.article_related2);
        articleRelated3 = (RelativeLayout)view.findViewById(R.id.article_related3);
        timeAgoRelated1 = (RelativeTimeTextView)view.findViewById(R.id.article_related1_timeago);
        timeAgoRelated2 = (RelativeTimeTextView)view.findViewById(R.id.article_related2_timeago);
        timeAgoRelated3 = (RelativeTimeTextView)view.findViewById(R.id.article_related3_timeago);
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
        if(i == References.ARTICLE_DETAIL_LOADER) {

            ArticleSelection articleSelection = new ArticleSelection();
            articleSelection.link(bundle.getString(References.ARG_KEY_ARTICLE_LINK));
            articleSelection.limit(1);
            return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, articleSelection.sel(), articleSelection.args(), null);
        }else if(i == References.ARTICLE_RELATED_LOADER){
            ArticleSelection articleSelection = new ArticleSelection();
            String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.TITLE, ArticleColumns.IMAGE, ArticleColumns.CATEGORY_ID, ArticleColumns.PUB_DATE, ArticleColumns.LINK, PublisherColumns.NAME, PublisherColumns.IMAGE_URL};

            Log.d(LOG_TAG, "related cursor category id "+bundle.getLong(References.ARG_KEY_CATEGORY_ID));
            Log.d(LOG_TAG, "related cursor article link " + bundle.getString(References.ARG_KEY_ARTICLE_LINK));
            articleSelection.categoryId(bundle.getLong(References.ARG_KEY_CATEGORY_ID));
            //articleSelection.linkNot(bundle.getString(References.ARG_KEY_ARTICLE_LINK));
            return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, COLUMNS, articleSelection.sel(), articleSelection.args(), "RANDOM() LIMIT 3");

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


        if(loader.getId() == References.ARTICLE_DETAIL_LOADER) {

            articleCursor = (Cursor) data;

            if (articleCursor.getCount() != 0) {
                articleCursor.moveToFirst();
                articleText.setText(Html.fromHtml(articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TEXT))));
                Log.d(LOG_TAG, "link is " + articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK)));
                articleText.setMovementMethod(LinkMovementMethod.getInstance());
                String title = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE));
                articleTitle.setText(title);
                collapsingToolbar.setTitle(title);
                String imageUrl = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.IMAGE));
                if (imageUrl != null) {
                    Glide.with(getActivity()).load(imageUrl).into(articleImage);
                }
                else {
                    //articleImageFrame.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                }
                Glide.with(getActivity()).load(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.IMAGE_URL))).into(publisherLogo);
                publisherName.setText(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.NAME)));
                timeAgo.setReferenceTime(articleCursor.getLong(articleCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
                int bookmarked = articleCursor.getInt(articleCursor.getColumnIndex(ArticleColumns.BOOK_MARKED));
                if (bookmarked == 1) {
                    bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
                } else if (bookmarked == 0) {
                    bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_outline_white_24dp));
                }
                mShareString = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE))
                        +" "
                        +articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK));
            }
        }else if(loader.getId() == References.ARTICLE_RELATED_LOADER){
            Log.d(LOG_TAG, "related cursor load finished");

            relatedCursor = (Cursor) data;
            if(relatedCursor != null){
                Log.d(LOG_TAG, "related cursor count is "+relatedCursor.getCount());
                if(relatedCursor.getCount()!=0){
                    Log.d(LOG_TAG, "related cursor count is "+relatedCursor.getCount());
                    relatedCursor.moveToFirst();
                    int i = 0;
                    RelativeLayout[] articleRelatedViews = {articleRelated1, articleRelated2, articleRelated3};
                    TextView[] relatedTextViews = {related1Title, related2Title, related3Title};
                    ImageView[] relatedImageViews = {related1Image, related2Image, related3Image};
                    RelativeTimeTextView[] timeAgoRelatedViews = {timeAgoRelated1, timeAgoRelated2, timeAgoRelated3};
                    do{

                        Log.d(LOG_TAG, "looped related");
                        relatedTextViews[i].setText(relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.TITLE)));
                        String imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));
                        if(imageUrl != null) {
                            relatedImageViews[i].setVisibility(View.VISIBLE);
                            Glide.with(getActivity()).load(imageUrl).into(relatedImageViews[i]);
                        }
                        articleRelatedViews[i].setOnClickListener(this);
                        timeAgoRelatedViews[i].setReferenceTime(relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
                        i++;
                        if(i>2){
                            break;
                        }
                    }while (relatedCursor.moveToNext());
                }
            }

        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getActivity(), ArticleDetailActivity.class);

        switch (view.getId()){
            case R.id.article_related1:
                relatedCursor.moveToPosition(0);

                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                Log.d(LOG_TAG, "related cursor link " + relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                getActivity().startActivity(i);
                break;
            case R.id.article_related2:
                relatedCursor.moveToPosition(1);
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                Log.d(LOG_TAG, "related cursor link "+relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                getActivity().startActivity(i);
                break;
            case R.id.article_related3:
                relatedCursor.moveToPosition(2);
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                Log.d(LOG_TAG, "related cursor link "+relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                getActivity().startActivity(i);
                break;
            default:
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void toggleBookmark(){
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_article_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(createShareIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }else if(id ==R.id.action_open_in_browser){
                openInBrowser();
        }else if(id==R.id.action_share){
            //TODO: Share article
        }

        return super.onOptionsItemSelected(item);
    }

    private void openInBrowser(){
                String link = getArguments().getString(References.ARG_KEY_ARTICLE_LINK);
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent createShareIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareString);
        return shareIntent;
    }

}
