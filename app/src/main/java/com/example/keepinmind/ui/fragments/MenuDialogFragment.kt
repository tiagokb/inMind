package com.example.keepinmind.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.keepinmind.R
import com.example.keepinmind.listener.MenuSelectListener
import com.example.keepinmind.models.NotesModel

class MenuDialogFragment(val model: NotesModel, val listener: MenuSelectListener): DialogFragment() {

    companion object{
        val MODE_DELETE = "delete"
        val MODE_POST = "post"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root: View = requireActivity().layoutInflater.inflate(R.layout.long_click_menu_fragment, null)

        val title = root.findViewById<TextView>(R.id.intent_menu_tv_title)
        val content = root.findViewById<TextView>(R.id.intent_menu_tv_content)

        val delete = root.findViewById<TextView>(R.id.intent_menu_btn_delete)
        val post = root.findViewById<TextView>(R.id.intent_menu_btn_edit_post)

        title.text = model.title
        content.text = model.subTitle

        delete.setOnClickListener{
            listener.select(MODE_DELETE, model)
            dismiss()
        }

        post.setOnClickListener {
            listener.select(MODE_POST, model)
            dismiss()
        }

        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setView(root)

        return alertDialog.create()
    }

}