package com.fgsguedes.gildit.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.fgsguedes.gildit.R
import com.fgsguedes.gildit.contract.LinkContract
import com.fgsguedes.gildit.model.Link
import com.fgsguedes.gildit.ui.dialog.PickSubredditDialogFragment
import javax.inject.Inject

class LinkListActivity : BaseActivity(), LinkContract.View, PickSubredditDialogFragment.ClickCallback {

  @Inject
  lateinit var presenter: LinkContract.Presenter

  val toolBar by lazy { findViewById(R.id.toolBar) as Toolbar }

  val linksRecycleView by lazy { findViewById(R.id.linksRecycleView) as RecyclerView }
  val adapter by lazy { LinksRecycleViewAdapter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applicationComponent.inject(this)

    setContentView(R.layout.activity_submission_list);

    setupUi(savedInstanceState)
    initialize(savedInstanceState)
  }

  private fun setupUi(savedInstanceState: Bundle?) {
    setSupportActionBar(toolBar)

    linksRecycleView.layoutManager = LinearLayoutManager(this).apply {
      onRestoreInstanceState(savedInstanceState?.getParcelable(RECYCLER_VIEW_STATE))
    }
    linksRecycleView.adapter = adapter
  }

  override fun onSaveInstanceState(outState: Bundle) {
    presenter.onSaveInstanceState(outState)
    outState.putParcelable(RECYCLER_VIEW_STATE, linksRecycleView.layoutManager.onSaveInstanceState())
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

  override fun showLinks(link: Link) {
    adapter.add(link)
  }

  override fun openPickSubredditDialog() {
    PickSubredditDialogFragment().show(supportFragmentManager, "")
  }

  override fun updateTitle(subreddit: String) {
    supportActionBar?.title = subreddit
  }

  override fun clearList() {
    adapter.clear()
  }

  override fun onNewSubredditChosen(sub: String) {
    presenter.onNewSubredditChosen(sub)
  }

  companion object {
    val RECYCLER_VIEW_STATE = "recyclerViewState"
  }
}
