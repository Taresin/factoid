package com.company.factoid._base

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.company.factoid.api.DataFeedService
import com.company.factoid.io.ImageRepo
import com.company.factoid.ui.FactListPresenterView
import com.company.factoid.ui.FactPresenter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactoidApp : Application() {

    private val dataFeedModule = module {
        fun provideGson(): Gson = GsonBuilder().create()
        fun provideHttpClient(): OkHttpClient = OkHttpClient()
        fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        single { provideGson() }
        single { provideHttpClient() }
        single { provideRetrofit(get(), get()) }
        single { DataFeedService.getService(get()) }
    }

    private val factModule = module {
        single { (activity: AppCompatActivity) -> ImageRepo(activity) }
        single { (view: FactListPresenterView) -> FactPresenter(get(), view, get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FactoidApp)
            androidLogger(level = Level.DEBUG)
            modules(
                dataFeedModule,
                factModule
            )
        }
    }
}