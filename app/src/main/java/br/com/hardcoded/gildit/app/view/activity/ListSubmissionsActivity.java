package br.com.hardcoded.gildit.app.view.activity;

import android.content.AsyncQueryHandler;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.atomic.AtomicInteger;

import br.com.hardcoded.gildit.R;
import br.com.hardcoded.gildit.app.view.fragment.ListSubmissionsFragment;

public class ListSubmissionsActivity extends ActionBarActivity {

  private static final String TAG = ListSubmissionsActivity.class.getSimpleName();
  private static final AtomicInteger token = new AtomicInteger(0);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_submissions);

    Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
    setSupportActionBar(toolBar);

    FragmentManager fragmentManager = getSupportFragmentManager();
    if (fragmentManager.findFragmentById(R.id.submissionListFragment) == null) {
      fragmentManager
          .beginTransaction()
          .add(android.R.id.content, new ListSubmissionsFragment())
          .commit();
    }

    setTitle(R.string.frontpage);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.subreddit_submission_list_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // TODO: It's working, make it pretty
    if (R.id.pickSubredditMenuItem == item.getItemId()) {
      FragmentManager supportFragmentManager = getSupportFragmentManager();
      final ListSubmissionsFragment fragment = (ListSubmissionsFragment) supportFragmentManager.findFragmentById(R.id.submissionListFragment);
      AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
          if (fragment != null) {
            fragment.onLoadFinished(null, cursor);
          }
        }
      };
      if (fragment != null) {
        setTitle("/r/Android");
        fragment.setListShown(false);
      }
      handler.startQuery(token.getAndIncrement(), null, Uri.parse("content://" + getString(R.string.subreddit_authority) + "/Android"), null, null, null, null);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}