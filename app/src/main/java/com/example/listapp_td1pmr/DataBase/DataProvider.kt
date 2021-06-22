package com.example.listapp_td1pmr.DataBase

object DataProvider {
    private val parcours = mutableListOf<User_local>(
            Parcours(1,"virgile",2),
            Parcours(2,"akash",3)
    )

    fun getUsers(): MutableList<Parcours> {
        return parcours
    }
}