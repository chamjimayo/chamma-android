package com.umc.chamma.ui.home.restroomInfo

import com.umc.chamma.ui.home.restroomInfo.model.RestroomDetailResponse
import com.umc.chamma.ui.mypage.chargepoint.model.UserinfoResponse
import retrofit2.Call
import retrofit2.http.*

interface RestroomInfoRetrofitInterface {

    @GET("/api/restroom/detail")
    fun tryToGetRestroomDetail(@Query("restroomId") restroomId : Int) : Call<RestroomDetailResponse>


}
