package io.aggreg.app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.aggreg.app.R;
import io.aggreg.app.provider.AggregioProvider;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.article.ArticleContentValues;
import io.aggreg.app.provider.article.ArticleCursor;
import io.aggreg.app.provider.article.ArticleSelection;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.publisher.PublisherColumns;
import io.aggreg.app.ui.ArticleDetailActivity;
import io.aggreg.app.ui.SettingsActivity;
import io.aggreg.app.utils.NetworkUtils;
import io.aggreg.app.utils.References;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks, View.OnClickListener {
    private TextView articleText;
    private TextView articleTitle;
    private TextView publisherName;
    private RelativeTimeTextView timeAgo;
    private SimpleDraweeView articleImage;
    private SimpleDraweeView publisherLogo;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton bookmarkFab;
    private static String LOG_TAG = ArticleDetailFragment.class.getSimpleName();
    private Cursor articleCursor;
    private Cursor relatedCursor;
    private Button viewOnWeb;
    private Button bookmarkButton;
    Tracker tracker;
    private SimpleDraweeView related1Image;
    private SimpleDraweeView related2Image;
    private SimpleDraweeView related3Image;
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
    private ViewSwitcher viewSwitcher;
    private boolean isTablet;
    private ProgressBar progressBar;
    private FrameLayout articleImageFrame;
    private Boolean hasNextBeenShown;


    private OnFragmentInteractionListener mListener;


    public static ArticleDetailFragment newInstance(Bundle bundle) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(bundle);
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public ArticleDetailFragment() {
        this.hasNextBeenShown = false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(References.ARTICLE_DETAIL_LOADER, getArguments(), this);
        if(!isTablet) {
            getLoaderManager().initLoader(References.ARTICLE_RELATED_LOADER, getArguments(), this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isTablet = getActivity().getResources().getBoolean(R.bool.isTablet);
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        imageWidth = (int) (displayMetrics.widthPixels);
        if(isTablet){
            imageWidth = (int) (displayMetrics.widthPixels);
        }

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(getActivity());
        tracker = analytics.newTracker(getString(R.string.analytics_tracker_id));
        tracker.setScreenName("detail screen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(isTablet){
            view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        }else {
            if(getArguments().getBoolean(References.ARG_KEY_ARTICLE_HAS_IMAGE, false)){
                view = inflater.inflate(R.layout.fragment_article_detail, container, false);
            }else{
                view = inflater.inflate(R.layout.no_image_fragment_article_detail, container, false);
            }
        }



        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if(toolbar != null){
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).setTitle(null);
            try {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        if(collapsingToolbar !=null){
            collapsingToolbar.setTitle(null);
        }
        articleText = (TextView) view.findViewById(R.id.article_detail_text);
        publisherName = (TextView) view.findViewById(R.id.article_detail_publisher_name);
        timeAgo = (RelativeTimeTextView) view.findViewById(R.id.article_detail_time_ago);
        articleTitle = (TextView) view.findViewById(R.id.article_detail_title);
        articleImage = (SimpleDraweeView) view.findViewById(R.id.article_detail_image);
        publisherLogo = (SimpleDraweeView) view.findViewById(R.id.article_item_publisher_logo);


        viewOnWeb = (Button) view.findViewById(R.id.btn_view_on_web);
        if(isTablet) {
            bookmarkFab = mListener.getBookmarkFab();
            bookmarkButton = (Button) view.findViewById(R.id.btn_bookmark);
            if (bookmarkButton != null) {
                bookmarkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggleBookmark();
                    }
                });
            }
        }else {
            bookmarkFab = (FloatingActionButton) view.findViewById(R.id.bookmark_fab);
        }
        if(bookmarkFab != null) {
            bookmarkFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleBookmark();
                }
            });
        }
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
        if(!isTablet) {
            related1Title = (TextView) view.findViewById(R.id.article_related1_title);
            related2Title = (TextView) view.findViewById(R.id.article_related2_title);
            related3Title = (TextView) view.findViewById(R.id.article_related3_title);
            related1Image = (SimpleDraweeView) view.findViewById(R.id.article_related1_image);
            related2Image = (SimpleDraweeView) view.findViewById(R.id.article_related2_image);
            related3Image = (SimpleDraweeView) view.findViewById(R.id.article_related3_image);
            articleRelated1 = (CardView) view.findViewById(R.id.article_related1);
            articleRelated2 = (CardView) view.findViewById(R.id.article_related2);
            articleRelated3 = (CardView) view.findViewById(R.id.article_related3);
            timeAgoRelated1 = (RelativeTimeTextView) view.findViewById(R.id.article_related1_timeago);
            timeAgoRelated2 = (RelativeTimeTextView) view.findViewById(R.id.article_related2_timeago);
            timeAgoRelated3 = (RelativeTimeTextView) view.findViewById(R.id.article_related3_timeago);
        }

        viewSwitcher = (ViewSwitcher)view.findViewById(R.id.detail_view_switcher);
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        if(isTablet){
            Toolbar toolbar2 = (Toolbar)view.findViewById(R.id.toolbar2);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar2);
            articleImageFrame = (FrameLayout)view.findViewById(R.id.article_detail_image_frame);
        }
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
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean shouldShowImageOnlyOnWifi = settings.getBoolean(getActivity().getString(R.string.key_images_on_wifi_only), false);
        Boolean isOnWifi = new NetworkUtils(getActivity()).isWIFIAvailable();


        if (loader.getId() == References.ARTICLE_DETAIL_LOADER) {

            articleCursor = (Cursor) data;

            if (articleCursor.getCount() != 0) {
                articleCursor.moveToFirst();

                articleText.setText(Html.fromHtml(articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TEXT))));
                articleText.setMovementMethod(LinkMovementMethod.getInstance());
                final String title = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE));
                articleTitle.setText(title);

                if(isTablet) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
                    String mainToolbarTitle;
                    if(getArguments().getBoolean(References.ARG_KEY_IS_BOOKMARKS)){
                        mainToolbarTitle = "Bookmarks";
                    }else {
                        mainToolbarTitle = articleCursor.getString(articleCursor.getColumnIndex(CategoryColumns.NAME));
                    }
                    mListener.updateTitle(mainToolbarTitle);


                }
                String imageUrl = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.IMAGE));
                if (imageUrl != null) {
                    if(shouldShowImageOnlyOnWifi){
                        if(isOnWifi){
                            Uri uri = Uri.parse(imageUrl + "=s" + imageWidth);

                            articleImage.setImageURI(uri);
                            if (collapsingToolbar != null) {
                                collapsingToolbar.setTitle(null);
                            }


                        }
                        else {
                            if(articleImageFrame != null){
                                articleImageFrame.setVisibility(View.GONE);
                            }
                        }
                    }
                    else {
                        Uri uri = Uri.parse(imageUrl + "=s" + imageWidth);

                        articleImage.setImageURI(uri);
                        if (collapsingToolbar != null) {
                            collapsingToolbar.setTitle(null);
                        }

                    }

                   } else {
                        if(articleImageFrame != null){
                            articleImageFrame.setVisibility(View.GONE);

                    }

                }
                String publisherImageUrl = articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.IMAGE_URL));
                if(publisherImageUrl!=null) {
                    Uri uri = Uri.parse(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.IMAGE_URL)));
                    publisherLogo.setImageURI(uri);
                }
                publisherName.setText(articleCursor.getString(articleCursor.getColumnIndex(PublisherColumns.NAME)));
                timeAgo.setReferenceTime(articleCursor.getLong(articleCursor.getColumnIndex(ArticleColumns.PUB_DATE)));
                int bookmarked = articleCursor.getInt(articleCursor.getColumnIndex(ArticleColumns.BOOK_MARKED));
                Drawable normalDrawable;
                Drawable wrapDrawable;
                if (bookmarked == 1) {
                    normalDrawable = getResources().getDrawable(R.drawable.ic_bookmark_black_24dp);
                    wrapDrawable = DrawableCompat.wrap(normalDrawable);

                } else {
                    normalDrawable = getResources().getDrawable(R.drawable.ic_bookmark_outline_white_24dp);
                    wrapDrawable = DrawableCompat.wrap(normalDrawable);
                }
                DrawableCompat.setTint(wrapDrawable, Color.parseColor("#ffffff"));
                bookmarkFab.setImageDrawable(wrapDrawable);

                mShareString = articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.TITLE))
                        + " "
                        + articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK));
                setShareIntent(createShareIntent());



                int isRead = articleCursor.getInt(articleCursor.getColumnIndex(ArticleColumns.IS_READ));
                if (isRead == 0) {
                    ArticleSelection articleSelection = new ArticleSelection();
                    articleSelection.link(articleCursor.getString(articleCursor.getColumnIndex(ArticleColumns.LINK)));
                    ArticleContentValues articleContentValues = new ArticleContentValues();
                    articleContentValues.putIsRead(true);
                    articleContentValues.update(getActivity().getContentResolver(), articleSelection);

                }
            }
            if(!hasNextBeenShown) {
                if (viewSwitcher.getNextView() != progressBar) {
                    hasNextBeenShown = true;
                    viewSwitcher.showNext();
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
                    SimpleDraweeView[] relatedImageViews = {related1Image, related2Image, related3Image};
                    RelativeTimeTextView[] timeAgoRelatedViews = {timeAgoRelated1, timeAgoRelated2, timeAgoRelated3};
                    do {
                        articleRelatedViews[i].setVisibility(View.VISIBLE);
                        relatedTextViews[i].setText(relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.TITLE)));
                        String imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));

                        if (imageUrl != null) {
                            Uri uri = Uri.parse(imageUrl);
                            if(shouldShowImageOnlyOnWifi){
                                if(isOnWifi){
                                    relatedImageViews[i].setVisibility(View.VISIBLE);
                                    relatedImageViews[i].setImageURI(uri);
                                }else {
                                    relatedImageViews[i].setVisibility(View.GONE);
                                }

                            }else {
                                relatedImageViews[i].setVisibility(View.VISIBLE);
                                relatedImageViews[i].setImageURI(uri);

                            }
                        }
                        else {
                            relatedImageViews[i].setVisibility(View.GONE);
                        }
                        articleRelatedViews[i].setOnClickListener(this);
                        articleRelatedViews[i].setPreventCornerOverlap(false);
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
        String imageUrl = null;
        boolean hasImage = false;

        switch (view.getId()) {
            case R.id.article_related1:
                relatedCursor.moveToPosition(0);
                imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));
                hasImage = false;
                if(imageUrl != null){
                    hasImage = true;
                }
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_HAS_IMAGE, hasImage);
                getActivity().startActivity(i);
                break;
            case R.id.article_related2:
                relatedCursor.moveToPosition(1);
                imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));
                hasImage = false;
                if(imageUrl != null){
                    hasImage = true;
                }
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_HAS_IMAGE, hasImage);
                getActivity().startActivity(i);
                break;
            case R.id.article_related3:
                relatedCursor.moveToPosition(2);
                imageUrl = relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.IMAGE));
                hasImage = false;
                if(imageUrl != null){
                    hasImage = true;
                }
                i.putExtra(References.ARG_KEY_ARTICLE_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns._ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_LINK, relatedCursor.getString(relatedCursor.getColumnIndex(ArticleColumns.LINK)));
                i.putExtra(References.ARG_KEY_CATEGORY_ID, relatedCursor.getLong(relatedCursor.getColumnIndex(ArticleColumns.CATEGORY_ID)));
                i.putExtra(References.ARG_KEY_ARTICLE_HAS_IMAGE, hasImage);
                getActivity().startActivity(i);
                break;
            default:
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        void updateTitle(String title);

        FloatingActionButton getBookmarkFab();
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
            return true;
        } else if (id == R.id.action_open_in_browser) {
            openInBrowser();
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("UX")
                    .setAction("click")
                    .setLabel("open in browser action")
                    .build());
            return true;
        } else if (id == R.id.action_share) {
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("UX")
                    .setAction("click")
                    .setLabel("share action")
                    .build());
            return true;

        } else if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        else if(id == android.R.id.home){
            getActivity().finish();
            return true;
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
        }else {
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
