package com.company.factoid.utils

import android.view.View
import android.widget.TextView

fun TextView.presentText(text: String?) {
    visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
    this.text = text
}