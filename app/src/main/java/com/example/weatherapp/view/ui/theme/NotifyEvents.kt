package com.example.weatherapp.view.ui.theme

import com.example.weatherapp.view.ui.components.NotifyUI

sealed class NotifyEvents {
    class Add(val notify: NotifyUI.Notify): NotifyEvents()
    object Delete: NotifyEvents()
}