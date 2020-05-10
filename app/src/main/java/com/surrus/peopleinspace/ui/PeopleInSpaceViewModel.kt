package com.surrus.peopleinspace.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surrus.common.remote.Assignment
import com.surrus.common.repository.PeopleInSpaceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.logging.Logger

@ExperimentalCoroutinesApi
class PeopleInSpaceViewModel(peopleInSpaceRepository: PeopleInSpaceRepository) : ViewModel() {
    val peopleInSpace = MutableStateFlow<List<Assignment>>(emptyList())

    init {
        viewModelScope.launch {
            val people = peopleInSpaceRepository.fetchPeople()
            peopleInSpace.value = people
        }
    }
}