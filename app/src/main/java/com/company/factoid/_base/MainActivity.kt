package com.company.factoid._base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.factoid.R
import com.company.factoid.model.Fact
import com.company.factoid.ui.FactListView
import com.company.factoid.ui.FactPresenter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), FactListView {

    private val presenter: FactPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

    override fun displayTitle(title: String?) {
        setTitle(title)
    }

    override fun displayFacts(factList: List<Fact>?) {
        // TODO
    }
}