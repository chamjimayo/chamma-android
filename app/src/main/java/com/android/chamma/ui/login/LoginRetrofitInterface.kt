package com.android.chamma.ui.login

import com.android.chamma.models.loginmodel.LoginPostData
import com.android.chamma.models.loginmodel.LoginResponse
import com.android.chamma.util.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRetrofitInterface {


    @POST("/api/auth/login")
    fun checkUuid(
        @Body params : LoginPostData,
        @Header("x-api-key") api : String? = Constants.xapikey
    ) : Call<LoginResponse>
}