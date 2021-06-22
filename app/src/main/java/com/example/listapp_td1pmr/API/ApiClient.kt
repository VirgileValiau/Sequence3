package com.example.listapp_td1pmr.API

import ApiService
import Item
import ToDoList
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private val BASE_URL = "http://tomnab.fr/todo-api/"

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ApiService::class.java)

    suspend fun getTokenFromApi(pseudo:String,pass:String): String? {
        val url = "authenticate?user=$pseudo&password=$pass"
        Log.i("EDPMR", url)
        return service.getToken(url).hash
    }

    /*suspend fun getUsers(token: String):MutableList<User_API>{
        val url = "users"
        Log.i("EDPMR", url)
        return service.getUsers(url,token).users
    }*/

    suspend fun createListe(title: String, token: String) {
        val url = "lists?label=$title"
        Log.i("EDPMR", url)
        service.createNewList(url,token)
    }

    suspend fun createItem(label: String,idList:String, token: String) {
        val url = "lists/$idList/items?label=$label"
        Log.i("EDPMR", url)
        service.createNewItem(url,token)
    }

    suspend fun getListes(token:String):MutableList<ToDoList>{
        val url = "lists"
        Log.i("EDPMR", url)
        Log.i("EDPMR", token)
        return service.getListes(url,token).lists
    }

    suspend fun getItems(listId:String, token: String): MutableList<Item> {
        val url = "lists/$listId/items"
        Log.i("EDPMR", url)
        Log.i("EDPMR", token)
        return service.getItems(url,token).items
    }

    suspend fun checkItem(idListe:String,idItem:String,checked:Int, token: String) {
        val url = "lists/$idListe/items/$idItem?check=$checked"
        Log.i("EDPMR", url)
        Log.i("EDPMR", token)
        service.checkItem(url,token)
    }
}