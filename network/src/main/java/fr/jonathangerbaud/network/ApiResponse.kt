package fr.jonathangerbaud.network


interface ApiResponse<T>
{
    fun getData(): T
    fun isError(): Boolean
    fun getErrorMsg(): String?
    fun getErrorCode(): Int?
    fun getErrorExtra(): Any?
}