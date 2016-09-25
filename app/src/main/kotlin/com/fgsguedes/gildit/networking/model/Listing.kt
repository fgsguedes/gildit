package com.fgsguedes.gildit.networking.model

import com.fgsguedes.gildit.model.Link

data class Listing(
    val data: ListingChildren
)

data class ListingChildren(
    val children: List<LinkThing>
)

data class LinkThing(
    val data: Link
)
