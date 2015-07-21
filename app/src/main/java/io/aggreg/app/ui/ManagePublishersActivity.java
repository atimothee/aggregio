package io.aggreg.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.PublishersFragment;
import io.aggreg.app.utils.GeneralUtils;
import io.aggreg.app.utils.References;

public class ManagePublishersActivity extends AppCompatActivity implements PublishersFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PublishersFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publisher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }else if(id == R.id.action_refresh){
            new GeneralUtils(this).SyncRefreshPublisherCategories();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFabClicked() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
