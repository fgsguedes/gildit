package com.fgsguedes.gildit.presenter

import android.os.Bundle
import android.util.Log
import com.fgsguedes.gildit.contract.LinkContract
import com.fgsguedes.gildit.model.Link
import com.fgsguedes.gildit.repository.LinkRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LinksListPresenter @Inject constructor(
    private val linkRepository: LinkRepository
) : LinkContract.Presenter {

  lateinit var view: LinkContract.View

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

  override fun bindView(view: LinkContract.View) {
    this.view = view
  }

  override fun onStart() {

    val requestObservable = with(currentSubreddit) {
      when (this) {
        null -> linkRepository.frontpage()
        else -> linkRepository.hotOf(this)
      }
    }

    subscribe(requestObservable)
  }

  override fun okPickSubredditClicked() {
    view.openPickSubredditDialog()
  }

  override fun onNewSubredditChosen(subreddit: String) {
    currentSubreddit = subreddit

    view.updateTitle(subreddit)
    view.clearList()

    subscribe(linkRepository.hotOf(subreddit))
  }

  private fun subscribe(observable: Observable<Link>) {
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
    val TAG = LinksListPresenter::class.java.simpleName

    val CURRENT_SUBREDDIT = "currentSubreddit"
  }
}
