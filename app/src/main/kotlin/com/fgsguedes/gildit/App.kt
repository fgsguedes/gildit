package com.fgsguedes.gildit

import android.app.Application
import com.fgsguedes.gildit.injecting.AndroidModule
import com.fgsguedes.gildit.injecting.module.ApplicationComponent
import com.fgsguedes.gildit.injecting.module.DaggerApplicationComponent

class App : Application() {

  val component: ApplicationComponent by lazy {
    DaggerApplicationComponent
        .builder()
        .androidModule(AndroidModule(this))
        .build()
  }
}
