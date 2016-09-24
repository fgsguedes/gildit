package com.fgsguedes.gildit.injecting.module

import com.fgsguedes.gildit.injecting.AndroidModule
import com.fgsguedes.gildit.injecting.PresenterModule
import com.fgsguedes.gildit.injecting.RetrofitModule
import com.fgsguedes.gildit.ui.activity.LinkListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, RetrofitModule::class, PresenterModule::class))
interface ApplicationComponent {
  fun inject(submissionListActivity: LinkListActivity)
}
