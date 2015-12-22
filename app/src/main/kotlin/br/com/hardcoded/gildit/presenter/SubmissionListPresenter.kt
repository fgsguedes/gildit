package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.gildit.view.SubmissionListView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response.ErrorListener
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import javax.inject.Inject

class SubmissionListPresenter @Inject constructor(val requestQueue: RequestQueue) : Presenter<SubmissionListView> {

  val TAG = SubmissionListPresenter::class.simpleName

  lateinit var view: SubmissionListView

  override fun onCreate(bundle: Bundle?) {
  }

  override fun onSaveInstanceState(outState: Bundle) = TODO()

  override fun bindView(view: SubmissionListView) {
    this.view = view
  }

  fun onStart() {
    requestQueue.add(StringRequest(
        Request.Method.GET,
        "https://reddit.com/r/androiddev.json",
        Listener<String> { Log.i(TAG, "Request response: $it") },
        ErrorListener { Log.w(TAG, "Request error: $it") }))
  }
}