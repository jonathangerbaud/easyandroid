package fr.jonathangerbaud.network

import androidx.lifecycle.MutableLiveData
import fr.jonathangerbaud.core.ext.e
import fr.jonathangerbaud.network.ext.awaitResult
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import retrofit2.Response
import java.lang.Exception


class CallHandler<RESPONSE : Any, DATA: Any> {
    private lateinit var client: Deferred<Response<RESPONSE>>

    fun makeCall() : MutableLiveData<Resource<DATA>>
    {

        val result = MutableLiveData<Resource<DATA>>()
        result.setValue(Resource.loading(null))

        launch {
            try {
                val response = client.awaitResult().getOrThrow() as DataResponse<DATA>
                withContext(Dispatchers.Main) { result.value = Resource.success(response.retrieveData()) }
            }
            catch (throwable: Throwable) {
                e(throwable)
                withContext(Dispatchers.Main) { result.value = Resource.error(0) }
            }
            catch (exception: Exception)
            {
                e(exception)
                withContext(Dispatchers.Main) { result.value = Resource.error(0) }
            }
        }

        return result
    }
}

fun <RESPONSE: DataResponse<*>, DATA: Any> networkCall(block: CallHandler<RESPONSE, DATA>.() -> Unit): MutableLiveData<Resource<DATA>> = CallHandler<RESPONSE, DATA>().apply(block).makeCall()

interface DataResponse<T> {
    fun retrieveData(): T
}