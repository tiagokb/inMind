package com.example.keepinmind.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.keepinmind.R
import com.example.keepinmind.listener.IntentPickListener

class IntentDialogFragment(val listener: IntentPickListener) : DialogFragment() {

    lateinit var camera: ImageView
    lateinit var gallery: ImageView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var root: View = requireActivity().layoutInflater.inflate(R.layout.intent_image_chooser_dialog_fragment, null)

        camera = root.findViewById(R.id.intent_camera)
        gallery = root.findViewById(R.id.intent_gallery)

        val alert = AlertDialog.Builder(activity)
        alert.setView(root)

        camera.setOnClickListener {
            listener.actionPick(1)
            dismiss()
        }

        gallery.setOnClickListener {
            listener.actionPick(2)
            dismiss()
        }

        return alert.create()
    }

}