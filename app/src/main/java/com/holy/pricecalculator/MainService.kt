package com.holy.pricecalculator

import ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class MainService {
    interface ApiService {
        @GET("service/evds/series={series}&startDate={startDate}&endDate={endDate}&type={type}&key={key}")
        suspend fun getExchangeRate(

            @Path("series") series: String,
            @Path("startDate") startDate: String,
            @Path("endDate") endDate: String,
            @Path("type") type: String,
            @Path("key") key: String
        ): Response<ExchangeRateResponse>
    }
}