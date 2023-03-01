package com.example.weatherapp.view.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BlockInfo(
    width: Dp,
    content: @Composable() (BoxScope.() -> Unit)
) {
    val colorList = listOf(
        Color(red = 24, green = 110, blue = 202, alpha = 100),
        Color(red = 62, green = 95, blue = 131, alpha = 155)
    )

    val backgroundGradient = Brush.linearGradient(
        colors = colorList,
        start = Offset(width.value * 2, 0f),
        end = Offset(0f, 0f)
    )

    Box(
        modifier = Modifier
            .background(backgroundGradient, RoundedCornerShape(20.dp)),
        content = content
    )
}