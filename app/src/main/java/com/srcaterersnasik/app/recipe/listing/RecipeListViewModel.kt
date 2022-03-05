package com.srcaterersnasik.app.recipe.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.srcaterersnasik.model.Recipe
import com.srcaterersnasik.repo.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    application: Application,
    private val recipeRepository: RecipeRepository,
) :
    AndroidViewModel(application) {
    private val recipeListViewModelResponse: MutableLiveData<Command.FetchRecipe.Response> =
        MutableLiveData()

    fun getObserver(): LiveData<Command.FetchRecipe.Response> {
        return recipeListViewModelResponse
    }

    sealed class Command {
        sealed class FetchRecipe : Command() {
            sealed class Request : FetchRecipe() {
                object FetchRecipe : Request()
            }

            sealed class Response : FetchRecipe() {
                class RecipeList(val recipeList: List<Recipe>) : Response()
            }
        }
    }

    fun processCommand(request: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            when (request) {
                is Command.FetchRecipe -> {
                    val categories = recipeRepository.getAllRecipe()
                    val response = Command.FetchRecipe.Response.RecipeList(categories)
                    sendResponse(response)
                }
            }
        }
    }

    private fun sendResponse(response: Command.FetchRecipe.Response) {
        viewModelScope.launch(Dispatchers.Main) {
            recipeListViewModelResponse.postValue(response)
        }

    }
}