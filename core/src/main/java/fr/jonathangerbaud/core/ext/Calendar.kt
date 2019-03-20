package fr.jonathangerbaud.core.ext

import java.util.*

var Calendar.year: Int
    get() = get(Calendar.YEAR)
    set(value) {
        set(Calendar.YEAR, value)
    }

var Calendar.month: Int
    get() = get(Calendar.MONTH)
    set(value) {
        set(Calendar.MONTH, value)
    }

var Calendar.dayOfMonth: Int
    get() = get(Calendar.DAY_OF_MONTH)
    set(value) {
        set(Calendar.DAY_OF_MONTH, value)
    }

var Calendar.dayOfWeek: Int
    get() = get(Calendar.DAY_OF_WEEK)
    set(value) {
        set(Calendar.DAY_OF_WEEK, value)
    }

var Calendar.dayOfYear: Int
    get() = get(Calendar.DAY_OF_YEAR)
    set(value) {
        set(Calendar.DAY_OF_YEAR, value)
    }

var Calendar.hours: Int
    get() = get(Calendar.HOUR)
    set(value) {
        set(Calendar.HOUR, value)
    }

var Calendar.hours24: Int
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) {
        set(Calendar.HOUR_OF_DAY, value)
    }

var Calendar.minutes: Int
    get() = get(Calendar.MINUTE)
    set(value) {
        set(Calendar.MINUTE, value)
    }

var Calendar.seconds: Int
    get() = get(Calendar.SECOND)
    set(value) {
        set(Calendar.SECOND, value)
    }

var Calendar.milliseconds: Int
    get() = get(Calendar.MILLISECOND)
    set(value) {
        set(Calendar.MILLISECOND, value)
    }

fun Calendar.monthStr():String = intToStr(month + 1)
fun Calendar.dayOfMonthStr():String = intToStr(dayOfMonth)
fun Calendar.dayOfWeekStr():String = intToStr(dayOfWeek)
fun Calendar.dayOfYearStr():String = intToStr(dayOfYear)
fun Calendar.hoursStr():String = intToStr(hours)
fun Calendar.hours24Str():String = intToStr(hours24)
fun Calendar.minutesStr():String = intToStr(minutes)
fun Calendar.secondsStr():String = intToStr(seconds)
fun Calendar.millisecondsStr():String = intToStr(milliseconds)
fun Calendar.isSameDay(other:Calendar):Boolean = year == other.year && month == other.month && dayOfMonth == other.dayOfMonth
fun Calendar.clearTime():Calendar {
    val c = clone() as Calendar
    c.hours24 = 0
    c.minutes = 0
    c.seconds = 0
    c.milliseconds = 0
    return c
}


private fun intToStr(value:Int):String = if (value < 10) "0$value" else value.toString()