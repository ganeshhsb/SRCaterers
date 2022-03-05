package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "order")
class Order {

    @PrimaryKey
    @ColumnInfo(name = "order_id")
    var order_id:String = ""

    @ColumnInfo(name = "order_date")
    var orderDate:Date? = Date()

    @ColumnInfo(name = "function_name")
    var functionName:String? = null

//    @ColumnInfo(name = "contact_person")
    @Embedded
    var contactPerson:Person? = null

    @ColumnInfo(name = "contact_person_phone")
    var contactPersonPhone:String? = null

    @ColumnInfo(name = "recipe_list")
    var events:List<Event>? = null

}