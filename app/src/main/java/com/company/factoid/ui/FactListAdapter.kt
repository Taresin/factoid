package com.company.factoid.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.company.factoid.R
import com.company.factoid.model.Fact
import com.company.factoid.utils.presentText
import kotlinx.android.synthetic.main.list_item_fact.view.*

class FactListAdapter() :
    RecyclerView.Adapter<FactListAdapter.FactViewHolder>() {
    var list: List<Fact> = emptyList()

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_fact, parent, false)
        return FactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fact: Fact) {
            itemView.title.presentText(fact.title)
            itemView.description.presentText(fact.description)

            val imageUrl = fact.imageHref
            itemView.image.visibility = if (imageUrl.isNullOrBlank()) View.GONE else View.VISIBLE

            imageUrl?.let {
                Glide.with(itemView.context)
                    .load(Uri.parse(it))
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.image.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.image.visibility = View.VISIBLE
                            return false
                        }
                    }).into(itemView.image)

            }
        }
    }
}