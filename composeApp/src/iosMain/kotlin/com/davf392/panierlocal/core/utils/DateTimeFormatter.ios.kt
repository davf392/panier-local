package com.davf392.panierlocal.core.utils

import kotlinx.datetime.LocalDateTime
import platform.Foundation.*
import platform.Foundation.NSDateFormatter

actual fun formatDateTime(start: LocalDateTime, end: LocalDateTime): Pair<String, String> {
    // Helper to convert LocalDateTime to NSDate
    fun LocalDateTime.toNSDate(): NSDate {
        val components = NSDateComponents()
        components.year = year.toLong()
        components.month = monthNumber.toLong()
        components.day = dayOfMonth.toLong()
        components.hour = hour.toLong()
        components.minute = minute.toLong()
        return NSCalendar.currentCalendar.dateFromComponents(components)!!
    }

    val dateFormatter = NSDateFormatter()
    dateFormatter.dateStyle = NSDateFormatterFullStyle
    dateFormatter.timeStyle = NSDateFormatterNoStyle
    
    val timeFormatter = NSDateFormatter()
    timeFormatter.dateStyle = NSDateFormatterNoStyle
    timeFormatter.timeStyle = NSDateFormatterShortStyle

    return Pair(
        dateFormatter.stringFromDate(start.toNSDate()),
        "${timeFormatter.stringFromDate(start.toNSDate())} - ${timeFormatter.stringFromDate(end.toNSDate())}"
    )
}
