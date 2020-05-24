package ru.leonov.repository.datasource

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import leonov.ru.model.data.SearchResult
import ru.leonov.repository.api.ApiService
import ru.leonov.repository.api.BaseInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation : DataSource<List<SearchResult>> {

    override suspend fun getData(word: String) =
        getService(BaseInterceptor.interceptor)
            .searchAsync(word)
            .await()

    private fun getService(interceptor: Interceptor) =
        createRetrofit(interceptor).create(ApiService::class.java)


    private fun createRetrofit(interceptor: Interceptor) = Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create(getGsonBuilder()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()

    private fun getGsonBuilder() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}
