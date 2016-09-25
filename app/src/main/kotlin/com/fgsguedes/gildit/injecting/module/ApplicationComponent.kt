package com.fgsguedes.gildit.injecting.module

import com.fgsguedes.gildit.injecting.*
import com.fgsguedes.gildit.ui.activity.LinkListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidModule::class,
        RetrofitModule::class,
        ApiModule::class,
        RepositoryModule::class,
        PresenterModule::class
    )
)
interface ApplicationComponent {
  fun inject(submissionListActivity: LinkListActivity)
}
