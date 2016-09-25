package com.fgsguedes.gildit.injecting

import com.fgsguedes.gildit.networking.SubredditApi
import com.fgsguedes.gildit.repository.LinkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun provideLinkRepository(
      subredditApi: SubredditApi
  ) = LinkRepository(subredditApi)

}
