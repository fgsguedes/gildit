package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.networking.SubredditRequest
import br.com.hardcoded.gildit.view.LinksListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LinksListPresenter @Inject constructor(private val subredditRequest: SubredditRequest) : Presenter<LinksListView>, Callback<Array<Thing.Link>> {

  lateinit var view: LinksListView

  private var currentSubreddit: String? = null
  private var subredditCall: Call<Array<Thing.Link>>? = null

  override fun onCreate(bundle: Bundle?) {
    bundle?.let {
      currentSubreddit = it.getString(CURRENT_SUBREDDIT)
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
    subredditCall = if (currentSubreddit != null) subredditRequest.hotOf(currentSubreddit!!) else subredditRequest.frontpage()
    subredditCall?.enqueue(this)
  }

  fun okPickSubredditClicked() {
    view.openPickSubredditDialog()
  }

  fun onNewSubredditChosen(subreddit: String) {
    currentSubreddit = subreddit

    view.updateTitle(subreddit)
    view.clearList()

    subredditCall = subredditRequest.hotOf(subreddit)
    subredditCall?.enqueue(this)
  }

  override fun onFailure(t: Throwable) = TODO()

  override fun onResponse(response: Response<Array<Thing.Link>>) {
    subredditCall = null
    if (response.isSuccess) {
      view.showLinks(response.body())
    }
  }

  companion object {
    val CURRENT_SUBREDDIT = "currentSubreddit"
  }
}
