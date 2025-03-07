package com.example.checkyourlife

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    val colors = listOf(
        Color(0xFFFF0000), Color(0xFFFF4500), Color(0xFFFF7F00), Color(0xFFFFA500), Color(0xFFFFFF00), Color(0xFFBFFF00),
        Color(0xFF00FF00), Color(0xFF4CAF50), Color(0xFF26D9C3), Color(0xFF00FFFF), Color(0xFF008B8B), Color(0xFF0000FF),
        Color(0xFF4169E1), Color(0xFF3F51B5), Color(0xFF8B00FF), Color(0xFF9C27B0), Color(0xFFE91E63), Color(0xFFFF00FF),
        Color(0xFFFFC107), Color(0xFF795548), Color(0xFFA9A9A9), Color(0xFFB0B0B0), Color(0xFFD3D3D3), Color(0xFF000000),
        Color(0xFFFFCDD2), Color(0xFFF8BBD0), Color(0xFFE1BEE7), Color(0xFFD1C4E9), Color(0xFFC5CAE9), Color(0xFFBBDEFB),
        Color(0xFFB3E5FC), Color(0xFFB2EBF2), Color(0xFFB2DFDB), Color(0xFFC8E6C9), Color(0xFFDCEDC8), Color(0xFFF0F4C3),
        Color(0xFFFFF9C4), Color(0xFFFFECB3), Color(0xFFFFE0B2), Color(0xFFFFCCBC)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.padding(8.dp)
    ) {
        items(colors.size) { index ->
            var color = colors[index]

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (color == selectedColor) 3.dp else 0.dp,
                        color = if (color == selectedColor) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(color) }
            )
        }
    }
}
