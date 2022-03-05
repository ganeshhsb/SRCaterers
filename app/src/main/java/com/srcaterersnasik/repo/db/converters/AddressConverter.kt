package com.srcaterersnasik.repo.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.srcaterersnasik.model.Address
import com.srcaterersnasik.model.Category
import com.srcaterersnasik.model.Event
import com.srcaterersnasik.model.Order
import com.srcaterersnasik.model.Person
import com.srcaterersnasik.model.Recipe
import java.lang.reflect.Type
import java.util.Date

class AddressConverter {
    @TypeConverter
    fun toList(value: String?): List<Address> {
        val listType: Type = object : TypeToken<List<Address?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Address?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class CategoryConverter {
    @TypeConverter
    fun toList(value: String?): List<Category> {
        val listType: Type = object : TypeToken<List<Category?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Category?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class EventConverter {
    @TypeConverter
    fun toList(value: String?): List<Event> {
        val listType: Type = object : TypeToken<List<Event?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Event?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class OrderConverter {
    @TypeConverter
    fun toList(value: String?): List<Order> {
        val listType: Type = object : TypeToken<List<Order?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Order?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


class PersonConverter {
    @TypeConverter
    fun toList(value: String?): List<Person> {
        val listType: Type = object : TypeToken<List<Person?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Person?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class RecipeConverter {
    @TypeConverter
    fun toList(value: String?): List<Recipe> {
        val listType: Type = object : TypeToken<List<Recipe?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Recipe?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class DateConverter{
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}