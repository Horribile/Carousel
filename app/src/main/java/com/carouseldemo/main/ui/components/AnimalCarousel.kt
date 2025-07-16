package com.carouseldemo.main.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.carouseldemo.main.data.Animal
import kotlinx.coroutines.launch
import kotlin.math.*

@Composable
fun AnimalCarousel(
    animals: List<Animal>,
    selectedIndex: Int,
    onAnimalSelected: (Int) -> Unit,
    onAnimalClicked: (Animal) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    
    // Animation for rotation
    val rotationAnimation = remember { Animatable(0f) }
    
    // Calculate target rotation based on selected index
    val targetRotation = -selectedIndex * (360f / animals.size)
    
    // Animate to target rotation when selectedIndex changes
    LaunchedEffect(selectedIndex) {
        rotationAnimation.animateTo(
            targetValue = targetRotation,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        // Snap to nearest item
                        val currentRotation = rotationAnimation.value
                        val anglePerItem = 360f / animals.size
                        val targetIndex = ((-currentRotation / anglePerItem).roundToInt() % animals.size + animals.size) % animals.size
                        onAnimalSelected(targetIndex)
                    }
                ) { _, dragAmount ->
                    scope.launch {
                        val sensitivity = with(density) { 0.5f / density }
                        rotationAnimation.snapTo(
                            rotationAnimation.value + dragAmount.x * sensitivity
                        )
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        animals.forEachIndexed { index, animal ->
            CarouselItem(
                animal = animal,
                index = index,
                totalItems = animals.size,
                rotation = rotationAnimation.value,
                isSelected = index == selectedIndex,
                onClick = { onAnimalClicked(animal) },
                onItemSelected = { onAnimalSelected(index) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CarouselItem(
    animal: Animal,
    index: Int,
    totalItems: Int,
    rotation: Float,
    isSelected: Boolean,
    onClick: () -> Unit,
    onItemSelected: () -> Unit
) {
    // Calculate position on circle
    val anglePerItem = 360f / totalItems
    val itemAngle = index * anglePerItem + rotation
    val angleRad = Math.toRadians(itemAngle.toDouble())
    
    // Carousel radius
    val radius = 120.dp
    val radiusPx = with(LocalDensity.current) { radius.toPx() }
    
    // Calculate 3D position
    val x = (sin(angleRad) * radiusPx).toFloat()
    val z = (cos(angleRad) * radiusPx).toFloat()
    
    // Scale and alpha based on z position (depth)
    val normalizedZ = (z + radiusPx) / (2 * radiusPx) // Normalize to 0-1
    val scale = 0.6f + 0.4f * normalizedZ // Scale from 0.6 to 1.0
    val alpha = 0.3f + 0.7f * normalizedZ // Alpha from 0.3 to 1.0
    
    Card(
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer {
                translationX = x
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .zIndex(z),
        onClick = {
            if (isSelected) {
                onClick()
            } else {
                onItemSelected()
            }
        },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = animal.imageRes),
                contentDescription = animal.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
            
            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            
            // Animal name text
            Text(
                text = animal.shortName,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp)
            )
        }
    }
}