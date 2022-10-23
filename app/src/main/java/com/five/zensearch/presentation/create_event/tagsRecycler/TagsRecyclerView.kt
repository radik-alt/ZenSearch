package com.five.zensearch.com.five.zensearch.presentation.create_event.tagsRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.five.zensearch.R

class TagsRecyclerView(
    private val tags: ArrayList<String>
) : RecyclerView.Adapter<TagsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tags, parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {

//        holder.tagName.setText(tags[position])

    }

    override fun getItemCount(): Int = tags.size
}