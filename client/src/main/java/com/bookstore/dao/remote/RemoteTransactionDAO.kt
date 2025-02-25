package com.bookstore.dao.remote

import com.bookstore.config.AppConfig
import com.bookstore.model.request.transaction.CheckoutRequest
import com.bookstore.model.request.transaction.PaymentRequest
import com.bookstore.model.response.transaction.Transaction
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RemoteTransactionDAO {

    @POST("/api/rest/transaction/checkout")
    suspend fun performCheckout(
        @Header("Authorization") authorization: String,
        @Body checkoutRequest: CheckoutRequest
    ): Transaction

    @POST("/api/rest/transaction/payment")
    suspend fun performPayment(
        @Header("Authorization") authorization: String,
        @Body paymentRequest: PaymentRequest
    ): Transaction

    @GET("/api/rest/transaction/findByUserId/${AppConfig.OAUTH_DEFAULT_CUSTOMER_ID}")
    suspend fun getCheckoutHistory(@Header("Authorization") authorization: String): List<Transaction>
}