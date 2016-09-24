package com.fgsguedes.gildit.networking

import com.fgsguedes.gildit.model.Thing
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface SubredditApi {

  @GET("/hot.json?raw_json=1")
  fun frontpage(): Observable<Array<Thing.Link>>

  @GET("/r/{subreddit}/hot.json?raw_json=1")
  fun hotOf(@Path("subreddit") subreddit: String): Observable<Array<Thing.Link>>
}
