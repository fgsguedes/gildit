package com.fgsguedes.gildit.repository

import com.fgsguedes.gildit.model.Link
import com.fgsguedes.gildit.networking.SubredditApi
import com.fgsguedes.gildit.networking.model.Listing
import rx.Observable

class LinkRepository(
    private val subredditApi: SubredditApi
) {

  private val listingToLink = { listing: Listing ->
    Observable.from(listing.data.children.map { it.data })
  }

  fun frontpage(): Observable<Link> {
    return subredditApi.frontpage()
        .flatMap(listingToLink)
  }

  fun hotOf(subreddit: String): Observable<Link> {
    return subredditApi.hotOf(subreddit)
        .flatMap(listingToLink)
  }

}
