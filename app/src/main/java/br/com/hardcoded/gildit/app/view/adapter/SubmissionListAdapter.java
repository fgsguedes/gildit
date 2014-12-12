package br.com.hardcoded.gildit.app.view.adapter;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import br.com.hardcoded.gildit.R;

public class SubmissionListAdapter extends SimpleCursorAdapter {

  public static final String TAG = SubmissionListAdapter.class.getSimpleName();

  public SubmissionListAdapter(Context context) {
    super(context, R.layout.list_submissions_row, null, new String[]{"title", "author", "subreddit", "num_comments"},
        new int[]{R.id.submissionTitle, R.id.submissionAuthor, R.id.submissionSubreddit, R.id.submissionComments}, 0);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // TODO: Implement ViewHolder pattern
    return super.getView(position, convertView, parent);
  }
}
