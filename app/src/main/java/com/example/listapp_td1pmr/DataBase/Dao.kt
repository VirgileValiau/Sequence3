package com.example.listapp_td1pmr.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE pseudo LIKE :pseudo AND " +
            "passe LIKE :passe LIMIT 1")
    fun getFromPseudoPasse(pseudo: String, passe: String): User

    @Insert
    fun insertAll(user: User)
}

@Dao
interface ListDao{

    @Query("SELECT * FROM TodoList WHERE userId LIKE :uid")
    fun getFromUserId(uid : Int): List<TodoList>

    @Insert
    fun insertAll(vararg lists: TodoList)

}

@Dao
interface ItemDao{
    @Query("SELECT * FROM Item WHERE listId LIKE :lid")
    fun getFromUserId(lid : Int): List<Item>

    @Insert
    fun insertAll(vararg items: Item)
}