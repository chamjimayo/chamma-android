package com.umc.chamma.ui.home.restroomInfo


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
//import com.android.chamma.R
import com.umc.chamma.R
import com.umc.chamma.databinding.ActivityReviewBinding
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData
import com.umc.chamma.ui.home.restroomInfo.model.ReviewData2
import com.umc.chamma.ui.home.restroomInfo.model.ReviewResponse
import com.umc.chamma.util.BottomSheet

class ReviewActivity : com.umc.chamma.config.BaseActivityVB<ActivityReviewBinding>(ActivityReviewBinding::inflate),
ReviewActivityInterface{
    private lateinit var bottomSheet: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Id= intent.getIntExtra("ID",0)

        binding.toolBar.setNavigationOnClickListener {
            finish()//왜안되냐!->잘못된 범위의 괄호안에다 넣음
        }

        val dataList:ArrayList<ReviewData2> = arrayListOf()

        dataList.apply{
            add(
                ReviewData2("나는참지않쥐", R.drawable.review_profile,"23.","07.",11,5f,
                "화장실 너무 깨끗해요\n휴지도 넉넉하게 있어서 좋았어요!")
            )
            add(
                ReviewData2("읏차",R.drawable.review_profile2,"23.","06.",30,5f,
                "관리가 잘 되어 있어요~")
            )
            add(
                ReviewData2("minjji7",R.drawable.review_profile3,"23.","06.",30,4f,
                "냄새 탈취제 있으면 더 좋을거 같음")
            )
            add(
                ReviewData2("dkwe32",R.drawable.review_profile4,"23.","06.",28,5f,
                "깔끔해요 화장실 급할 때 이 근처면 여기만 갈 것 같아요")
            )
            add(
                ReviewData2("급해",R.drawable.review_profile5,"23.","06.",21,5f,
                "화장실 너무 깨끗해요\n휴지도 넉넉하게 있어서 좋았어요!")
            )
        }


        val dataRVAdapter = ReviewDataAdapter(dataList)

        binding.recyclerView.adapter=dataRVAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

        binding.optionBtn.setOnClickListener{
            BottomSheet.reviewSequence(this,binding)
        }
        ReviewService(this).tryToGetReviewList(Id)
    }

    override fun onTryToGetRLSuccess(response: ReviewResponse) {
        Log.d("리뷰결과",response.toString())
    }

    override fun onTryToGetRLFailure(message: String) {
        Log.d("리뷰결과",message)
    }

}