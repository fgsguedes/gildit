package com.fgsguedes.gildit.contract

import com.fgsguedes.gildit.model.Link

object LinkContract {

  interface Presenter : BasePresenter<View> {
    fun onStart()
    fun okPickSubredditClicked()
    fun onNewSubredditChosen(subreddit: String)
  }

  interface View {
    fun showLinks(link: Link)
    fun openPickSubredditDialog()
    fun updateTitle(subreddit: String)
    fun clearList()
  }
}
