package br.com.hardcoded.gildit.networking

import br.com.hardcoded.gildit.model.Thing

data class LinkListingResponse(val subreddit: String, val links: Array<Thing.Link>)