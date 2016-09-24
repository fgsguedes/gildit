package com.fgsguedes.gildit.contract

import android.os.Bundle

interface BasePresenter<V> {
  fun onCreate(bundle: Bundle?): Unit
  fun onSaveInstanceState(outState: Bundle): Unit
  fun bindView(view: V): Unit
}
