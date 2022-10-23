package com.five.zensearch.com.five.zensearch.presentation.create_event.tagsRecycler

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.five.zensearch.R

class TagsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tagName = itemView.findViewWithTag<AppCompatButton>(R.id.button_tag)

}