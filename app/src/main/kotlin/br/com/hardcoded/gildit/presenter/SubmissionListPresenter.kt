package br.com.hardcoded.gildit.presenter

import android.os.Bundle
import br.com.hardcoded.gildit.view.SubmissionListView

class SubmissionListPresenter : Presenter<SubmissionListView> {

  val TAG = SubmissionListPresenter::class.simpleName

  lateinit var view: SubmissionListView

  override fun onCreate(bundle: Bundle?) {
  }

  override fun onSaveInstanceState(outState: Bundle) = TODO()

  override fun bindView(view: SubmissionListView) {
    this.view = view
  }

  fun onStart() {
    view.showSubmissions(arrayOf("Guedes", "Teste"))
  }
}