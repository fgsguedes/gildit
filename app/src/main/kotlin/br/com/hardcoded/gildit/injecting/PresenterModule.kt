package br.com.hardcoded.gildit.injecting

import android.content.Context
import br.com.hardcoded.gildit.presenter.SubmissionListPresenter
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

  @Provides
  @Singleton
  fun providesPresenter(context: Context) = SubmissionListPresenter(Volley.newRequestQueue(context))
}