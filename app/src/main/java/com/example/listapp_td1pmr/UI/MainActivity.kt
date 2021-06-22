package com.example.listapp_td1pmr.UI

import User_API
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.listapp_td1pmr.API.ApiClient
import com.example.listapp_td1pmr.DataBase.AppDatabase
import com.example.listapp_td1pmr.DataBase.User
import com.example.listapp_td1pmr.R
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val CAT = "EDPMR"
    private var edtPseudo: EditText? = null
    private var edtPass : EditText? = null
    private var editor: SharedPreferences.Editor? = null
    private val activityScope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main
    )
    var job : Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn:Button = findViewById(R.id.btn_ok)
        edtPseudo = findViewById(R.id.pseudo_edittext)
        edtPass = findViewById(R.id.edit_passe)
        btn.setOnClickListener(this)
        btn.setVisibility(INVISIBLE)

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected) {
            btn.setVisibility(VISIBLE)
        }else{
            alerter("connexion impossible")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_preference) {
            Intent(this, SettingsActivity::class.java).also {
                startActivity(it)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun alerter(s: String) {
        Log.i(CAT, s)
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    override fun onStart(){
        super.onStart()
        val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val l = sp!!.getString("login", "login inconnu")
        edtPseudo!!.setText(l)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ok -> {
                val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                editor = sp.edit()
                editor!!.putString("login", edtPseudo!!.text.toString())
                editor!!.commit()

                Connexion()
            }
        }
    }

    private fun Connexion() {
        activityScope.launch {
            try {
                //appel à l'API
                val hash = ApiClient.getTokenFromApi(edtPseudo?.text.toString(),edtPass?.text.toString())
                if (hash != null) {
                    Log.i(CAT,hash)
                    val sp:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                    editor = sp.edit()
                    editor!!.putString("token", hash)
                    editor!!.commit()

                    //Récupération liste des users dans l'API
                    val users:MutableList<User_API> = ApiClient.getUsers(hash)
                    var uid:Int = 0
                    for (user in users){
                        if (user.pseudo == edtPseudo?.text.toString()){
                            uid = user.id
                        }
                    }

                    //Enregistrement du user dans la dataBase
                    val db = Room.databaseBuilder(
                            applicationContext,
                            AppDatabase::class.java, "Todo"
                    ).build()
                    val userDao = db.userDao()
                    userDao.insertAll(User(uid,edtPseudo?.text.toString(),edtPass?.text.toString(),hash))

                    //Vérification enregistrement
                    val local_users:List<User> = userDao.getAll()
                    for (local_user in local_users){
                        alerter(local_user.pseudo)
                    }

                    val bdl = Bundle()
                    bdl.putString("pseudo", edtPseudo!!.text.toString())
                    // Changer d'activité
                    val versChoix: Intent
                    // Intent explicite
                    versChoix = Intent(this@MainActivity, ChoixListActivity::class.java)
                    // Ajout d'un bundle à l'intent
                    versChoix.putExtras(bdl)
                    startActivity(versChoix)
                }
            } catch (e: Exception) {
                alerter("${e.message} ")
            }
        }


    }

    }