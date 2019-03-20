package fr.jonathangerbaud.core.ext

inline fun Boolean.cond(trueBlock: () -> Unit, falseBlock: () -> Unit)
{
    if (this) trueBlock() else falseBlock()
}

fun Boolean?.isNullOrFalse() = this == null || this == false
fun Boolean?.isNullOrTrue() = this == null || this == true
fun Boolean?.isFalseIfNotNull() = this != null && this == false
fun Boolean?.isTrueIfNotNull() = this != null && this == true