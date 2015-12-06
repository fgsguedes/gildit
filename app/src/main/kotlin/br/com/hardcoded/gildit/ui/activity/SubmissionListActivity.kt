package br.com.hardcoded.gildit.ui.activity

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.widget.ArrayAdapter
import br.com.hardcoded.gildit.R
import br.com.hardcoded.gildit.presenter.SubmissionListPresenter
import br.com.hardcoded.gildit.view.SubmissionListView
import kotlinx.android.synthetic.main.activity_submission_list.*
import javax.inject.Inject

class SubmissionListActivity : BaseActivity(), SubmissionListView {
  val TAG = SubmissionListActivity::class.simpleName

  lateinit var submissionList: ListFragment

  @Inject
  lateinit var presenter: SubmissionListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    Log.e(TAG, "onCreate")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_submission_list);
    applicationComponent.inject(this)

    setupUi()
    initialize(savedInstanceState)
  }

  private fun setupUi() {
    setSupportActionBar(toolBar)
    supportActionBar.title = getString(R.string.frontpage)

    submissionList = submissionListFragment as ListFragment
  }

  private fun initialize(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun showSubmissions(submissions: Array<String>) {
    submissionList.listAdapter = ArrayAdapter(this, R.layout.list_row_submission, R.id.submissionTitle, submissions)
  }
}
