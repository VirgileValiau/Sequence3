package com.example.listapp_td1pmr.UI

import Item
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp_td1pmr.API.ApiClient
import com.example.listapp_td1pmr.UI.Adapter.ItemAdapter
import com.example.listapp_td1pmr.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ShowListActivity : AppCompatActivity(),
    ItemAdapter.ActionListener2 {

    private val CAT = "EDPMR"
    var itemListe : MutableList<String> = mutableListOf()
    var idListe:String = "inconnu"
    lateinit var labelListe:String
    lateinit var hash:String
    private val activityScope = CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showlist)

        //Récupération de l'id de la liste et de son label du bundle
        val bdl = this.intent.extras
        idListe = bdl!!.getString("ListId").toString()
        labelListe = bdl!!.getString("label").toString()

        //Affichage du titre de la liste dans le layout
        val titre_liste = findViewById<TextView>(R.id.titre_Liste)
        titre_liste.setText(labelListe + " :")

        //Récupération du hash des préférences
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        hash = sp.getString("token", "").toString()



        getItemsAndDisplay()

        //gestion du button
        val NewItem:EditText = findViewById<EditText>(R.id.et_newItem)
        val CreateBtn: Button = findViewById<Button>(R.id.create_item)
        CreateBtn.setOnClickListener {
            createNewItem(NewItem.text.toString())
        }

    }

    private fun alerter(s: String) {
        Log.i(CAT, s)
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    private fun createNewItem(label:String) {
        activityScope.launch {
            try {
                ApiClient.createItem(label,idListe,hash)
                getItemsAndDisplay()
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }
    }

    private fun getItemsAndDisplay() {
        activityScope.launch {
            try {
                itemListe = mutableListOf()
                val Checks:MutableList<Int> = mutableListOf()
                val itemsIds:MutableList<String> = mutableListOf()

                val Items = ApiClient.getItems(idListe,hash)
                val iterate = Items.listIterator()

                while (iterate.hasNext()) {
                    var item:Item = iterate.next()
                    Log.i(CAT,item.label)
                    itemListe.add(item.label)
                    Checks.add(item.checked)
                    itemsIds.add(item.id.toString())
                }
                val myAdapter =
                    ItemAdapter(
                        this@ShowListActivity,
                        itemsIds,
                        Checks,
                        itemListe
                    )
                myAdapter.notifyDataSetChanged()

                //gestion du recyclerView
                var recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter= myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@ShowListActivity)
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }
    }


    override fun onItemClicked(idItem: String, checked: Int) {
        activityScope.launch {
            try {
                ApiClient.checkItem(idListe,idItem,checked,hash)
                getItemsAndDisplay()
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }
    }

}
