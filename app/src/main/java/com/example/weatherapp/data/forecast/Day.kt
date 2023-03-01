package com.example.weatherapp.data.forecast

data class Day(
    val avghumidity: Double? = null,
    val avgtemp_c: Double? = null,
    val avgtemp_f: Double? = null,
    val avgvis_km: Double? = null,
    val avgvis_miles: Double? = null,
    val condition: Condition? = null,
    val daily_chance_of_rain: Int? = null,
    val daily_chance_of_snow: Int? = null,
    val daily_will_it_rain: Int? = null,
    val daily_will_it_snow: Int? = null,
    val maxtemp_c: Double? = null,
    val maxtemp_f: Double? = null,
    val maxwind_kph: Double? = null,
    val maxwind_mph: Double? = null,
    val mintemp_c: Double? = null,
    val mintemp_f: Double? = null,
    val totalprecip_in: Double? = null,
    val totalprecip_mm: Double? = null,
    val totalsnow_cm: Double? = null,
    val uv: Double? = null,
)