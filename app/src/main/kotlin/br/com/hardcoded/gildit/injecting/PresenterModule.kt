package br.com.hardcoded.gildit.injecting

import br.com.hardcoded.gildit.networking.SubredditApi
import br.com.hardcoded.gildit.presenter.LinksListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter(subredditApi: SubredditApi) = LinksListPresenter(subredditApi)
}