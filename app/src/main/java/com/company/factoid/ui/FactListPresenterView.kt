package com.company.factoid.ui

import com.company.factoid.model.Fact

interface FactListPresenterView {
    fun showLoading(isLoading: Boolean)
    fun displayTitle(title: String?)
    fun displayFacts(factList: List<Fact>?)
    fun displayError()
}