package br.com.hardcoded.gildit.ui.activity

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.com.hardcoded.gildit.R

import kotlinx.android.synthetic.activity_submission_list.*

class SubmissionListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_submission_list);

    setSupportActionBar(toolBar)
    supportActionBar.setTitle(R.string.frontpage)

    val arrayAdapter = ArrayAdapter(this, R.layout.list_row_submission, R.id.submissionTitle, arrayOf("Guedes", "Teste"))

    val listFragment = submissionListFragment as ListFragment
    listFragment.listAdapter = arrayAdapter
  }
}
