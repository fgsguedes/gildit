package br.com.hardcoded.gildit.ui.activity

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.widget.ArrayAdapter
import br.com.hardcoded.gildit.R
import br.com.hardcoded.gildit.model.Thing
import br.com.hardcoded.gildit.presenter.LinksListPresenter
import br.com.hardcoded.gildit.view.LinksListView
import kotlinx.android.synthetic.main.activity_submission_list.*
import javax.inject.Inject

class SubmissionListActivity : BaseActivity(), LinksListView {

  val submissionList: ListFragment by lazy {
    submissionListFragment as ListFragment
  }

  @Inject
  lateinit var presenter: LinksListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    applicationComponent.inject(this)

    setContentView(R.layout.activity_submission_list);

    setupUi()
    initialize(savedInstanceState)
  }

  private fun setupUi() {
    setSupportActionBar(toolBar)
    supportActionBar.title = getString(R.string.frontpage)
  }

  private fun initialize(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun showLinks(links: Array<Thing.Link>) {
    submissionList.listAdapter = ArrayAdapter(this, R.layout.list_row_submission, R.id.submissionTitle, links.map { it.title })
  }
}
