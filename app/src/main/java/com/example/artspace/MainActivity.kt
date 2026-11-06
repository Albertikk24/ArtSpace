package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    // Индекс текущей картинки (от 0 до 4)
    var currentIndex by remember { mutableIntStateOf(0) }

    // Выбираем картинку, заголовок и автора в зависимости от индекса
    val (imageRes, titleRes, artistRes) = when (currentIndex) {
        0 -> Triple(R.drawable.art1, R.string.art1_title, R.string.art1_artist)
        1 -> Triple(R.drawable.art2, R.string.art2_title, R.string.art2_artist)
        2 -> Triple(R.drawable.art3, R.string.art3_title, R.string.art3_artist)
        3 -> Triple(R.drawable.art4, R.string.art4_title, R.string.art4_artist)
        else -> Triple(R.drawable.art5, R.string.art5_title, R.string.art5_artist)
    }

    // Проверяем ориентацию экрана
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Альбомная ориентация: картинка слева, текст и кнопки справа
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            ArtInfoColumn(
                titleRes = titleRes,
                artistRes = artistRes,
                modifier = Modifier.weight(1f),
                onPrev = { if (currentIndex > 0) currentIndex-- },
                onNext = { if (currentIndex < 4) currentIndex++ }
            )
        }
    } else {
        // Портретная ориентация: картинка сверху, текст и кнопки снизу
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            ArtInfoColumn(
                titleRes = titleRes,
                artistRes = artistRes,
                modifier = Modifier.fillMaxWidth(),
                onPrev = { if (currentIndex > 0) currentIndex-- },
                onNext = { if (currentIndex < 4) currentIndex++ }
            )
        }
    }
}

@Composable
fun ArtInfoColumn(
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
            Button(
                onClick = onPrev,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.previous))
            }
            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}