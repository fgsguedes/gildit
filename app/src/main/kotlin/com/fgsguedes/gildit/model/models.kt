package com.fgsguedes.gildit.model


data class Listing(val before: String, val after: String, val modhash: String, val children: Array<Thing>)


sealed class Thing(val id: String, val name: String) {
  class Link(
      val commentId: String,
      val commentName: String,
      val title: String,
      val author: String,
      val subreddit: String)
  : Thing(commentId, commentName), Votable, Created {

    // Votable
    override val ups: Int by lazy { ups }
    override val downs: Int by lazy { downs }
    override val likes: Boolean? by lazy { likes }

    // Created
    override val created: Long by lazy { created }
    override val created_utc: Long by lazy { created_utc }
  }
}

interface Votable {
  val ups: Int
  val downs: Int
  val likes: Boolean?
}

interface Created {
  val created: Long
  val created_utc: Long
}

enum class Kind(val prefix: String) {
  COMMENT("t1"),
  ACCOUNT("t2"),
  LINK("t3"),
  MESSAGE("t4"),
  SUBREDDIT("t5"),
  AWARD("t6"),
  PROMO_CAMPAIGN("t8");

  override fun toString() = this.prefix
}
