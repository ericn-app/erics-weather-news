package app.ericn.ericsweather.ui.main.network

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {

    @GET("orders")
    fun fetchOrdersObservable(): Observable<OrdersResponse>
}