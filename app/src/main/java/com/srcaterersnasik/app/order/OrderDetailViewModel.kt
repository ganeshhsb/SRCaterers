package com.srcaterersnasik.app.order

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
class OrderDetailViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    private val orderDetailViewModelResponse: MutableLiveData<Command> =
        MutableLiveData()

    fun getObserver(): LiveData<Command> {
        return orderDetailViewModelResponse
    }

    sealed class Command {

    }

    fun processCommand(request: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            when (request) {

            }
        }
    }

    private fun sendResponse(response: Command){
        viewModelScope.launch(Dispatchers.Main) {

        }
    }
}