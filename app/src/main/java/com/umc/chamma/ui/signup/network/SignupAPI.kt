package com.umc.chamma.ui.signup.network

import com.umc.chamma.ui.signup.model.SignupPostData
import com.umc.chamma.ui.signup.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignupAPI {
    @POST("/api/auth/signup")
    fun signup(
        @Body params : SignupPostData
    ): Call<SignupResponse>
}