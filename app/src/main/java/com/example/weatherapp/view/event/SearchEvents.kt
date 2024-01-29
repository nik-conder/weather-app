package com.example.weatherapp.view.event

sealed class SearchEvents {
    class Check(val city: String) : SearchEvents()
}