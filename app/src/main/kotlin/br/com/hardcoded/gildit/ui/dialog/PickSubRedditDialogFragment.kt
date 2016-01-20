package br.com.hardcoded.gildit.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import br.com.hardcoded.gildit.R

class PickSubRedditDialogFragment() : DialogFragment() {

  val editText by lazy { dialog.findViewById(R.id.pickSubredditEditText) as EditText }
  var callBack: ClickCallback? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    callBack = when (activity) {
      is ClickCallback -> activity as ClickCallback
      else -> null
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val view = LayoutInflater.from(activity).inflate(R.layout.pick_subreddit_dialog, null, false)

    return AlertDialog.Builder(activity)
        .setTitle(R.string.pick_subreddit)
        .setView(view)
        .setPositiveButton(R.string.navigate) { dialogInterface, which ->
          callBack?.onNewSubRedditChosen(editText.text.toString())
        }
        .create()
  }

  interface ClickCallback {
    fun onNewSubRedditChosen(sub: String)
  }
}