package com.example.keepinmind.viewholders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.keepinmind.R
import com.example.keepinmind.listener.MainListener
import com.example.keepinmind.models.NotesModel

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById(R.id.item_rv_title) as TextView
    private val subTitle = itemView.findViewById(R.id.item_rv_subtitle) as TextView
    private val layout = itemView.findViewById(R.id.item_cv_layout) as CardView

    fun bind(model: NotesModel, listener: MainListener) {
        title.text = model.title
        subTitle.text = model.subTitle

        layout.setOnClickListener {
            listener.onclick(model.id)
        }

        layout.setOnLongClickListener {
            listener.onLongClick(model)
            return@setOnLongClickListener true
        }
    }

}