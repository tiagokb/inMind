package com.example.keepinmind.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.keepinmind.R
import com.example.keepinmind.listener.MainListener
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.viewholders.MainViewHolder

class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var list: List<NotesModel> = listOf()
    private lateinit var listener: MainListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val root: View = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main, parent, false)
        return MainViewHolder(root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list.get(position), listener)
    }

    override fun getItemCount() = list.size

    fun updatelist(list: List<NotesModel>){
        this.list = list
        notifyDataSetChanged()
    }

    fun setListener(listener: MainListener){
        this.listener = listener
    }
}