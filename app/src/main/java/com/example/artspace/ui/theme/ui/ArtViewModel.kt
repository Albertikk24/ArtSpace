package com.example.artspace.ui

import androidx.lifecycle.ViewModel
import com.example.artspace.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArtViewModel : ViewModel() {
    // Приватное изменяемое состояние
    private val _uiState = MutableStateFlow(ArtUiState())
    // Публичное только для чтения
    val uiState: StateFlow<ArtUiState> = _uiState.asStateFlow()

    // Список ваших картинок и текстов (те самые 5 картинок из прошлой лабы)
    private val artworks = listOf(
        Triple(R.drawable.art1, R.string.art1_title, R.string.art1_artist),
        Triple(R.drawable.art2, R.string.art2_title, R.string.art2_artist),
        Triple(R.drawable.art3, R.string.art3_title, R.string.art3_artist),
        Triple(R.drawable.art4, R.string.art4_title, R.string.art4_artist),
        Triple(R.drawable.art5, R.string.art5_title, R.string.art5_artist)
    )

    init {
        // При запуске показываем первую картинку (индекс 0)
        updateState(0)
    }

    fun next() {
        // Переключаем на следующую, не выходя за пределы списка
        val newIndex = (_uiState.value.currentIndex + 1).coerceAtMost(artworks.size - 1)
        updateState(newIndex)
    }

    fun previous() {
        // Переключаем на предыдущую, не выходя за пределы списка
        val newIndex = (_uiState.value.currentIndex - 1).coerceAtLeast(0)
        updateState(newIndex)
    }

    private fun updateState(index: Int) {
        val (img, title, artist) = artworks[index]
        _uiState.update {
            it.copy(
                currentIndex = index,
                imageRes = img,
                titleRes = title,
                artistRes = artist
            )
        }
    }
}