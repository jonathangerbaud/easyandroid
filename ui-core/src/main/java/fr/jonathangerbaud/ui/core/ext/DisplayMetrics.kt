package fr.jonathangerbaud.ui.core.ext

import fr.jonathangerbaud.core.BaseApp
import fr.jonathangerbaud.core.ext.getDisplayMetrics

private val metrics = BaseApp.instance.getDisplayMetrics()

fun deviceWidth(): Int =  metrics.widthPixels
fun deviceHeight(): Int =  metrics.heightPixels