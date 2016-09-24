package com.fgsguedes.gildit.view

import com.fgsguedes.gildit.model.Thing

interface LinksListView {
  fun showLinks(links: Array<Thing.Link>)
  fun openPickSubredditDialog()
  fun updateTitle(subreddit: String)
  fun clearList()
}
