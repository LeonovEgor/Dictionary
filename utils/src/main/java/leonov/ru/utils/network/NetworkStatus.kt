package leonov.ru.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class NetworkStatus(context: Context): INetworkStatus {

    private val networkStatus = MutableLiveData(true)

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, object: ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                networkStatus.postValue(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkStatus.postValue(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkStatus.postValue(true)
            }
        })
    }

    override fun isOnline(): LiveData<Boolean> = networkStatus
}