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

class LinksListPresenter @Inject constructor(val requestQueue: RequestQueue) : Presenter<LinksListView> {

  val TAG = LinksListPresenter::class.simpleName

  lateinit var view: LinksListView

  override fun onCreate(bundle: Bundle?) {
  }

  override fun onSaveInstanceState(outState: Bundle) = TODO()

  override fun bindView(view: LinksListView) {
    this.view = view
  }

  fun onStart() = requestQueue.add(LinkRequest(
      "https://www.reddit.com/r/androiddev/hot.json?raw_json=1",
      Listener<Array<Thing.Link>> { showSubmissions(it) },
      ErrorListener { Log.w(TAG, "Request error: `$it`") }))

  fun showSubmissions(links: Array<Thing.Link>) = view.showLinks(links)
}
