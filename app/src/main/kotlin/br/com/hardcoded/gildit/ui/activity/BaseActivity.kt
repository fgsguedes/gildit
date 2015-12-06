package br.com.hardcoded.gildit.ui.activity

import android.support.v7.app.AppCompatActivity
import br.com.hardcoded.gildit.App
import br.com.hardcoded.gildit.injecting.module.ApplicationComponent

open class BaseActivity : AppCompatActivity() {

  protected val applicationComponent: ApplicationComponent by lazy {
    (application as App).component
  }
}