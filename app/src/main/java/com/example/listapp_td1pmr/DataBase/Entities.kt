package com.example.listapp_td1pmr.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
        @PrimaryKey val userId: Int,
        @ColumnInfo(name= "pseudo") val pseudo: String,
        @ColumnInfo(name= "passe") val passe: String,
        @ColumnInfo(name= "hash") val hash: String
    )

@Entity
data class TodoList (
        @PrimaryKey val todoListId: Int,
        @ColumnInfo val userId: Int,
        @ColumnInfo(name= "label") val label: String
)

@Entity
data class Item (
    @PrimaryKey val itemId: Int,
    @ColumnInfo val listId: Int,
    @ColumnInfo val userId: Int,
    @ColumnInfo(name= "description") val description: String,
    @ColumnInfo(name= "fait") val fait: Boolean
)