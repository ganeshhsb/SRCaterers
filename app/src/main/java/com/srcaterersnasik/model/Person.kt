package com.srcaterersnasik.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
class Person {
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    var person_id:String = ""

    @ColumnInfo(name = "name")
    var name:String? = null

    @ColumnInfo(name = "phone_number")
    var phoneNumber:String? = null

//    @ColumnInfo(name = "address")
    @Embedded
    var address:Address? = null

    @ColumnInfo(name = "address_proof_id")
    var addressProofId:String? = null
}