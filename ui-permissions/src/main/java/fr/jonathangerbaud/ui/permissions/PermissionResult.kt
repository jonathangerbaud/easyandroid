package fr.jonathangerbaud.ui.permissions


class PermissionResult internal constructor(val granted:List<String>, val denied:List<String>, val rationale:List<String>)
{
    fun areAllGranted():Boolean = granted.isNotEmpty() && denied.isEmpty() && rationale.isEmpty()
    fun areAllDenied():Boolean = granted.isEmpty() && denied.isNotEmpty() && rationale.isEmpty()
    fun areAllRationale():Boolean = granted.isEmpty() && denied.isEmpty() && rationale.isNotEmpty()
}