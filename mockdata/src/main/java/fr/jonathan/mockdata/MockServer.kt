package fr.jonathan.mockdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.ioThenMainDelayed
import fr.jonathangerbaud.core.ext.runDelayed
import fr.jonathangerbaud.network.Resource

class MockServer(private val loadingTimeInMilliSeconds: Long = 3000)
{
    fun <T : Any> single(createObject: (MockGenerator) -> T): LiveData<Resource<T>>
    {
        val liveData = MutableLiveData<Resource<T>>()
        liveData.value = Resource.loading(null)

        ioThenMainDelayed(loadingTimeInMilliSeconds, { createObject(MockGenerator()) }) { liveData.postValue(Resource.success(it)) }

        return liveData
    }

    fun <T> list(createObject: (MockGenerator) -> T, count: Int = 10): LiveData<Resource<List<T>>>
    {
        val liveData = MutableLiveData<Resource<List<T>>>()
        liveData.value = Resource.loading(null)

        ioThenMainDelayed(loadingTimeInMilliSeconds, {
            val list = arrayListOf<T>()
            val generator = MockGenerator()
            repeat(count) { list.add(createObject(generator)) }
            list
        }) {
            liveData.postValue(Resource.success(it))
        }

        return liveData
    }
}
