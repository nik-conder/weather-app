package com.example.weatherapp.view.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout

sealed class NotifyUI {
    sealed class NotifyStatus {
        object Error: NotifyStatus()
        object Success: NotifyStatus()
        object Neutral: NotifyStatus()
    }
    data class Notify(
        val id: Int,
        val title: String,
        val text: String? = null,
        val status: NotifyStatus
    )
}

@Composable
fun Notify(state: Boolean, notificationsList: List<NotifyUI.Notify>) {
    AnimatedVisibility(modifier = Modifier.zIndex(1f), visible = state) {
        LazyColumn(userScrollEnabled = true) {
             items(notificationsList) {
                 NotifyMsg(notify = it)
             }
        }
    }
}

@Composable
fun NotifyMsg(notify: NotifyUI.Notify) {
    val colorBackground = when(notify.status) {
        NotifyUI.NotifyStatus.Success -> Color(red = 1, green = 150, blue = 7, alpha = 255)
        NotifyUI.NotifyStatus.Error -> Color(red = 255, green = 0, blue = 0, alpha = 255)
        NotifyUI.NotifyStatus.Neutral -> Color(red = 126, green = 126, blue = 126, alpha = 255)
    }
    val colorText = when(notify.status) {
        NotifyUI.NotifyStatus.Success -> Color.White
        NotifyUI.NotifyStatus.Error -> Color.White
        NotifyUI.NotifyStatus.Neutral -> Color.Black
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .background(colorBackground, RoundedCornerShape(20.dp))
            .wrapContentHeight()
            .fillMaxWidth(0.9f)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Text(
                    text = notify.title,
                    fontSize = 14.sp,
                    color = colorText,
                    fontWeight = FontWeight(700)
                )
            }
            if (notify.text != null) {
                Row() {
                    Text(
                        text = notify.text,
                        fontSize = 12.sp,
                        color = colorText
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.NEXUS_5, showSystemUi = true)
@Composable
fun PreviewNotify() {
    val stateMsg = remember { mutableStateOf(true) }

    val list: List<NotifyUI.Notify> = listOf(
        NotifyUI.Notify(id = 1, title = "Сообщение", status = NotifyUI.NotifyStatus.Error),
        NotifyUI.Notify(id = 2, title = "Второе сообщение", status = NotifyUI.NotifyStatus.Success, text = "дополнительный текст"),
        NotifyUI.Notify(id = 3, title = "dsfdsfsdfsdfsdf dsfs dfsd fsdf sdf sdf сообщение", status = NotifyUI.NotifyStatus.Success)
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Notify(state = stateMsg.value, notificationsList = list)
        ConstraintLayout(modifier = Modifier
            .padding(paddingValues)
            .background(Color.Gray)) {
            Text(text = "какой то контент \n\n\n\n123213")
            Button(onClick = {
                stateMsg.value = !stateMsg.value
            }) {
                Text(text = if (stateMsg.value) "Скрыть" else "Показать")
            }
        }

    }

}