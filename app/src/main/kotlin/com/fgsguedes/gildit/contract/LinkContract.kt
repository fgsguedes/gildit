package com.fgsguedes.gildit.contract

import com.fgsguedes.gildit.model.Thing

object LinkContract {

  interface Presenter : BasePresenter<View> {

  }

  interface View {
    fun showLinks(links: Array<Thing.Link>)
    fun openPickSubredditDialog()
    fun updateTitle(subreddit: String)
    fun clearList()
  }
}
