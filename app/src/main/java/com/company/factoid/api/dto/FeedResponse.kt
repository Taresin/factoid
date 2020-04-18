package com.company.factoid.api.dto

import com.company.factoid.model.Fact

data class FeedResponse(
    val title: String?,
    val rows: MutableList<Fact>?
)