package com.company.factoid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.factoid.R
import com.company.factoid.model.Fact
import kotlinx.android.synthetic.main.list_item_fact.view.*

class FactListAdapter(private val list: List<Fact>) :
    RecyclerView.Adapter<FactListAdapter.FactViewHolder>() {
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_fact, parent, false)
        return FactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fact: Fact) {
            itemView.title.text = fact.title ?: "<No Title>"
            itemView.description.text = fact.description ?: "<No Description>"
        }
    }
}