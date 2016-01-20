package br.com.hardcoded.gildit.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import br.com.hardcoded.gildit.R
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.presenter.LinksListPresenter
import br.com.hardcoded.gildit.ui.dialog.PickSubRedditDialogFragment
import br.com.hardcoded.gildit.view.LinksListView
import javax.inject.Inject

class LinkListActivity : BaseActivity(), LinksListView, PickSubRedditDialogFragment.ClickCallback {

  @Inject
  lateinit var presenter: LinksListPresenter

  val linksRecycleView by lazy { findViewById(R.id.linksRecycleView) as RecyclerView }
  val toolBar by lazy { findViewById(R.id.toolBar) as Toolbar }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applicationComponent.inject(this)

    setContentView(R.layout.activity_submission_list);

    setupUi()
    initialize(savedInstanceState)
  }

  private fun setupUi() {
    setSupportActionBar(toolBar)
    updateTitle(getString(R.string.frontpage))
  }

  private fun initialize(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_link_list, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.pickSubredditMenuItem -> {
        presenter.okPickSubredditClicked()
        true
      }
      else -> false
    }
  }

  override fun showLinks(links: Array<Thing.Link>) {
    linksRecycleView.layoutManager = LinearLayoutManager(this)
    linksRecycleView.adapter = LinksRecycleViewAdapter(this, links)
  }

  override fun openPickSubRedditDialog() {
    PickSubRedditDialogFragment().show(supportFragmentManager, "")
  }

  override fun updateTitle(subreddit: String) {
    supportActionBar.title = subreddit
  }

  override fun clearList() {
    linksRecycleView.swapAdapter(null, true)
  }

  override fun onNewSubRedditChosen(sub: String) {
    presenter.onNewSubRedditChosen(sub)
  }
}
