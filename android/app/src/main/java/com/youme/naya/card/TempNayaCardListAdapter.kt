package com.youme.naya.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youme.naya.R
import com.youme.naya.db.entity.NayaCard
import com.youme.naya.card.TempNayaCardListAdapter.NayaCardViewHolder

class TempNayaCardListAdapter : ListAdapter<NayaCard, NayaCardViewHolder>(NAYA_CARDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NayaCardViewHolder {
        return NayaCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NayaCardViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class NayaCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nayaCardItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            nayaCardItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NayaCardViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return NayaCardViewHolder(view)
            }
        }
    }

    companion object {
        private val NAYA_CARDS_COMPARATOR = object: DiffUtil.ItemCallback<NayaCard>() {
            override fun areItemsTheSame(oldItem: NayaCard, newItem: NayaCard): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: NayaCard, newItem: NayaCard): Boolean {
                return oldItem.NayaCardId == newItem.NayaCardId
            }
        }
    }
//    class NayaCardsComparator : DiffUtil.ItemCallback<NayaCard>() {
//        override fun areItemsTheSame(oldItem: NayaCard, newItem: NayaCard): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: NayaCard, newItem: NayaCard): Boolean {
//            return oldItem.NayaCardId == newItem.NayaCardId
//        }
//    }

}