package br.com.hardcoded.gildit.app.view.activity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.atomic.AtomicInteger;

import br.com.hardcoded.gildit.R;
import br.com.hardcoded.gildit.app.view.fragment.ListSubmissionsFragment;
import br.com.hardcoded.gildit.app.view.fragment.dialog.PickSubredditDialogFragment;

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
      PickSubredditDialogFragment dialogFragment = new PickSubredditDialogFragment();
      dialogFragment.show(supportFragmentManager, "pickSubredditDialog");
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    String subreddit = intent.getStringExtra("input");
    if (!TextUtils.isEmpty(subreddit)) {
      final ListSubmissionsFragment fragment = (ListSubmissionsFragment) getSupportFragmentManager().findFragmentById(R.id.submissionListFragment);
      if (fragment != null) {
        AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
          @Override
          protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            fragment.updateCursor(cursor);
          }
        };
        fragment.setListShown(false);
        setTitle("/r/" + subreddit);
        handler.startQuery(token.getAndIncrement(), null, Uri.parse("content://" + getString(R.string.subreddit_authority) + "/" + subreddit), null, null, null, null);
      }
    }
  }
}