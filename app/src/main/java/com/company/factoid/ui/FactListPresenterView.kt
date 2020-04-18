package com.company.factoid.ui

import com.company.factoid.model.Fact

interface FactListPresenterView {
    fun displayTitle(title: String?)
    fun displayFacts(factList: List<Fact>?)
}