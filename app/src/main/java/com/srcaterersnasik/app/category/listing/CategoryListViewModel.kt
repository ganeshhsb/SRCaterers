package com.srcaterersnasik.app.category.listing

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
class CategoryListViewModel @Inject constructor(
    application: Application,
    private val categoryRepository: CategoryRepository,
) :
    AndroidViewModel(application) {
    private val categoryListViewModelResponse: MutableLiveData<Command.FetchCategory.Response> =
        MutableLiveData()

    fun getObserver(): LiveData<Command.FetchCategory.Response> {
        return categoryListViewModelResponse
    }

    sealed class Command {
        sealed class FetchCategory : Command() {
            sealed class Request : FetchCategory() {
                object FetchCategory : Request()
            }

            sealed class Response : FetchCategory() {
                class CategoryList(val categoryList: List<Category>) : Response()
            }
        }
    }

    fun processCommand(request: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            when (request) {
                is Command.FetchCategory -> {
                    val categories = categoryRepository.getAllCategories()
                    val response = Command.FetchCategory.Response.CategoryList(categories)
                    sendResponse(response)
                }
            }
        }
    }

    private fun sendResponse(response: Command.FetchCategory.Response) {
        viewModelScope.launch(Dispatchers.Main) {
            categoryListViewModelResponse.postValue(response)
        }

    }
}