package br.com.hardcoded.gildit.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.com.hardcoded.gildit.R;

public class ListSubmissionsActivity extends FragmentActivity {

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

  public static class ListSubmissionsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      setListAdapter(new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null,
          new String[]{"title", "selftext"}, new int[]{android.R.id.text1, android.R.id.text2}, 0));

      getLoaderManager().initLoader(0, null, this);
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
      return new CursorLoader(getActivity(), Uri.parse("content://" + getString(R.string.subreddit_authority) + "/Android"), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      getActivity().setTitle(data.getExtras().getString("subreddit"));
      ((SimpleCursorAdapter) getListAdapter()).changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      ((SimpleCursorAdapter) getListAdapter()).changeCursor(null);
    }
  }
}