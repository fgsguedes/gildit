package br.com.hardcoded.gildit.injecting.module

import br.com.hardcoded.gildit.injecting.PresenterModule
import br.com.hardcoded.gildit.ui.activity.SubmissionListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(PresenterModule::class))
public interface ApplicationComponent {
  fun inject(submissionListActivity: SubmissionListActivity)
}