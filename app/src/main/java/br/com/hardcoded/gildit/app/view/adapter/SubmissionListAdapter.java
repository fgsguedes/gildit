package br.com.hardcoded.gildit.app.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.hardcoded.gildit.R;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class SubmissionListAdapter extends CursorAdapter {

  public static final String TAG = SubmissionListAdapter.class.getSimpleName();

  // TODO: Check if not using too much RAM
  private static final LruCache<String, Bitmap> thumbnailCache = new LruCache<>(50);

  public SubmissionListAdapter(Context context) {
    super(context, null, 0);
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    ViewHolder holder;
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.list_submissions_row, parent, false);
      holder = new ViewHolder();
      holder.thumbnail = (ImageView) convertView.findViewById(R.id.submissionThumbnail);
      holder.title = (TextView) convertView.findViewById(R.id.submissionTitle);
      holder.author = (TextView) convertView.findViewById(R.id.submissionAuthor);
      holder.subreddit = (TextView) convertView.findViewById(R.id.submissionSubreddit);
      holder.comments = (TextView) convertView.findViewById(R.id.submissionComments);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    getCursor().moveToPosition(position);

    holder.position = position;
    holder.title.setText(getString("title"));
    holder.author.setText(getString("author"));
    holder.subreddit.setText(getString("subreddit"));
    holder.comments.setText(getString("num_comments"));

    final String thumbnailUrl = getString("thumbnail");
    Bitmap cachedThumbnail = thumbnailCache.get(thumbnailUrl);
    if (cachedThumbnail != null) {
      holder.thumbnail.setImageBitmap(cachedThumbnail);
    } else {
      holder.thumbnail.setImageResource(R.drawable.thumbnail_exclamation);

      // TODO: Extract to a helper
      new AsyncTask<ViewHolder, Void, Bitmap>() {
        private ViewHolder v;

        @Override
        protected Bitmap doInBackground(ViewHolder... params) {
          v = params[0];


          try {
            URL url = new URL(thumbnailUrl);
            Bitmap thumbnail = BitmapFactory.decodeStream(url.openStream());
            thumbnailCache.put(thumbnailUrl, thumbnail);
            return thumbnail;
          } catch (MalformedURLException e) {
            Log.w(TAG, "Invalid thumbnail URL: " + thumbnailUrl);
          } catch (IOException e) {
            Log.w(TAG, "Unable to download thumbnail");

          }

          return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
          if (bitmap != null && v.position == position) {
            v.thumbnail.setImageBitmap(bitmap);
          }
        }
      }.executeOnExecutor(THREAD_POOL_EXECUTOR, holder);
    }

    return convertView;
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) { return null; }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {}

  private String getString(String key) {
    return getCursor().getString(getCursor().getColumnIndex(key));
  }

  private static class ViewHolder {
    int position;
    ImageView thumbnail;
    TextView title;
    TextView author;
    TextView subreddit;
    TextView comments;
  }
}
