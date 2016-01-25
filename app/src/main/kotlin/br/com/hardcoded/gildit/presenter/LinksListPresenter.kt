package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.networking.SubredditRequest
import br.com.hardcoded.gildit.view.LinksListView
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LinksListPresenter @Inject constructor(private val subredditRequest: SubredditRequest) : Presenter<LinksListView> {

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
    subredditRequest
        .frontpage()
        .enqueue(Lala())
  }

  fun okPickSubredditClicked() {
    view.openPickSubredditDialog()
  }

  fun onNewSubredditChosen(subreddit: String) {
    view.updateTitle(subreddit)
    view.clearList()
    subredditRequest
        .hotOf(subreddit)
        .enqueue(Lala())
  }

  inner class Lala : Callback<Array<Thing.Link>> {
    override fun onFailure(t: Throwable?) {
      throw UnsupportedOperationException()
    }

    override fun onResponse(response: Response<Array<Thing.Link>>?) {
      view.showLinks(response?.body() ?: emptyArray())
    }
  }
}
