package com.company.factoid.ui

import com.company.factoid.api.DataFeedService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FactPresenter(
    private val dataFeedService: DataFeedService,
    private val view: FactListPresenterView
) {

    fun onCreate() {
        retrieveData()
    }

    fun onRefresh() {
        retrieveData(false)
    }

    private fun retrieveData(showLoading: Boolean = true) {
        dataFeedService.getFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showLoading(showLoading)
            }
            .doFinally {
                view.showLoading(false)
            }
            .subscribe(
                {
                    view.displayTitle(it.title)
                    view.displayFacts(it.rows)
                },
                {},
                {}
            )
    }
}