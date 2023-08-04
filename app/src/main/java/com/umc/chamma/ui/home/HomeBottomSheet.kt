package com.umc.chamma.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.umc.chamma.databinding.FragmentHomeBottomsheetBinding
import com.umc.chamma.models.homemodel.MarkerData
import com.umc.chamma.ui.qr.QRActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeBottomSheet(val data : MarkerData) : BottomSheetDialogFragment() {

    private var _binding : FragmentHomeBottomsheetBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = data.restroomName
        binding.tvDistance.text = "내 위치로부터 ${data.distance?.toInt()}m"
        binding.tvRating.text = data.reviewRating.toString()
        if(data.publicOrPaid == "public") binding.tvType.text = "무료화장실"
        else binding.tvType.text = "유료화장실"
        binding.ratingbar.rating = data.reviewRating!!

        binding.btnUse.setOnClickListener {
            startActivity(Intent(requireContext(),QRActivity::class.java))
        }
    }

    //    binding.tvName.text = data.restroomName
//    binding.tvDistance.text = "내 위치로부터" +  if(data.distance != null)"${data.distance.toInt()}m" else "0m"
//    binding.tvRating.text = data.reviewRating.toString()
//    if(data.publicOrPaid == "public") binding.tvType.text = "무료화장실"
//    else binding.tvType.text = "유료화장실"
//    binding.ratingbar.rating = data.reviewRating ?: 0.0F


}