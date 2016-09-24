package com.fgsguedes.gildit.injecting

import com.fgsguedes.gildit.networking.SubredditApi
import com.fgsguedes.gildit.presenter.LinksListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter(subredditApi: SubredditApi) = LinksListPresenter(subredditApi)
}
