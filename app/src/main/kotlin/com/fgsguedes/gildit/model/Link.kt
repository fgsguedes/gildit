package com.fgsguedes.gildit.model

data class Link(
    val id: String,
    val name: String,
    val title: String,
    val author: String,
    val subreddit: String,
    override val ups: Int,
    override val downs: Int,
    override val likes: Boolean?,
    override val created: Long
) : Votable, Created
