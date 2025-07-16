package com.carouseldemo.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.carouseldemo.controls.Carousel
import com.carouseldemo.controls.CarouselAdapter
import com.carouseldemo.main.databinding.ActivityMainBinding
import com.carouseldemo.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupCarousel()
        observeViewModel()
    }
    
    private fun setupCarousel() {
        val carousel = binding.carousel
        
        // Set up click listener
        carousel.setOnItemClickListener { parent, _, position, _ ->
            viewModel.getAnimalAt(position)?.let { animal ->
                Toast.makeText(
                    this,
                    "${animal.shortName} has been clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        
        // Set up selection listener
        carousel.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.selectAnimal(position)
        }
    }
    
    private fun observeViewModel() {
        viewModel.selectedAnimal.observe(this) { animal ->
            animal?.let {
                binding.selectedItem.text = it.description
            }
        }
    }
}