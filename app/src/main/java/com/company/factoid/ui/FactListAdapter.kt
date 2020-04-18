package com.company.factoid.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
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
        val imageView = itemView.image
        val target by lazy {
            object : ImageViewTarget<Drawable>(imageView) {
                override fun setResource(resource: Drawable?) {
                    imageView.setImageDrawable(resource)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    super.onResourceReady(resource, transition)
                    imageView.visibility = View.VISIBLE
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    imageView.visibility = View.GONE
                }
            }
        }

        fun bind(fact: Fact) {
            itemView.title.presentText(fact.title)
            itemView.description.presentText(fact.description)
            itemView.image.visibility = View.VISIBLE
            val imageUrl = fact.imageHref
            if (imageUrl == null) {
                imageView.setImageDrawable(null)
                imageView.visibility = View.GONE
            } else {
                Glide.with(imageView)
                    .load(Uri.parse(imageUrl))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(target)
            }
        }
    }
}