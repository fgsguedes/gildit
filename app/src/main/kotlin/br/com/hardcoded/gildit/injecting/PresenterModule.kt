package br.com.hardcoded.gildit.injecting

import br.com.hardcoded.gildit.presenter.SubmissionListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter() = SubmissionListPresenter()
}