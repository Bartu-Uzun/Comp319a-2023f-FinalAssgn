package com.example.havanasiapp.util

import com.example.havanasiapp.domain.model.response.Hour
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object Utils {


     fun getHourValue(hour: Hour): Int {

        return LocalDateTime.parse(
            hour.time,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        ).hour

    }

    fun getDayOfWeek(date: String): String {
        return LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    }
}