package br.com.hardcoded.gildit.app.view.activity;

import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.concurrent.atomic.AtomicInteger;

import br.com.hardcoded.gildit.R;
import br.com.hardcoded.gildit.app.view.adapter.SubmissionListAdapter;

public class ListSubmissionsActivity extends FragmentActivity {

  private static final String TAG = ListSubmissionsActivity.class.getSimpleName();
  private static final AtomicInteger token = new AtomicInteger(0);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FragmentManager fragmentManager = getSupportFragmentManager();
    if (fragmentManager.findFragmentById(android.R.id.content) == null) {
      fragmentManager
          .beginTransaction()
          .add(android.R.id.content, new ListSubmissionsFragment())
          .commit();
    }
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    // TODO: It's working, make it pretty
    if (R.id.pickSubredditMenuItem == item.getItemId()) {
      AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
          FragmentManager supportFragmentManager = getSupportFragmentManager();
          ListSubmissionsFragment fragment = (ListSubmissionsFragment) supportFragmentManager.findFragmentById(android.R.id.content);
          if (fragment != null) {
            fragment.onLoadFinished(null, cursor);
          }
        }
      };
      handler.startQuery(token.getAndIncrement(), null, Uri.parse("content://" + getString(R.string.subreddit_authority) + "/Android"), null, null, null, null);
      return true;
    }
    return super.onMenuItemSelected(featureId, item);
  }

  public static class ListSubmissionsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      setListAdapter(new SubmissionListAdapter(getActivity()));
      setHasOptionsMenu(true);

      getListView().setDivider(null);
      getListView().setDividerHeight(0);

      getListView().setDrawSelectorOnTop(true);

      getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.subreddit_submission_list_menu, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      CursorWrapper item = (CursorWrapper) getListAdapter().getItem(position);

      if ("true".equals(item.getString(item.getColumnIndex("is_self")))) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(item.getString(item.getColumnIndex("title")));
        builder.setMessage(item.getString(item.getColumnIndex("selftext")));
        builder.setNeutralButton(R.string.close, null);
        builder.show();
      } else {
        String url = item.getString(item.getColumnIndex("url"));
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
      }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      return new CursorLoader(getActivity(), Uri.parse("content://" + getString(R.string.subreddit_authority)), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      if (data != null) {
        getActivity().setTitle(data.getExtras().getString("subreddit"));
      }
      ((CursorAdapter) getListAdapter()).changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      ((CursorAdapter) getListAdapter()).changeCursor(null);
    }
  }
}