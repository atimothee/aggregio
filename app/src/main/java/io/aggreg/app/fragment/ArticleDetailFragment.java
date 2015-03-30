package io.aggreg.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.aggreg.app.R;
import io.aggreg.app.ui.widget.CheckableFrameLayout;
import io.aggreg.app.ui.widget.ObservableScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    private static final String TAG = ArticleDetailFragment.class.getName();

    private static final int[] SECTION_HEADER_RES_IDS = {
            R.id.session_links_header,
            R.id.related_videos_header,
    };
    private static final float PHOTO_ASPECT_RATIO = 1.7777777f;

    public static final String VIEW_NAME_PHOTO = "photo";
    private String mTagsString;
    private ViewGroup mRootView;
    private View mScrollViewChild;
    private TextView mTitle;
    private TextView mSubtitle;
    private ObservableScrollView mScrollView;
    private CheckableFrameLayout mAddScheduleButton;

    private TextView mAbstract;
    private LinearLayout mTags;
    private ViewGroup mTagsContainer;
    private View mHeaderBox;
    private View mHeaderContentBox;
    private View mHeaderBackgroundBox;
    private View mHeaderShadow;
    private View mDetailsContainer;
    private int mHeaderTopClearance;
    private int mPhotoHeightPixels;
    private int mHeaderHeightPixels;
    private int mAddScheduleButtonHeightPixels;

    private boolean mHasPhoto;
    private View mPhotoViewContainer;
    private ImageView mPhotoView;
    boolean mGapFillShown;
    private int mSessionColor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleDetailFragment newInstance(String param1, String param2) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
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
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

//    @Override
//    public void onScrollChanged(int deltaX, int deltaY) {
//        final BaseActivity activity = (BaseActivity) getActivity();
//        if (activity == null) {
//            return;
//        }
//
//        // Reposition the header bar -- it's normally anchored to the top of the content,
//        // but locks to the top of the screen on scroll
//        int scrollY = mScrollView.getScrollY();
//
//        float newTop = Math.max(mPhotoHeightPixels, scrollY + mHeaderTopClearance);
//        mHeaderBox.setTranslationY(newTop);
//        mAddScheduleButton.setTranslationY(newTop + mHeaderHeightPixels
//                - mAddScheduleButtonHeightPixels / 2);
//
//        mHeaderBackgroundBox.setPivotY(mHeaderHeightPixels);
//        int gapFillDistance = (int) (mHeaderTopClearance * GAP_FILL_DISTANCE_MULTIPLIER);
//        boolean showGapFill = !mHasPhoto || (scrollY > (mPhotoHeightPixels - gapFillDistance));
//        float desiredHeaderScaleY = showGapFill ?
//                ((mHeaderHeightPixels + gapFillDistance + 1) * 1f / mHeaderHeightPixels)
//                : 1f;
//        if (!mHasPhoto) {
//            mHeaderBackgroundBox.setScaleY(desiredHeaderScaleY);
//        } else if (mGapFillShown != showGapFill) {
//            mHeaderBackgroundBox.animate()
//                    .scaleY(desiredHeaderScaleY)
//                    .setInterpolator(new DecelerateInterpolator(2f))
//                    .setDuration(250)
//                    .start();
//        }
//        mGapFillShown = showGapFill;
//
//        LPreviewUtilsBase lpu = activity.getLPreviewUtils();
//
//        mHeaderShadow.setVisibility(lpu.hasLPreviewAPIs() ? View.GONE : View.VISIBLE);
//
//        if (mHeaderTopClearance != 0) {
//            // Fill the gap between status bar and header bar with color
//            float gapFillProgress = Math.min(Math.max(UIUtils.getProgress(scrollY,
//                    mPhotoHeightPixels - mHeaderTopClearance * 2,
//                    mPhotoHeightPixels - mHeaderTopClearance), 0), 1);
//            lpu.setViewElevation(mHeaderBackgroundBox, gapFillProgress * mMaxHeaderElevation);
//            lpu.setViewElevation(mHeaderContentBox, gapFillProgress * mMaxHeaderElevation + 0.1f);
//            lpu.setViewElevation(mAddScheduleButton, gapFillProgress * mMaxHeaderElevation
//                    + mFABElevation);
//            if (!lpu.hasLPreviewAPIs()) {
//                mHeaderShadow.setAlpha(gapFillProgress);
//            }
//        }
//
//        // Move background photo (parallax effect)
//        mPhotoViewContainer.setTranslationY(scrollY * 0.5f);
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



//    private void tryRenderTags() {
//        if (mTagMetadata == null || mTagsString == null || !isAdded()) {
//            return;
//        }
//
//        if (TextUtils.isEmpty(mTagsString)) {
//            mTagsContainer.setVisibility(View.GONE);
//        } else {
//            mTagsContainer.setVisibility(View.VISIBLE);
//            mTags.removeAllViews();
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            String[] tagIds = mTagsString.split(",");
//
//            List<TagMetadata.Tag> tags = new ArrayList<TagMetadata.Tag>();
//            for (String tagId : tagIds) {
//                if (Config.Tags.SESSIONS.equals(tagId) ||
//                        Config.Tags.SPECIAL_KEYNOTE.equals(tagId)) {
//                    continue;
//                }
//
//                TagMetadata.Tag tag = mTagMetadata.getTag(tagId);
//                if (tag == null) {
//                    continue;
//                }
//
//                tags.add(tag);
//            }
//
//            if (tags.size() == 0) {
//                mTagsContainer.setVisibility(View.GONE);
//                return;
//            }
//
//            Collections.sort(tags, TagMetadata.TAG_DISPLAY_ORDER_COMPARATOR);
//
//            for (final TagMetadata.Tag tag : tags) {
//                TextView chipView = (TextView) inflater.inflate(
//                        R.layout.include_session_tag_chip, mTags, false);
//                chipView.setText(tag.getName());
//
//                if (Config.Tags.CATEGORY_TOPIC.equals(tag.getCategory())) {
//                    ShapeDrawable colorDrawable = new ShapeDrawable(new OvalShape());
//                    colorDrawable.setIntrinsicWidth(mTagColorDotSize);
//                    colorDrawable.setIntrinsicHeight(mTagColorDotSize);
//                    colorDrawable.getPaint().setStyle(Paint.Style.FILL);
//                    chipView.setCompoundDrawablesWithIntrinsicBounds(colorDrawable,
//                            null, null, null);
//                    colorDrawable.getPaint().setColor(tag.getColor());
//                }
//
//                chipView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        getActivity().finish(); // TODO: better encapsulation
//                        Intent intent = new Intent(getActivity(), BrowseSessionsActivity.class)
//                                .putExtra(BrowseSessionsActivity.EXTRA_FILTER_TAG, tag.getId())
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                });
//
//                mTags.addView(chipView);
//            }
//        }
//    }

}
