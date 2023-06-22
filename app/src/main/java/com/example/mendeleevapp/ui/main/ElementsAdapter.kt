package com.example.mendeleevapp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mendeleevapp.databinding.ItemElementBinding
import com.example.mendeleevapp.domain.model.Element

class ElementsAdapter(private val onClick: (Element) -> Unit) :
    ListAdapter<Element, ElementsAdapter.ElementsViewHolder>(
        NewsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementsViewHolder {
        val binding = ItemElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ElementsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ElementsViewHolder, position: Int) {
        val stockInfo = getItem(position)
        holder.bind(stockInfo)
    }

    inner class ElementsViewHolder(
        private val binding: ItemElementBinding,
        private val onClick: (Element) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(element: Element) {
            binding.tvWord.text = element.symbol
            binding.tvMass.text = element.atomicMass.toString()
            binding.tvName.text = "- " + element.name.uppercase()
            binding.tvNumber.text = element.number.toString()
            binding.root.setOnClickListener {
                Log.d("adapter", element.number.toString())
                onClick(element)
            }
        }
    }

    private class NewsDiffCallback :
        DiffUtil.ItemCallback<Element>() {
        override fun areItemsTheSame(
            oldItem: Element,
            newItem: Element
        ): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(
            oldItem: Element,
            newItem: Element
        ): Boolean {
            return oldItem == newItem
        }
    }
}