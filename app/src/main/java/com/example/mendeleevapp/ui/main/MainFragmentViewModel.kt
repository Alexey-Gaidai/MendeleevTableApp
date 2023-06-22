package com.example.mendeleevapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mendeleevapp.domain.PeriodicElementsRepository
import com.example.mendeleevapp.domain.model.Element
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: PeriodicElementsRepository) :
    ViewModel() {
    private val _elements: MutableLiveData<List<Element>> = MutableLiveData()
    val elements: LiveData<List<Element>> get() = _elements
    private var allElements: List<Element> = emptyList()
    private var currentGroup: Int? = null

    init {
        loadElements()
    }

    private fun loadElements() {
        viewModelScope.launch(Dispatchers.IO) {
            val table = repository.getTable()
            _elements.postValue(table.elements)
            allElements = table.elements
        }
    }

    fun searchElements(query: String) {
        val filteredElements = if (query.isBlank()) {
            allElements // Вернуть все элементы, если поисковый запрос пустой
        } else {
            allElements.filter { element ->
                element.name.contains(
                    query,
                    ignoreCase = true
                ) // Измените условие поиска в соответствии с вашими требованиями
            }
        }
        _elements.value = filteredElements
    }

    fun filterElementsByGroup(periodNumber: Int?) {
        currentGroup = periodNumber
        val filteredElements = if (periodNumber == null) {
            allElements
        } else {
            allElements.filter { element ->
                element.period == periodNumber
            }
        }
        _elements.value = filteredElements
    }
}