package com.company.factoid.ui

import android.app.Activity
import com.company.factoid.api.DataFeedService
import com.company.factoid.io.ImageRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FactPresenter(
    private val dataFeedService: DataFeedService,
    private val view: FactListPresenterView,
    private val imageRepo: ImageRepo
) {
    lateinit var activity: Activity

    fun onCreate(activity: Activity) {
        this.activity = activity
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
                { response ->
                    view.displayTitle(response.title)
                    response.rows?.let { facts -> imageRepo.fetchImages(facts) }
                    view.displayFacts(response.rows)
                },
                {},
                {}
            )
    }
}