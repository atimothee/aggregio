package io.aggreg.app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.SelectPublishersFragment;
import io.aggreg.app.utils.References;

public class SelectPublishersActivity extends AppCompatActivity implements SelectPublishersFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        // record the fact that the app has been started at least once
        SharedPreferences settings = getSharedPreferences(References.KEY_PREFERENCES, 0);
        settings.edit().putBoolean(References.KEY_PREF_FIRST_TIME, false).apply();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SelectPublishersFragment.newInstance(References.ACTIVITY_TYPE_SETUP_PUBLISHERS))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_publishers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFabClicked() {

        Intent i = new Intent(SelectPublishersActivity.this, MainActivity.class);
        i.putExtra(References.ARG_IS_FIRST_TIME, true);
        startActivity(i);
    }
}
