package com.company.factoid.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.company.factoid.R
import com.company.factoid.io.ImageRepo
import com.company.factoid.model.Fact
import com.company.factoid.utils.presentText
import kotlinx.android.synthetic.main.list_item_fact.view.*

class FactListAdapter(
    private val imageRepo: ImageRepo
) :
    RecyclerView.Adapter<FactListAdapter.FactViewHolder>() {
    var list: List<Fact> = emptyList()

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_fact, parent, false)
        return FactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(list[position], imageRepo)
    }

    class FactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        Observer<Drawable?> {
        var fact: Fact? = null
        val imageView: ImageView = itemView.image
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

        fun bind(fact: Fact, imageRepo: ImageRepo) {
            this.fact?.let { imageRepo.unsubscribe(it, this) }
            this.fact = fact

            itemView.title.presentText(fact.title)
            itemView.description.presentText(fact.description)
            val imageUrl = fact.imageHref
            if (imageUrl == null) {
                imageView.setImageDrawable(null)
                imageView.visibility = View.GONE
            } else {
                imageRepo.subscribe(fact, this)
            }
        }

        override fun onChanged(t: Drawable?) {
            imageView.visibility = if (t == null) View.GONE else View.VISIBLE
            Glide.with(imageView)
                .load(t)
                .into(imageView)
        }
    }
}