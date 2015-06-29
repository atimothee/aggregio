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
import android.support.v7.widget.CardView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleContentValues;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.SettingsActivity;
import io.aggreg.app.utils.References;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks, View.OnClickListener {
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
    private CardView articleRelated1;
    private CardView articleRelated2;
    private CardView articleRelated3;
    private RelativeTimeTextView timeAgoRelated1;
    private RelativeTimeTextView timeAgoRelated2;
    private RelativeTimeTextView timeAgoRelated3;
    private ShareActionProvider mShareActionProvider;
    private String mShareString;
    private int imageWidth;


    private OnFragmentInteractionListener mListener;

    public static ArticleDetailFragment newInstance(String articleLink, Long categoryId, Long articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(References.ARG_KEY_ARTICLE_LINK, articleLink);
        args.putLong(References.ARG_KEY_CATEGORY_ID, categoryId);
        args.putLong(References.ARG_KEY_ARTICLE_ID, articleId);
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
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        imageWidth = (int) (displayMetrics.widthPixels);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(getActivity());
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id));
        tracker.setScreenName("detail screen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
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
        bookmarkFab = (FloatingActionButton) view.findViewById(R.id.bookmark_fab);
        viewOnWeb = (Button) view.findViewById(R.id.btn_view_on_web);
        bookmarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBookmark();
            }
        });
        viewOnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("UX")
                        .setAction("click")
                        .setLabel("open in browser button")
                        .build());
                openInBrowser();
            }
        });
        related1Title = (TextView) view.findViewById(R.id.article_related1_title);
        related2Title = (TextView) view.findViewById(R.id.article_related2_title);
        related3Title = (TextView) view.findViewById(R.id.article_related3_title);
        related1Image = (ImageView) view.findViewById(R.id.article_related1_image);
        related2Image = (ImageView) view.findViewById(R.id.article_related2_image);
        related3Image = (ImageView) view.findViewById(R.id.article_related3_image);
        articleRelated1 = (CardView) view.findViewById(R.id.article_related1);
        articleRelated2 = (CardView) view.findViewById(R.id.article_related2);
        articleRelated3 = (CardView) view.findViewById(R.id.article_related3);
        timeAgoRelated1 = (RelativeTimeTextView) view.findViewById(R.id.article_related1_timeago);
        timeAgoRelated2 = (RelativeTimeTextView) view.findViewById(R.id.article_related2_timeago);
        timeAgoRelated3 = (RelativeTimeTextView) view.findViewById(R.id.article_related3_timeago);
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("40F568795D1384A9EC06ABA81110930E")
                .build();
        mAdView.loadAd(adRequest);

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
        if (i == References.ARTICLE_DETAIL_LOADER) {
            Uri contentUri = Uri.parse(AggregioProvider.CONTENT_URI_BASE + "/" + ArticleColumns.TABLE_NAME + "/" + bundle.getLong(References.ARG_KEY_ARTICLE_ID, 0));
            return new CursorLoader(getActivity(), contentUri, null, null, null, null);
        } else if (i == References.ARTICLE_RELATED_LOADER) {
            ArticleSelection articleSelection = new ArticleSelection();
            String[] COLUMNS = {ArticleColumns._ID, ArticleColumns.TITLE, ArticleColumns.IMAGE, ArticleColumns.CATEGORY_ID, ArticleColumns.PUB_DATE, ArticleColumns.LINK, PublisherColumns.NAME, PublisherColumns.IMAGE_URL};
            articleSelection.categoryId(bundle.getLong(References.ARG_KEY_CATEGORY_ID));
            articleSelection.and();
            articleSelection.linkNot(bundle.getString(References.ARG_KEY_ARTICLE_LINK));
            articleSelection.and();
            articleSelection.publisherFollowing(true);
            return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, COLUMNS, articleSelection.sel(), articleSelection.args(), "RANDOM() LIMIT 3");

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


        if (loader.getId() == References.ARTICLE_DETAIL_LOADER) {

            articleCursor = (Cursor) data;

            if (articleCursor.getCount() != 0) {
                articleCursor.moveToFirst();
                articleText.setText(Html.fromHtml(articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TEXT))));
                //Log.d(LOG_TAG, "link is " + articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK)));
                articleText.setMovementMethod(LinkMovementMethod.getInstance());
                String title = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE));
                articleTitle.setText(title);
                collapsingToolbar.setTitle(title);
                String imageUrl = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.IMAGE));
                if (imageUrl != null) {
                    Picasso.with(getActivity()).load(imageUrl + "=s" + imageWidth).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(articleImage);
                } else {
                    //articleImageFrame.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                }
                Picasso.with(getActivity()).load(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.IMAGE_URL))).fit().centerCrop().into(publisherLogo);
                publisherName.setText(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.NAME)));
                timeAgo.setReferenceTime(articleCursor.getLong(articleCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
                int bookmarked = articleCursor.getInt(articleCursor.getColumnIndex(ArticleColumns.BOOK_MARKED));
                if (bookmarked == 1) {
                    bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
                } else if (bookmarked == 0) {
                    bookmarkFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_bookmark_outline_white_24dp));
                }
                mShareString = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE))
                        + " "
                        + articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK));

                int isRead = articleCursor.getInt(articleCursor.getColumnIndex(ArticleColumns.IS_READ));
                if (isRead == 0) {
                    ArticleSelection articleSelection = new ArticleSelection();
                    articleSelection.link(articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK)));
                    ArticleContentValues articleContentValues = new ArticleContentValues();
                    articleContentValues.putIsRead(true);
                    articleContentValues.update(getActivity().getContentResolver(), articleSelection);

                }
            }
        } else if (loader.getId() == References.ARTICLE_RELATED_LOADER) {

            relatedCursor = (Cursor) data;
            if (relatedCursor != null) {
                if (relatedCursor.getCount() != 0) {
                    relatedCursor.moveToFirst();
                    int i = 0;
                    CardView[] articleRelatedViews = {articleRelated1, articleRelated2, articleRelated3};
                    TextView[] relatedTextViews = {related1Title, related2Title, related3Title};
                    ImageView[] relatedImageViews = {related1Image, related2Image, related3Image};
                    RelativeTimeTextView[] timeAgoRelatedViews = {timeAgoRelated1, timeAgoRelated2, timeAgoRelated3};
                    do {
                        articleRelatedViews[i].setVisibility(View.VISIBLE);
                        relatedTextViews[i].setText(relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.TITLE)));
                        String imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));
                        if (imageUrl != null) {
                            relatedImageViews[i].setVisibility(View.VISIBLE);
                            Picasso.with(getActivity()).load(imageUrl).placeholder(R.drawable.no_img_placeholder).fit().centerCrop().into(relatedImageViews[i]);
                        }
                        articleRelatedViews[i].setOnClickListener(this);
                        timeAgoRelatedViews[i].setReferenceTime(relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
                        i++;
                        if (i > 2) {
                            break;
                        }
                    } while (relatedCursor.moveToNext());
                }
            }

        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClick(View view) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("related")
                .build());
        Intent i = new Intent(getActivity(), ArticleDetailActivity.class);

        switch (view.getId()) {
            case R.id.article_related1:
                relatedCursor.moveToPosition(0);
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                getActivity().startActivity(i);
                break;
            case R.id.article_related2:
                relatedCursor.moveToPosition(1);
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                getActivity().startActivity(i);
                break;
            case R.id.article_related3:
                relatedCursor.moveToPosition(2);
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
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

    private void toggleBookmark() {
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
        ArticleCursor articleCursor = articleSelection.query(getActivity().getContentResolver());
        articleCursor.moveToFirst();
        Boolean bookMarked = articleCursor.getBookMarked();
        if (bookMarked == null) {
            bookMark();
        } else if (bookMarked) {
            unBookMark();

        } else {
            bookMark();
        }
    }

    private void bookMark() {
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
        ArticleContentValues articleContentValues = new ArticleContentValues();
        articleContentValues.putBookMarked(true);
        articleContentValues.update(getActivity().getContentResolver(), articleSelection);
        Toast.makeText(getActivity(), "The article was bookmarked", Toast.LENGTH_SHORT).show();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("bookmark")
                .build());
    }

    private void unBookMark() {
        ArticleSelection articleSelection = new ArticleSelection();
        articleSelection.link(getArguments().getString(References.ARG_KEY_ARTICLE_LINK));
        ArticleContentValues articleContentValues = new ArticleContentValues();
        articleContentValues = new ArticleContentValues();
        articleContentValues.putBookMarked(false);
        articleContentValues.update(getActivity().getContentResolver(), articleSelection);
        Toast.makeText(getActivity(), "The bookmark was removed", Toast.LENGTH_SHORT).show();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("unbookmark")
                .build());
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
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("UX")
                    .setAction("click")
                    .setLabel("settings action")
                    .build());
        } else if (id == R.id.action_open_in_browser) {
            openInBrowser();
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("UX")
                    .setAction("click")
                    .setLabel("open in browser action")
                    .build());
        } else if (id == R.id.action_share) {
            //TODO: Share article
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("UX")
                    .setAction("click")
                    .setLabel("share action")
                    .build());

        }

        return super.onOptionsItemSelected(item);
    }

    private void openInBrowser() {
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

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareString);
        return shareIntent;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
