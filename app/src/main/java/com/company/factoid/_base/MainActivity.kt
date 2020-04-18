package com.company.factoid._base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.factoid.R
import com.company.factoid.api.DataFeedService
import com.company.factoid.api.dto.FeedResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {

    private val service by inject(DataFeedService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service.getFacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onResponseReceived(it) },
                { onResponseError(it) },
                { onResponseComplete() }
            )
    }

    private fun onResponseReceived(response: FeedResponse) {
        Toast.makeText(this, response.title, Toast.LENGTH_SHORT).show()
    }

    private fun onResponseError(error: Throwable) {

    }

    private fun onResponseComplete() {

    }
}