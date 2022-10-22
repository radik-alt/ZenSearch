package com.five.zensearch.com.five.zensearch.presentation.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.five.zensearch.com.five.zensearch.domain.model.PostModel
import com.five.zensearch.databinding.PostCardBinding
import java.text.SimpleDateFormat
import java.util.*

class EventsListRecyclerAdapter(
    private val subscribe: (eventId: String) -> Unit,
    private val unsubscribe: (eventId: String) -> Unit
) :
    ListAdapter<PostModel, EventsListRecyclerAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent, subscribe, unsubscribe)
    }

    class PostViewHolder private constructor(
        private val binding: PostCardBinding,
        private val subscribe: (eventId: String) -> Unit,
        private val unsubscribe: (eventId: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostModel) {
            binding.tvEventName.text = item.name
            binding.tvLocation.text = item.address
            binding.tvDate.text = item.date?.let {
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(
                    it
                )
            }
            binding.tvTime.text = item.date?.let {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                    it
                )
            }
            binding.description.text = item.description

            binding.btnJoin.setOnClickListener {
                subscribe(item.id!!)
                binding.btnJoin.visibility = View.GONE
                binding.btnJoinCancel.visibility = View.VISIBLE
            }
            binding.btnJoinCancel.setOnClickListener {
                unsubscribe(item.id!!)
                binding.btnJoin.visibility = View.VISIBLE
                binding.btnJoinCancel.visibility = View.GONE
            }
        }

        companion object {
            fun from(parent: ViewGroup,
                     subscribe: (eventId: String) -> Unit,
                     unsubscribe: (eventId: String) -> Unit): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostCardBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding, subscribe, unsubscribe)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}