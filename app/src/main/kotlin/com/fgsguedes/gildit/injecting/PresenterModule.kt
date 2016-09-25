package com.fgsguedes.gildit.injecting

import com.fgsguedes.gildit.contract.LinkContract
import com.fgsguedes.gildit.presenter.LinksListPresenter
import com.fgsguedes.gildit.repository.LinkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter(linkRepository: LinkRepository): LinkContract.Presenter = LinksListPresenter(linkRepository)
}
