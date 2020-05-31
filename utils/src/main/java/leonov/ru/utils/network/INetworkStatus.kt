package leonov.ru.utils.network

import androidx.lifecycle.LiveData

interface INetworkStatus {
    fun isOnline(): LiveData<Boolean>
}