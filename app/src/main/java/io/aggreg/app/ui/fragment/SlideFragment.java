package io.aggreg.app.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import io.aggreg.app.R;
import io.aggreg.app.utils.References;


public class SlideFragment extends Fragment {
    public static SlideFragment newInstance(String shortDesc, String longDesc, Uri picUrl){
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putString(References.ARG_KEY_SHORT_DESC, shortDesc);
        args.putString(References.ARG_KEY_LONG_DESC, longDesc);
        args.putString(References.ARG_KEY_PIC_URL, picUrl.toString());
        fragment.setArguments(args);
        return fragment;
    }

    public SlideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_slide, container, false);
        TextView shortDesc = (TextView)view.findViewById(R.id.short_description);
        shortDesc.setText(getArguments().getString(References.ARG_KEY_SHORT_DESC));
        TextView longDesc = (TextView)view.findViewById(R.id.long_description);
        longDesc.setText(getArguments().getString(References.ARG_KEY_LONG_DESC));
        ImageView picture = (ImageView)view.findViewById(R.id.picture);

        picture.setImageURI(Uri.parse(getArguments().getString(References.ARG_KEY_PIC_URL)));
        return view;
    }

}
