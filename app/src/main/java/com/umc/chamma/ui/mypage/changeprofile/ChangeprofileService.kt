package com.umc.chamma.ui.mypage.changeprofile

import com.umc.chamma.config.App
import com.umc.chamma.config.BaseResponse
import com.umc.chamma.ui.mypage.changeprofile.model.ChangeprofilePostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeprofileService(val view : ChangeprofileFragmentInterface) {



    fun changeUserImg(data : ChangeprofilePostData){
        val changeUserImgRetro = App.getRetro().create(ChangeprofileRetrofitInterface::class.java)
        changeUserImgRetro.changeUserImg(data).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if(response.body() != null){
                    if(response.code() == 200) view.onChangeImgSuccess("성공")
                    else view.onChangeImgFailure("API 통신 실패")
                }else view.onChangeImgFailure("API 통신 실패")
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onChangeImgFailure(t.message.toString())
            }
        })
    }


    fun changeUserNick(data : ChangeprofilePostData){
        val changeUserNickRetro = App.getRetro().create(ChangeprofileRetrofitInterface::class.java)
        changeUserNickRetro.changeUserNick(data).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if(response.body() != null){
                    if(response.code() == 200) view.onChangeNickSuccess("성공")
                    else view.onChangeNickFailure("API 통신 실패")
                }else view.onChangeNickFailure("API 통신 실패")
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onChangeNickFailure(t.message.toString())
            }
        })
    }

}