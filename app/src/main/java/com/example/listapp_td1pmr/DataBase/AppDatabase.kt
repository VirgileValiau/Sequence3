package com.example.listapp_td1pmr.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(
        User::class,
        TodoList::class,
        Item::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao
}