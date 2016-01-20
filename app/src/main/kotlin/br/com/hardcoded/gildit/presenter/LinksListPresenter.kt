package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.networking.request.LinkRequest
import br.com.hardcoded.gildit.view.LinksListView
import com.android.volley.RequestQueue
import com.android.volley.Response.ErrorListener
import com.android.volley.Response.Listener
import javax.inject.Inject

class LinksListPresenter @Inject constructor(private val requestQueue: RequestQueue) : Presenter<LinksListView> {

  companion object {
    val TAG = LinksListPresenter::class.simpleName
  }

  lateinit var view: LinksListView

  override fun onCreate(bundle: Bundle?) {
  }

  override fun onSaveInstanceState(outState: Bundle) = TODO()

  override fun bindView(view: LinksListView) {
    this.view = view
  }

  fun onStart() {
    requestQueue.add(LinkRequest(
        "https://www.reddit.com/hot.json?raw_json=1",
        Listener<Array<Thing.Link>> { view.showLinks(it) },
        ErrorListener { Log.w(TAG, "Request error: `$it`") }))
  }

  fun okPickSubredditClicked() {
    view.openPickSubRedditDialog()
  }

  fun onNewSubRedditChosen(subreddit: String) {
    requestQueue.add(LinkRequest(
        "https://www.reddit.com/r/$subreddit/hot.json?raw_json=1",
        Listener<Array<Thing.Link>> { view.showLinks(it) },
        ErrorListener { Log.w(TAG, "Request error: `$it`") }))
  }
}
