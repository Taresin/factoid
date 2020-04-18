package com.company.factoid.ui

import com.company.factoid.model.Fact

interface FactListView {
    fun displayTitle(title: String?)
    fun displayFacts(factList: List<Fact>?)
}