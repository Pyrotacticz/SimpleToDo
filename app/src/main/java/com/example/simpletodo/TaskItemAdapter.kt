package com.example.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(
    private val listOfItems: List<String>,
    val clickListener: OnClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemLongClicked(position: Int)
        fun onItemClicked(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in our layout
        val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnLongClickListener {
                clickListener.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfItems.get(position)

        holder.textView.text = item

    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

}