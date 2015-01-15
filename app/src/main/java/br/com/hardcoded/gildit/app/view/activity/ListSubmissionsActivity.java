package br.com.hardcoded.gildit.app.view.activity;

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
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ListView;

import br.com.hardcoded.gildit.R;
import br.com.hardcoded.gildit.app.view.adapter.SubmissionListAdapter;

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

      setListAdapter(new SubmissionListAdapter(getActivity()));

      getListView().setDivider(null);
      getListView().setDividerHeight(0);

      getListView().setDrawSelectorOnTop(true);

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