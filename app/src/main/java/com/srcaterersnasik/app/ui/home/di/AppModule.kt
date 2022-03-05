package com.srcaterersnasik.app.ui.home.di

import android.content.Context
import com.srcaterersnasik.SRCatererApp
import com.srcaterersnasik.repo.SRCaterersDatabase
import com.srcaterersnasik.repo.db.CategoryDao
import com.srcaterersnasik.repo.db.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app:Context): SRCaterersDatabase {
        val srCatererApp:SRCatererApp = app as SRCatererApp
        return srCatererApp.appDatabase
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: SRCaterersDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: SRCaterersDatabase): RecipeDao {
        return database.recipeDao()
    }

}