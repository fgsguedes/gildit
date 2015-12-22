package br.com.hardcoded.gildit

import android.app.Application
import android.util.Log
import br.com.hardcoded.gildit.injecting.AndroidModule
import br.com.hardcoded.gildit.injecting.module.ApplicationComponent
import br.com.hardcoded.gildit.injecting.module.DaggerApplicationComponent

class App : Application() {

  val TAG = App::class.simpleName

  val component: ApplicationComponent by lazy {
    DaggerApplicationComponent
        .builder()
        .androidModule(AndroidModule(this))
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    Log.e(TAG, "onCreate")
  }
}