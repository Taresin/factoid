package com.company.factoid.api

import com.company.factoid.api.dto.FeedResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.http.GET

interface DataFeedService {
    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getFacts(): Observable<FeedResponse>

    companion object {
        fun getService(retrofit: Retrofit): DataFeedService = retrofit.create(DataFeedService::class.java)
    }
}