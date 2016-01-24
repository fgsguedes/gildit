package br.com.hardcoded.gildit.extension

import android.text.Editable

fun Editable.withoutSpaces() = this.replace(Regex("\\s"), "")