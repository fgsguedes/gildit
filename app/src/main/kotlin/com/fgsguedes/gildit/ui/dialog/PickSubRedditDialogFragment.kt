package com.fgsguedes.gildit.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import com.fgsguedes.gildit.R
import com.fgsguedes.gildit.extension.withoutSpaces

class PickSubredditDialogFragment() : DialogFragment() {

  val editText by lazy { dialog.findViewById(R.id.pickSubredditEditText) as EditText }
  val callBack by lazy { activity as ClickCallback }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (activity !is ClickCallback) {
      throw IllegalStateException("Calling activity must implement ClickCallback")
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val view = LayoutInflater.from(activity).inflate(R.layout.pick_subreddit_dialog, null, false)

    return AlertDialog.Builder(activity)
        .setTitle(R.string.pick_subreddit)
        .setView(view)
        .setNeutralButton(R.string.cancel) { dialogInterface, which -> }
        .setPositiveButton(R.string.navigate) { dialogInterface, which ->
          callBack.onNewSubredditChosen(editText.text.withoutSpaces())
        }
        .create()
  }

  interface ClickCallback {
    fun onNewSubredditChosen(sub: String)
  }
}
