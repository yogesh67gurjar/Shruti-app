package com.shrutiapp.adapters

import android.content.Context
import android.provider.SyncStateContract.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shrutiapp.R
import com.shrutiapp.data.Folders
import com.shrutiapp.interfaces.RecyclerViewClickListener
import com.shrutiapp.utils.MyConstants

class FoldersAdapter(
    private val list: MutableList<String>,

    val recyclerViewClickListener: RecyclerViewClickListener
) :
    RecyclerView.Adapter<FoldersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_folders, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleUnit = list[position]

        holder.name.text = singleUnit

        holder.itemView.setOnClickListener(View.OnClickListener {
            if (holder.adapterPosition !== RecyclerView.NO_POSITION) {
                recyclerViewClickListener.onClick(position, MyConstants.FOLDERS)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }
}