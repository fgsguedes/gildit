package br.com.hardcoded.gildit.view

import br.com.hardcoded.gildit.model.Thing

interface LinksListView {
  fun showLinks(links: Array<Thing.Link>)
  fun openPickSubredditDialog()
  fun updateTitle(subreddit: String)
  fun clearList()
}