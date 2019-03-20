package fr.jonathangerbaud.core.ext

fun String.contains(from: List<String>): Boolean
{
    for (s in from)
    {
        if (contains(s))
            return true
    }

    return false
}