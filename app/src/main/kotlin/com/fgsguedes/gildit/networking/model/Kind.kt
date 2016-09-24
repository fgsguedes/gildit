package com.fgsguedes.gildit.networking.model

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
