package io.aggreg.app;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Timo on 4/15/15.
 */
public class ToolbarNavigationUtil {
    public static View getToolbarNavigationIcon ( Toolbar toolbar ){
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils . isEmpty(toolbar . getNavigationContentDescription());
        String contentDescription = ! hadContentDescription ?  toolbar . getNavigationContentDescription().toString() : " navigationIcon " ;
        toolbar . setNavigationContentDescription(contentDescription);
        ArrayList< View > potentialViews = new ArrayList< View > ();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar . findViewsWithText(potentialViews,contentDescription, View . FIND_VIEWS_WITH_CONTENT_DESCRIPTION );
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null ;
        if (potentialViews . size() > 0 ){
            navIcon = potentialViews . get( 0 ); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar . setNavigationContentDescription( null );
        return navIcon;
    }

    public static TextView getToolbarTitleView ( ActionBarActivity activity , Toolbar toolbar ){
        ActionBar actionBar = activity . getSupportActionBar();
        CharSequence actionbarTitle = null ;
        if (actionBar != null )
            actionbarTitle = actionBar . getTitle();
        actionbarTitle = TextUtils. isEmpty(actionbarTitle) ?  toolbar . getTitle() : actionbarTitle;
        if ( TextUtils . isEmpty(actionbarTitle)) return null ;
        // can't find if title not set
        for ( int i = 0 ; i < toolbar . getChildCount(); i ++ ){
            View v = toolbar . getChildAt(i);
            if (v != null && v instanceof TextView ){
                TextView t = ( TextView ) v;
                CharSequence title = t . getText();
                if ( ! TextUtils . isEmpty(title) && actionbarTitle . equals(title) && t . getId() == View . NO_ID ){
                    //Toolbar does not assign id to views with layout params SYSTEM, hence getId() == View.NO_ID
                    //in same manner subtitle TextView can be obtained.
                    return t;
                }
            }
        }
        return null ;
    }

}
