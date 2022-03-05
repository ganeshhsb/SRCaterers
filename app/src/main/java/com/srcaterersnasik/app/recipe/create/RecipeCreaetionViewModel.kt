package com.srcaterersnasik.app.recipe.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.srcaterersnasik.model.Category
import com.srcaterersnasik.model.Recipe
import com.srcaterersnasik.repo.CategoryRepository
import com.srcaterersnasik.repo.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeCreationViewModel @Inject constructor(
    application: Application,
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository,
) :
    AndroidViewModel(application) {

    private val recipeCreateViewModelResponse: MutableLiveData<Command> =
        MutableLiveData()

    fun getObserver(): LiveData<Command> {
        return recipeCreateViewModelResponse
    }

    private val categoryResponse: MutableLiveData<Command> =
        MutableLiveData()

    fun getCategoryObserver(): LiveData<Command> {
        return categoryResponse
    }

    sealed class Command {
        sealed class FetchCategories : Command() {
            sealed class Request : FetchCategories() {
                object FetchCategories : Request()
            }

            sealed class Response : FetchCategories() {
                class Categories(val categories: List<Category>) : Response()
            }
        }

        sealed class CreateRecipe : Command() {
            sealed class Request : CreateRecipe() {
                class CreateRecipe(
                    val category: Category,
                    val recipeName: String,
                    val ingredients: String,
                ) : Request()
            }

            sealed class Response : CreateRecipe() {
                class CreatedRecipe(val recipe: Recipe) : Response()
            }
        }

        sealed class FetchRecipe : Command() {
            sealed class Request : FetchRecipe() {
                class Fetch(recipeId:String) : Request()
            }

            sealed class Response : FetchRecipe() {
                class FetchRecipe(val recipe: Recipe) : Response()
            }
        }

        sealed class DeleteRecipe : Command() {
            sealed class Request : CreateRecipe() {
                class Delete(recipeId: String) : Request()
            }

            sealed class Response : CreateRecipe() {
                class DeleteResponse(val recipe: Recipe) : Response()
            }
        }
    }

    fun processCommand(request: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            when (request) {
                is Command.CreateRecipe.Request.CreateRecipe -> {
                    val recipe =
                        recipeRepository.addNewRecipe(request.recipeName, request.ingredients)
                    sendResponse(Command.CreateRecipe.Response.CreatedRecipe(recipe))
                }

                is Command.FetchCategories.Request.FetchCategories -> {
                    val categories = categoryRepository.getAllCategories()
                    sendCategoryFetchResponse(Command.FetchCategories.Response.Categories(categories))
                }
            }
        }
    }

    private fun sendResponse(response: Command) {
        viewModelScope.launch(Dispatchers.Main) {
            recipeCreateViewModelResponse.postValue(response)
        }
    }

    private fun sendCategoryFetchResponse(response: Command) {
        viewModelScope.launch(Dispatchers.Main) {
            categoryResponse.postValue(response)
        }
    }
}