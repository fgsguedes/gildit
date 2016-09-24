package com.fgsguedes.gildit.ui.activity

import android.support.v7.app.AppCompatActivity
import com.fgsguedes.gildit.App
import com.fgsguedes.gildit.injecting.module.ApplicationComponent

open class BaseActivity : AppCompatActivity() {

  protected val applicationComponent: ApplicationComponent by lazy {
    (application as App).component
  }
}
