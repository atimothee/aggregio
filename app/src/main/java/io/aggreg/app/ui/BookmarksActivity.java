package io.aggreg.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.aggreg.app.R;
import io.aggreg.app.ui.fragment.ArticlesFragment;
import io.aggreg.app.utils.References;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        if (savedInstanceState == null) {
            Bundle articlesBundle = new Bundle();
            articlesBundle.putBoolean(References.ARG_KEY_IS_BOOKMARKS, true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, ArticlesFragment.newInstance(articlesBundle))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmarks, menu);
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
}
