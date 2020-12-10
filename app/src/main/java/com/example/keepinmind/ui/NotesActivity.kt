package com.example.keepinmind.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.keepinmind.R
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.viewmodel.NotesActivityViewModel
import com.example.keepinmind.viewmodelfactory.NotesActivityViewModelFactory

class NotesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var cancel: ImageView
    private lateinit var save: ImageView
    private lateinit var viewModel: NotesActivityViewModel

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        viewModel = ViewModelProvider(
            this,
            NotesActivityViewModelFactory(application)
        ).get(NotesActivityViewModel::class.java)

        title = findViewById(R.id.annotation_et_title)
        content = findViewById(R.id.annotation_et_content)
        cancel = findViewById(R.id.annotation_iv_cancel)
        save = findViewById(R.id.annotation_iv_save)

        isEditMode()
        observe()

        cancel.setOnClickListener(this)
        save.setOnClickListener(this)
    }

    private fun isEditMode() {
        val intent: Int = intent.getIntExtra(MainActivity.INTENT_ID_KEY, 0)
        if (intent == 0) {
            return
        }

        id = intent
        viewModel.loadModel(id)
    }

    private fun observe() {
        viewModel.saveResponse.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        viewModel.editModel.observe(this, Observer {
            title.setText(it.title)
            content.setText(it.subTitle)
        })
    }

    override fun onClick(v: View?) {
        v?.id.let {
            when (it) {
                R.id.annotation_iv_save -> {

                    if (id == 0) {
                        val finalTitle = title.text.toString()
                        val finalContent = content.text.toString()
                        viewModel.insertAnnotation(finalTitle, finalContent)
                    } else {
                        val finalTitle = title.text.toString()
                        val finalContent = content.text.toString()
                        val model = NotesModel(id, finalTitle, finalContent)
                        viewModel.updateModel(model)
                    }
                }
                R.id.annotation_iv_cancel -> {
                    makeAlertDialog()
                }
            }
        }
    }

    fun makeAlertDialog(){
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Voltar")
            .setMessage(when (id) {
                0 -> "Tem certeza que deseja sair? Todos os dados serão perdidos"
                else -> "Tem certeza que deseja sair? As modificações não serão salvas"
            })
            .setNegativeButton("Continuar aqui", null)
            .setPositiveButton("Sair") { _, _ ->
                finish()
            }
            .setCancelable(false)

        dialog.create().show()

    }
}