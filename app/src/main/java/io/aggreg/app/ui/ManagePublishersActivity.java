package io.aggreg.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.SelectPublishersFragment;
import io.aggreg.app.utils.References;

public class ManagePublishersActivity extends AppCompatActivity implements SelectPublishersFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SelectPublishersFragment.newInstance(References.ACTIVITY_TYPE_MANAGE_PUBLISHERS))
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
