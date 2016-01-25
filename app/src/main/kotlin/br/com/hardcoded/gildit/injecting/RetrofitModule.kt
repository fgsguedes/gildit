package br.com.hardcoded.gildit.injecting

import br.com.hardcoded.gildit.BuildConfig
import br.com.hardcoded.gildit.networking.LinkConverter
import br.com.hardcoded.gildit.networking.SubredditRequest
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

  @Provides
  @Singleton
  fun providesRetrofit(): Retrofit {

    val client = OkHttpClient.Builder()
        .addInterceptor {
          val request = it.request()
              .newBuilder()
              .header("User-agent", "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME} (by /u/fgsguedes)")
              .build()

          it.proceed(request)
        }.build()

    return Retrofit.Builder()
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(LinkConverter())
        .client(client)
        .build()
  }

  @Provides
  @Singleton
  fun providesSubredditRequester(retrofit: Retrofit) = retrofit.create(SubredditRequest::class.java)
}