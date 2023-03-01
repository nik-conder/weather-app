package com.example.weatherapp.view.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Headline(
    text: String
) {
    BoxWithConstraints {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .drawBehind {
                    drawLine(
                        Color.LightGray,
                        Offset(0f, 70f),
                        Offset(this.size.width, 70f),
                        1f
                    )
                }) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHeadline() {
    Headline(text = "Тестовый заголовок")
}