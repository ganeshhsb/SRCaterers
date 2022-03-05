package com.srcaterersnasik.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.srcaterersnasik.model.Address
import com.srcaterersnasik.model.Category
import com.srcaterersnasik.model.Event
import com.srcaterersnasik.model.Order
import com.srcaterersnasik.model.Person
import com.srcaterersnasik.model.Recipe
import com.srcaterersnasik.repo.db.AddressDao
import com.srcaterersnasik.repo.db.CategoryDao
import com.srcaterersnasik.repo.db.EventDao
import com.srcaterersnasik.repo.db.OrderDao
import com.srcaterersnasik.repo.db.PersonDao
import com.srcaterersnasik.repo.db.RecipeDao
import com.srcaterersnasik.repo.db.converters.AddressConverter
import com.srcaterersnasik.repo.db.converters.CategoryConverter
import com.srcaterersnasik.repo.db.converters.DateConverter
import com.srcaterersnasik.repo.db.converters.EventConverter
import com.srcaterersnasik.repo.db.converters.OrderConverter
import com.srcaterersnasik.repo.db.converters.PersonConverter
import com.srcaterersnasik.repo.db.converters.RecipeConverter

@Database(entities = [Address::class, Category::class, Event::class, Order::class, Person::class, Recipe::class],
    version = 2)
@TypeConverters(value = [AddressConverter::class, CategoryConverter::class, EventConverter::class, OrderConverter::class, PersonConverter::class, RecipeConverter::class, DateConverter::class])
abstract class SRCaterersDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun categoryDao(): CategoryDao
    abstract fun eventDao(): EventDao
    abstract fun orderDao(): OrderDao
    abstract fun personDao(): PersonDao
    abstract fun recipeDao(): RecipeDao
}