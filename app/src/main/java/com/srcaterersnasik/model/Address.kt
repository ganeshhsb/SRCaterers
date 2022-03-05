package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
class Address {
    @PrimaryKey
    @ColumnInfo(name = "address_id")
    var address_id:String = ""

    @ColumnInfo(name = "address1")
    var address1:String? = null

    @ColumnInfo(name = "address2")
    var address2:String? = null

    @ColumnInfo(name = "address3")
    var address3:String? = null

    @ColumnInfo(name = "city")
    var city:String? = null

    @ColumnInfo(name = "state")
    var state:String? = null

    @ColumnInfo(name = "country")
    var country:String? = null

    @ColumnInfo(name = "postal_code")
    var postalCode:String? = null
}