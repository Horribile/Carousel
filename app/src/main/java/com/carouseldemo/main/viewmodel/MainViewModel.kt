package com.carouseldemo.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carouseldemo.main.data.Animal
import com.carouseldemo.main.data.AnimalRepository

/**
 * ViewModel for the main activity managing carousel state and animal data
 */
class MainViewModel : ViewModel() {
    
    private val animalRepository = AnimalRepository
    
    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>> = _animals
    
    private val _selectedAnimal = MutableLiveData<Animal>()
    val selectedAnimal: LiveData<Animal> = _selectedAnimal
    
    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> = _selectedPosition
    
    init {
        loadAnimals()
    }
    
    private fun loadAnimals() {
        _animals.value = animalRepository.getAnimals()
        // Set initial selection
        animalRepository.getAnimals().firstOrNull()?.let { firstAnimal ->
            _selectedAnimal.value = firstAnimal
            _selectedPosition.value = 0
        }
    }
    
    fun selectAnimal(position: Int) {
        val animalList = _animals.value ?: return
        if (position in animalList.indices) {
            _selectedAnimal.value = animalList[position]
            _selectedPosition.value = position
        }
    }
    
    fun getAnimalAt(position: Int): Animal? {
        return _animals.value?.getOrNull(position)
    }
    
    fun getAnimalCount(): Int {
        return _animals.value?.size ?: 0
    }
}