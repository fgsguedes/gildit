package br.com.hardcoded.gildit.injecting.module

import br.com.hardcoded.gildit.injecting.AndroidModule
import br.com.hardcoded.gildit.injecting.PresenterModule
import br.com.hardcoded.gildit.injecting.RetrofitModule
import br.com.hardcoded.gildit.ui.activity.LinkListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, RetrofitModule::class, PresenterModule::class))
interface ApplicationComponent {
  fun inject(submissionListActivity: LinkListActivity)
}