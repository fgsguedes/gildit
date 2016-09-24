package com.fgsguedes.gildit.model

interface Votable {
  val ups: Int
  val downs: Int
  val likes: Boolean?
}
