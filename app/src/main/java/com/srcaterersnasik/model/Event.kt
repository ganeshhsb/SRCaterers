package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "event")
class Event {

    @PrimaryKey
    @ColumnInfo(name = "event_id")
    var event_id:String = ""

    @ColumnInfo(name="name")
    var name:String? = null

    @ColumnInfo(name="time")
    var time: Date? = null

//    @ColumnInfo(name="address")
    @Embedded
    var address:Address? = null

    @ColumnInfo(name="contact_person")
    var contactPersons:List<Person>? = null

    @ColumnInfo(name = "total_no_of_people")
    var totalNumberOfPeople:Int? =null

    @ColumnInfo(name="recipe_list")
    var recipeList:List<Recipe>? = null
}