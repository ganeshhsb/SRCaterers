package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

interface Selectable {
    fun getDisplayName(): String
    fun getId(): String
}

@Entity(tableName = "category")
class Category : Selectable {
    @ColumnInfo(name = "name")
    var name: String? = null

    @PrimaryKey
    @ColumnInfo(name = "category_id")
    var category_id: String = ""
    override fun getDisplayName(): String {
        return name ?: ""
    }

    override fun getId(): String {
        return name ?: ""
    }

    override fun toString(): String {
        return name ?: ""
    }
}