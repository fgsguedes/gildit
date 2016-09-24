package br.com.hardcoded.gildit.injecting

import br.com.hardcoded.gildit.BuildConfig
import br.com.hardcoded.gildit.networking.SubredditApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

  @Provides
  @Singleton
  fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor {
          it.proceed(
              it.request().newBuilder()
                  .header("User-agent", "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME} (by /u/fgsguedes)")
                  .build()
          )
        }.build()
  }

  @Provides
  @Singleton
  fun providesRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://www.reddit.com")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build()
  }

  @Provides
  @Singleton
  fun providesSubredditRequester(retrofit: Retrofit) = retrofit.create(SubredditApi::class.java)
}
