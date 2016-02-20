package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.networking.SubredditRequest
import br.com.hardcoded.gildit.view.LinksListView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LinksListPresenter @Inject constructor(private val subredditRequest: SubredditRequest) : Presenter<LinksListView> {

  lateinit var view: LinksListView

  private var currentSubreddit: String? = null

  override fun onCreate(bundle: Bundle?) {
    bundle?.apply {
      currentSubreddit = getString(CURRENT_SUBREDDIT)
    }

    view.updateTitle(currentSubreddit ?: "frontpage")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putString(CURRENT_SUBREDDIT, currentSubreddit)
  }

  override fun bindView(view: LinksListView) {
    this.view = view
  }

  fun onStart() {

    val requestObservable = currentSubreddit?.let {
      subredditRequest.hotOf(it)
    } ?: subredditRequest.frontpage()

    subscribe(requestObservable)
  }

  fun okPickSubredditClicked() {
    view.openPickSubredditDialog()
  }

  fun onNewSubredditChosen(subreddit: String) {
    currentSubreddit = subreddit

    view.updateTitle(subreddit)
    view.clearList()

    subscribe(subredditRequest.hotOf(subreddit))
  }

  private fun subscribe(observable: Observable<Array<Thing.Link>>) {
    observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(
            { view.showLinks(it) },
            { Log.e(TAG, "Error: ${it.message}", it) },
            { Log.i(TAG, "SubReddit request Completed") })
  }

  companion object {
    val TAG = LinksListPresenter::class.simpleName

    val CURRENT_SUBREDDIT = "currentSubreddit"
  }
}
