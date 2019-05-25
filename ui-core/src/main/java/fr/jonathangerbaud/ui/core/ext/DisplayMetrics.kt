package fr.jonathangerbaud.ui.core.ext

import fr.jonathangerbaud.core.AppInstance
import fr.jonathangerbaud.core.ext.getDisplayMetrics

private val metrics = AppInstance.get().getDisplayMetrics()

fun deviceWidth(): Int =  metrics.widthPixels
fun deviceHeight(): Int =  metrics.heightPixels