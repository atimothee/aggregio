package io.aggreg.app.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.ArrayUtils;

import io.aggreg.app.R;
import io.aggreg.app.fragment.dummy.DummyContent;
import io.aggreg.app.provider.article.ArticleColumns;
import io.aggreg.app.provider.category.CategoryColumns;
import io.aggreg.app.provider.newssource.NewsSourceColumns;

public class ArticlesFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks {


    private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter mAdapter;
    int[] VIEW_IDS = {R.id.article_item_title, R.id.article_item_image, R.id.article_item_time_ago, R.id.article_item_source_logo, R.id.article_item_source_name};
    String[] foreignFields = {NewsSourceColumns.NAME, CategoryColumns.NAME};
    String[] COLUMNS = ArrayUtils.addAll(ArticleColumns.ALL_COLUMNS, foreignFields);

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    private View mFakeHeader;
    private View mHeader;
    private int mHeaderHeight;
    private KenBurnsView mHeaderPicture;
    private ImageView mHeaderLogo;
    private int mMinHeaderTranslation;
    private TypedValue mTypedValue = new TypedValue();
    private int mActionBarHeight;
    private AccelerateDecelerateInterpolator mSmoothInterpolator = new AccelerateDecelerateInterpolator();

    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    private Toolbar toolbar;
    private TextView title;
    private ImageView icon;
    private int mActionBarTitleColor;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticlesFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmoothInterpolator = new AccelerateDecelerateInterpolator();
//        final ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
//        actionBar.setIcon(R.drawable.ic_launcher);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.article_item, null,
                COLUMNS, VIEW_IDS);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if(view.getId()==R.id.article_item_image){
                    Picasso.with(getActivity()).load(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE))).into((ImageView)view);
                    return true;
                }
                else if(view.getId()==R.id.article_item_title){
                    ((TextView)view).setText(cursor.getString(cursor.getColumnIndex(ArticleColumns.TITLE)));
                    return true;
                }
                else if(view.getId()==R.id.article_item_source_name){
                    ((TextView)view).setText(cursor.getString(cursor.getColumnIndex(NewsSourceColumns.NAME)));
                    return true;
                }
                return false;
            }
        });
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_article, container, false);
        View view = inflater.inflate(R.layout.article_list, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((ActionBarActivity)getActivity()).setSupportActionBar(toolbar);
        icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        title = (TextView) view.findViewById(R.id.title);

        // Set the adapter
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView = (ListView) view.findViewById(R.id.listview);

       // ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mHeader = view.findViewById(R.id.header);
        mHeaderPicture = (KenBurnsView) view.findViewById(R.id.header_picture);
        mHeaderPicture.setImageResource(R.drawable.ic_launcher);
        //mHeaderPicture.setResourceIds(R.drawable.picture0, R.drawable.picture1);
        mHeaderLogo = (ImageView) view.findViewById(R.id.header_logo);
        mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        mSpannableString = new SpannableString("Title");
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        mFakeHeader = inflater.inflate(R.layout.fake_header, mListView, false);
        mListView.addHeaderView(mFakeHeader);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();
                //sticky actionbar
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                //header_logo --> actionbar icon
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
                View actionBarIconView = getActionBarIconView();
                Log.d("ab", "view is "+actionBarIconView.toString());
                Log.d("ab", "view is "+mHeaderLogo.toString());
                mSmoothInterpolator = new AccelerateDecelerateInterpolator();
                Log.d("ab", "view is "+mSmoothInterpolator.toString());
                if(actionBarIconView!=null) {
                    interpolate(mHeaderLogo, actionBarIconView, mSmoothInterpolator.getInterpolation(ratio));
                }
                //actionbar title alpha
                //getActionBarTitleView().setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
                //---------------------------------
                //better way thanks to @cyrilmottier
                setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });

        return view;
    }

    private ImageView getActionBarIconView() {
//        int id_home =  getActivity().getResources().getIdentifier("home", "id", "android.support.v7.appcompat");
//
//        Log.d("home", "home id is "+id_home);
//        return (ImageView)getActivity().findViewById(id_home);
        return icon;
    }

    private void setTitleAlpha(float alpha) {
//        mAlphaForegroundColorSpan.setAlpha(alpha);
//        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        getActivity().setTitle(mSpannableString);
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(mSpannableString);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min,Math.min(value, max));
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

        view1.setTranslationX(translationX);
        view1.setTranslationY(translationY - mHeader.getTranslationY());
        view1.setScaleX(scaleX);
        view1.setScaleY(scaleY);
    }
    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }



    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ArticleColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mAdapter.swapCursor((Cursor) data);
        Cursor cursor = (Cursor)data;
        if(cursor.getCount()!=0) {
            cursor.moveToPosition(1);
            try {
                mHeaderPicture.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(ArticleColumns.IMAGE))));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mFakeHeader.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }

}
