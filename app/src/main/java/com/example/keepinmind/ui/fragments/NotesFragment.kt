package com.example.keepinmind.ui.fragments

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keepinmind.R
import com.example.keepinmind.adapters.MainAdapter
import com.example.keepinmind.listener.MainListener
import com.example.keepinmind.listener.MenuSelectListener
import com.example.keepinmind.models.NotesModel
import com.example.keepinmind.ui.NotesActivity
import com.example.keepinmind.ui.MainActivity
import com.example.keepinmind.ui.PostActivity
import com.example.keepinmind.viewmodel.MainActivityViewModel
import com.example.keepinmind.viewmodelfactory.MainActivityViewModelFactory

class NotesFragment : Fragment() {

    companion object{
        val KEY_NOTES_FRAGMENT = "key_notes_fragments"
    }

    private lateinit var homeViewModel: MainActivityViewModel
    private lateinit var rv: RecyclerView
    private lateinit var mAdapter: MainAdapter
    private var list: List<NotesModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes, container, false)

        homeViewModel =
            ViewModelProvider(this, MainActivityViewModelFactory(getApplicationContext())).get(
                MainActivityViewModel::class.java
            )

        rv = root.findViewById(R.id.main_rv)
        rv.layoutManager = GridLayoutManager(root.context, 2)
        mAdapter = MainAdapter()
        mAdapter.setListener(object : MainListener {
            override fun onclick(id: Int) {
                editModel(id)
            }

            override fun onLongClick(model: NotesModel) {
                openMenuOptions(model)
            }

        })
        rv.adapter = mAdapter

        observe()

        return root
    }

    private fun openMenuOptions(model: NotesModel) {
        val dialogFragment = MenuDialogFragment(model, object : MenuSelectListener {
            override fun select(selectedAction: String, model: NotesModel) {
                when (selectedAction){
                    MenuDialogFragment.MODE_DELETE -> deleteConfirmation(model)
                    MenuDialogFragment.MODE_POST -> {
                        val intent = Intent(requireContext(), PostActivity::class.java)
                        intent.putExtra(KEY_NOTES_FRAGMENT, model.id)
                        startActivity(intent)
                    }
                }
            }
        })

        dialogFragment.show(requireActivity().supportFragmentManager, "MenuFragment")
    }

    private fun observe() {
        homeViewModel.newList.observe(viewLifecycleOwner, Observer {
            list = it
            mAdapter.updatelist(list)
        })
        homeViewModel.updateList.observe(viewLifecycleOwner, Observer {
            if (it) {
                loadList()
            } else {
                Toast.makeText(context, "Erro ao excluir, tente novamente", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun editModel(id: Int) {
        val intent: Intent = Intent(this.context, NotesActivity::class.java)
        intent.putExtra(MainActivity.INTENT_ID_KEY, id)
        startActivity(intent)
    }

    fun deleteConfirmation(model: NotesModel) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Excluir anotação")
            .setMessage("A anotação será excluida permanentemente. \nTem certeza que deseja continuar?")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Excluir") { _, _ ->
                homeViewModel.deleteModel(model)
            }
            .setCancelable(false)

        alertDialog.create().show()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    private fun loadList() {
        homeViewModel.loadList()
    }

    private fun getApplicationContext(): Application {
        val application = activity?.application
        return application!!
    }
}