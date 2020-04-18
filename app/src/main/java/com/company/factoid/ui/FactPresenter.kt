package com.company.factoid.ui

import com.company.factoid.api.DataFeedService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FactPresenter(
    private val dataFeedService: DataFeedService,
    private val view: FactListView
) {

    fun onCreate() {
        retrieveData()
    }

    private fun retrieveData() {
        dataFeedService.getFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.displayTitle(it.title)
                    view.displayFacts(it.rows)
                },
                { },
                { }
            )
    }
}