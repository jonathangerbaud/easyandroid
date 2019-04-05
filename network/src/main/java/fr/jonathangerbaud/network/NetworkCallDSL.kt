package fr.jonathangerbaud.network

import androidx.lifecycle.MutableLiveData
import fr.jonathangerbaud.core.ext.e
import fr.jonathangerbaud.network.ext.awaitResult
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception


class CallHandler<T : Any>
{
    lateinit var client: Deferred<Response<T>>

    @Suppress("UNCHECKED_CAST")
    fun makeCall(): MutableLiveData<Resource<T>>
    {

        val result = MutableLiveData<Resource<T>>()
        result.setValue(Resource.loading(null))

        GlobalScope.launch {
            try
            {
                val data = client.awaitResult().getOrThrow()

                withContext(Dispatchers.Main)
                {
                    if (data is ApiResponse<*>)
                    {
                        val apiResponse = data as ApiResponse<T>

                        result.value =
                            if (apiResponse.isError())
                                Resource.error(
                                    apiResponse.getErrorMsg(),
                                    apiResponse.getErrorCode(),
                                    apiResponse.getErrorExtra()
                                )
                            else Resource.success(apiResponse.getData())
                    }
                    else
                    {
                        result.value = Resource.success(data)
                    }
                }
            }
            catch (throwable: Throwable)
            {
                e(throwable)
                withContext(Dispatchers.Main) { result.value = Resource.error(0) }
            }
            catch (exception: HttpException)
            {
                withContext(Dispatchers.Main) {
                    result.value = Resource.error("${exception.message} | code ${exception.response().code()}", 0)
                }
                exception.printStackTrace()
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

fun <T : Any> networkCall(block: CallHandler<T>.() -> Unit): MutableLiveData<Resource<T>> =
    CallHandler<T>().apply(block).makeCall()

class CallApiHandler<T: Any, RESPONSE:ApiResponse<T>>
{
    lateinit var client: Deferred<Response<RESPONSE>>

    @Suppress("UNCHECKED_CAST")
    fun makeCall(): MutableLiveData<Resource<T>>
    {

        val result = MutableLiveData<Resource<T>>()
        result.setValue(Resource.loading(null))

        GlobalScope.launch {
            try
            {
                val apiResponse = client.awaitResult().getOrThrow()

                withContext(Dispatchers.Main)
                {
                    result.value =
                        if (apiResponse.isError())
                            Resource.error(
                                apiResponse.getErrorMsg(),
                                apiResponse.getErrorCode(),
                                apiResponse.getErrorExtra()
                            )
                        else Resource.success(apiResponse.getData())
                }
            }
            catch (throwable: Throwable)
            {
                e(throwable)
                withContext(Dispatchers.Main) { result.value = Resource.error(0) }
            }
            catch (exception: HttpException)
            {
                withContext(Dispatchers.Main) {
                    result.value = Resource.error("${exception.message} | code ${exception.response().code()}", 0)
                }
                exception.printStackTrace()
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
//fun <RESPONSE: DataResponse<*>, DATA: Any> networkCall(block: CallHandler<RESPONSE, DATA>.() -> Unit): MutableLiveData<Resource<DATA>>
//        = CallHandler<RESPONSE, DATA>().apply(block).makeCall()
fun <T: Any, RESPONSE:ApiResponse<T>> networkApiCall(block: CallApiHandler<T, RESPONSE>.() -> Unit): MutableLiveData<Resource<T>> =
    CallApiHandler<T, RESPONSE>().apply(block).makeCall()