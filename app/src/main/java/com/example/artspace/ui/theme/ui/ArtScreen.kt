package com.example.artspace.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artspace.R

@Composable
fun ArtScreen(viewModel: ArtViewModel = viewModel()) {
    // Подключаемся к StateFlow из ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Альбомная ориентация
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(uiState.imageRes),
                contentDescription = null,
                modifier = Modifier.weight(1f).fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            ArtInfo(
                titleRes = uiState.titleRes,
                artistRes = uiState.artistRes,
                modifier = Modifier.weight(1f),
                onPrev = { viewModel.previous() },
                onNext = { viewModel.next() }
            )
        }
    } else {
        // Портретная ориентация
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(uiState.imageRes),
                contentDescription = null,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            ArtInfo(
                titleRes = uiState.titleRes,
                artistRes = uiState.artistRes,
                modifier = Modifier.fillMaxWidth(),
                onPrev = { viewModel.previous() },
                onNext = { viewModel.next() }
            )
        }
    }
}

// Вспомогательная функция для отображения текста и кнопок (чтобы не дублировать код)
@Composable
fun ArtInfo(
    titleRes: Int,
    artistRes: Int,
    modifier: Modifier = Modifier,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(artistRes),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onPrev, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.previous))
            }
            Button(onClick = onNext, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.next))
            }
        }
    }
}