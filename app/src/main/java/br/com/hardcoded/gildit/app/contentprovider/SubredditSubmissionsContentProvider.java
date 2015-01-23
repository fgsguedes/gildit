package br.com.hardcoded.gildit.app.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import br.com.hardcoded.gildit.app.external.Reddit;

public class SubredditSubmissionsContentProvider extends ContentProvider {

  public static final String TAG = SubredditSubmissionsContentProvider.class.getSimpleName();

  public SubredditSubmissionsContentProvider() {
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public String getType(Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public boolean onCreate() { return true; }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {

    MatrixCursor cursor = null;

    try {
      String subreddit = uri.getLastPathSegment();
      JSONObject submissions = Reddit.submissions(subreddit);

      JSONArray listing = submissions.getJSONObject("data").getJSONArray("children");
      int id = 0;
      for (int i = 0; i < listing.length(); i++) {
        JSONObject post = listing.getJSONObject(i).getJSONObject("data");
        String[] fields = new String[post.length() + 1];
        fields[0] = "_id";
        Iterator<String> keys = post.keys();
        for (int j = 0; keys.hasNext(); j++) {
          fields[j + 1] = keys.next();
        }
        if (i == 0) {
          cursor = new MatrixCursor(fields);
        }

        String[] data = new String[post.length() + 1];
        data[0] = String.valueOf(id++);
        Iterator<String> keys2 = post.keys();
        for (int j = 0; keys2.hasNext(); j++) {
          String field = keys2.next();
          if (!field.equals("_id")) {
            data[j + 1] = post.getString(field);
          }
        }

        cursor.addRow(data);
      }
    } catch (JSONException e) {
      Log.e(TAG, "Error", e);
    }

    return cursor;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
