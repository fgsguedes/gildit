package br.com.hardcoded.gildit.app.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.hardcoded.gildit.R;

public class PickSubredditDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final FragmentActivity activity = getActivity();
    View view = LayoutInflater.from(activity).inflate(R.layout.pick_subreddit_dialog, null, false);

    return new AlertDialog.Builder(activity)
        .setTitle(R.string.view_subreddit)
        .setView(view)
        .setNegativeButton(R.string.cancel, null)
        .setPositiveButton(R.string.view, this)
        .create();
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    EditText editText = (EditText) getDialog().findViewById(R.id.pickSubredditEditText);

    Intent intent = new Intent(getActivity(), getActivity().getClass());
    intent.putExtra("input", editText.getText().toString().trim());

    startActivity(intent);
  }
}
