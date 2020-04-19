package com.company.factoid.io

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.company.factoid.model.Fact

class ImageRepo(val activity: AppCompatActivity) {
    var imageMap = mutableMapOf<String, MutableLiveData<Drawable?>>()
    fun fetchImages(list: List<Fact>) {
        list.mapNotNull { it.imageHref }
            .forEach { url ->
                imageMap[url] = MutableLiveData<Drawable?>()
                Glide.with(activity)
                    .asDrawable()
                    .load(Uri.parse(url))
                    .into(object : CustomTarget<Drawable>(200, 200) {

                        override fun onLoadStarted(placeholder: Drawable?) {
                            imageMap[url]?.value = null
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Do nothing here
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            imageMap[url]?.value = resource
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            imageMap[url]?.value = null
                        }
                    })
            }
    }

    fun subscribe(fact: Fact, observer: Observer<Drawable?>) {
        imageMap[fact.imageHref]?.observe(activity, observer)
    }

    fun unsubscribe(fact: Fact, observer: Observer<Drawable?>) {
        imageMap[fact.imageHref]?.removeObserver(observer)
    }
}