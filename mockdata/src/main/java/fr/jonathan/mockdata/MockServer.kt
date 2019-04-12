package fr.jonathan.mockdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.jonathangerbaud.core.ext.d
import fr.jonathangerbaud.core.ext.runDelayed
import fr.jonathangerbaud.network.Resource

class MockServer(private val loadingTimeInMilliSeconds:Long = 3000)
{
    fun <T> single(createObject:(MockGenerator)->T): LiveData<Resource<T>>
    {
        val liveData = MutableLiveData<Resource<T>>()
        liveData.value = Resource.loading(null)

        runDelayed(loadingTimeInMilliSeconds) { liveData.postValue(Resource.success(createObject(MockGenerator()))) }

        return liveData
    }

    fun <T> list(createObject:(MockGenerator)->T, count:Int = 10):LiveData<Resource<List<T>>>
    {
        val liveData = MutableLiveData<Resource<List<T>>>()
        liveData.value = Resource.loading(null)

        runDelayed(loadingTimeInMilliSeconds) {
            d("0")
            val list = arrayListOf<T>()
            d("1")
            val generator = MockGenerator()
            d("1.5")
            for (i in 0..count)
            {
                d("2")
                list.add(createObject(generator))
            }
            d("3")
            liveData.postValue(Resource.success(list))
        }

        return liveData
    }
}
