package com.fgsguedes.gildit.injecting

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(val application: Application) {

  @Provides
  @Singleton
  fun providesContext() = application.applicationContext
}
