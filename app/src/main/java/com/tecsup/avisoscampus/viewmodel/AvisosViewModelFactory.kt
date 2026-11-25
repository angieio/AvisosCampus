package com.tecsup.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.avisoscampus.data.AvisosRepository

class AvisosViewModelFactory(
    private val repository: AvisosRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == AvisosViewModel::class.java) {
            return AvisosViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel no compatible: ${modelClass.name}")
    }
}
