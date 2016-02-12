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
import br.com.hardcoded.gildit.ui.dialog.PickSubredditDialogFragment
import br.com.hardcoded.gildit.view.LinksListView
import javax.inject.Inject

class LinkListActivity : BaseActivity(), LinksListView, PickSubredditDialogFragment.ClickCallback {

  @Inject
  lateinit var presenter: LinksListPresenter

  val linksRecycleView by lazy { findViewById(R.id.linksRecycleView) as RecyclerView }
  val toolBar by lazy { findViewById(R.id.toolBar) as Toolbar }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applicationComponent.inject(this)

    setContentView(R.layout.activity_submission_list);

    setupUi(savedInstanceState)
    initialize(savedInstanceState)

  }

  private fun setupUi(savedInstanceState: Bundle?) {
    setSupportActionBar(toolBar)

    val layoutManager = LinearLayoutManager(this)
    savedInstanceState?.let {
      layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("lala"))
    }

    linksRecycleView.layoutManager = layoutManager
  }

  override fun onSaveInstanceState(outState: Bundle) {
    presenter.onSaveInstanceState(outState)
    outState.putParcelable("lala", linksRecycleView.layoutManager.onSaveInstanceState())
    super.onSaveInstanceState(outState)
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
    linksRecycleView.adapter = LinksRecycleViewAdapter(this, links)
  }

  override fun openPickSubredditDialog() {
    PickSubredditDialogFragment().show(supportFragmentManager, "")
  }

  override fun updateTitle(subreddit: String) {
    supportActionBar?.title = subreddit
  }

  override fun clearList() {
    linksRecycleView.swapAdapter(null, true)
  }

  override fun onNewSubredditChosen(sub: String) {
    presenter.onNewSubredditChosen(sub)
  }
}
