package br.com.hardcoded.gildit.presenter

import android.os.Bundle

interface Presenter<V> {
  fun onCreate(bundle: Bundle?): Unit
  fun onSaveInstanceState(outState: Bundle): Unit
  fun bindView(view: V): Unit
}