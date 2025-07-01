package com.apps.aivisioncmp.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.datetime.LocalDateTime

fun String.toFormattedDate(): String {
    // Expected input format: "EEE MMM dd HH:mm:ss zzz yyyy"
    // Example: "Wed Jun 19 14:32:00 UTC 2024"
    val monthMap = mapOf(
        "Jan" to 1, "Feb" to 2, "Mar" to 3, "Apr" to 4,
        "May" to 5, "Jun" to 6, "Jul" to 7, "Aug" to 8,
        "Sep" to 9, "Oct" to 10, "Nov" to 11, "Dec" to 12
    )
    val parts = this.split(" ")

    if (parts.size < 6) return "Invalid date"

    val month = monthMap[parts[1]] ?: return "Invalid month"
    val day = parts[2].toIntOrNull() ?: return "Invalid day"
    val (hour, minute, _) = parts[3].split(":").mapNotNull { it.toIntOrNull() }
    val year = parts[5].toIntOrNull() ?: return "Invalid year"

    val localDateTime = LocalDateTime(year, month, day, hour, minute)

    // Output format: "dd MMM yyyy - HH:mm"
    val monthStr = parts[1]
    val dayStr = day.toString().padStart(2, '0')
    val hourStr = hour.toString().padStart(2, '0')
    val minuteStr = minute.toString().padStart(2, '0')

    return "$dayStr $monthStr $year - $hourStr:$minuteStr"
}

fun Modifier.click(onClick: () -> Unit = {}) = composed {
    this
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                onClick()
            }
        )

}