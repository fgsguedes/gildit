package br.com.hardcoded.gildit.injecting

import br.com.hardcoded.gildit.networking.SubredditRequest
import br.com.hardcoded.gildit.presenter.LinksListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter(subredditRequest: SubredditRequest) = LinksListPresenter(subredditRequest)
}