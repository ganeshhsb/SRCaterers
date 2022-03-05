package com.srcaterersnasik.app.category.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.srcaterersnasik.model.Category
import com.srcaterersnasik.repo.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryCreationViewModel @Inject constructor(
    application: Application,
    private val categoryRepository: CategoryRepository,
) : AndroidViewModel(application) {
    private var categoryInDB: Category? = null
    private val categoryCreateViewModelResponse: MutableLiveData<Command> =
        MutableLiveData()

    fun getObserver(): LiveData<Command> {
        return categoryCreateViewModelResponse
    }

    sealed class Command {
        sealed class DeleteCategory : Command() {
             class DeleteCategoryRequest() : DeleteCategory()
             class DeleteCategoryResponse(val category: Category?) : DeleteCategory()
        }

        sealed class FetchCategory : Command() {
            sealed class Request : FetchCategory() {
                class FetchCategory(val categoryId: String) : Request()
            }

            sealed class Response : FetchCategory() {
                class FetchCategory(val category: Category?) : Response()
            }
        }

        sealed class CreateCategory : Command() {
            sealed class Request : CreateCategory() {
                class CreateCategory(val categoryName: String) : Request()
            }

            sealed class Response : CreateCategory() {
                class CreateCategory(val category: Category) : Response()
            }
        }
    }

    fun processCommand(request: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            when (request) {
                is Command.CreateCategory.Request.CreateCategory -> {
                    val category = if (categoryInDB == null) {
                        categoryRepository.addNewCategory(request.categoryName)
                    } else {
                        categoryRepository.replaceCategory(request.categoryName, categoryInDB!!)
                    }

                    sendResponse(Command.CreateCategory.Response.CreateCategory(category))
                }

                is Command.FetchCategory.Request.FetchCategory -> {
                    val category = categoryRepository.getCategoryById(request.categoryId)
                    categoryInDB = category
                    sendResponse(Command.FetchCategory.Response.FetchCategory(category))
                }
                is Command.DeleteCategory.DeleteCategoryRequest -> {
                    categoryInDB?.let { categoryRepository.deleteCategory(it) }
                    sendResponse(Command.DeleteCategory.DeleteCategoryResponse(categoryInDB))
                }
            }
        }
    }

    private fun sendResponse(response: Command) {
        viewModelScope.launch(Dispatchers.Main) {
            categoryCreateViewModelResponse.postValue(response)
        }
    }

    fun setCategory(category: Category) {
        sendResponse(Command.FetchCategory.Response.FetchCategory(category))
    }
}