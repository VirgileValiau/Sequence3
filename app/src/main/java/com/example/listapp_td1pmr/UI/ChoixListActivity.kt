package com.example.listapp_td1pmr.UI

import ToDoList
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp_td1pmr.API.ApiClient
import com.example.listapp_td1pmr.UI.Adapter.ListAdapter
import com.example.listapp_td1pmr.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ChoixListActivity : AppCompatActivity(),
    ListAdapter.ActionListener {

    private val CAT = "EDPMR"
    private var editor: SharedPreferences.Editor? = null
    private lateinit var pseudo:String
    var titleListe : MutableList<String> = mutableListOf()
    lateinit var hash:String
    private val activityScope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Main
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choixlistes)

        val bdl = this.intent.extras
        pseudo = bdl!!.getString("pseudo").toString()

        val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        hash = sp.getString("token", "").toString()


        getListsAndDisplay()




        //gestion du button
        val CreateBtn:Button = findViewById<Button>(R.id.create)
        val NewTitleListe = findViewById<TextView>(R.id.NewList)
        CreateBtn.setOnClickListener {
            createNewList(NewTitleListe.text.toString())
        }
    }

    private fun createNewList(titre:String) {
        activityScope.launch {
            try {
                ApiClient.createListe(titre,hash)
                getListsAndDisplay()
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }
    }

    private fun getListsAndDisplay() {
        activityScope.launch {
            try {
                titleListe = mutableListOf()
                var idListe:MutableList<Int> = mutableListOf()

                val Listes = ApiClient.getListes(hash)
                val iterate = Listes.listIterator()
                while (iterate.hasNext()) {
                    var ToDoListe:ToDoList = iterate.next()
                    Log.i(CAT,ToDoListe.label)
                    titleListe.add(ToDoListe.label)
                    idListe.add(ToDoListe.id)
                }
                val myAdapter =
                    ListAdapter(
                        this@ChoixListActivity,
                        idListe,
                        titleListe
                    )
                myAdapter.notifyDataSetChanged()

                //gestion du recyclerView
                var recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.choixlistes_recycler_view)
                Log.i(CAT,titleListe.toString())
                recyclerView.adapter= myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@ChoixListActivity)
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }
    }

    private fun alerter(s: String) {
        Log.i(CAT, s)
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }




    override fun onListClicked(id:Int,label:String) {

        val bdl = Bundle()
        bdl.putString("ListId", id.toString())
        bdl.putString("label", label)
        // Changer d'activité
        val versShow: Intent
        // Intent explicite
        versShow = Intent(this@ChoixListActivity, ShowListActivity::class.java)
        // Ajout d'un bundle à l'intent
        versShow.putExtras(bdl)
        startActivity(versShow)
    }
}

