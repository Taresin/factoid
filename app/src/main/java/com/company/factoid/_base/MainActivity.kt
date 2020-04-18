package com.company.factoid._base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.factoid.R
import com.company.factoid.model.Fact
import com.company.factoid.ui.FactListAdapter
import com.company.factoid.ui.FactListPresenterView
import com.company.factoid.ui.FactPresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), FactListPresenterView {

    private val presenter: FactPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter.onCreate()
    }

    override fun displayTitle(title: String?) {
        setTitle(title)
    }

    override fun displayFacts(factList: List<Fact>?) {
        list.adapter = FactListAdapter(factList ?: emptyList())
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}