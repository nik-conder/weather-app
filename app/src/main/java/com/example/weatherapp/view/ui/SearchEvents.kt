package com.example.weatherapp.view.ui

sealed class SearchEvents {
    class Check(val city: String) : SearchEvents()
}