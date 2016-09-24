package com.fgsguedes.gildit.injecting

import com.fgsguedes.gildit.networking.SubredditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

  @Provides
  @Singleton
  fun providesSubredditRequester(retrofit: Retrofit) = retrofit.create(SubredditApi::class.java)
}
