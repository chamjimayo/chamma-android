package com.android.chamma.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.chamma.databinding.ItemSearchRecentsearchBinding
import com.android.chamma.ui.search.SearchService
import com.android.chamma.ui.search.model.SearchResultData

class RecentKeywordAdapter(
    private val datas : ArrayList<SearchResultData>,
    private val onItemClickListener: (data : SearchResultData) -> Unit,
    private val onDeleteClickListener : (searchId : Int) -> Unit
) : RecyclerView.Adapter<RecentKeywordAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding : ItemSearchRecentsearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : SearchResultData){
            binding.tvSearchData.text = item.name
            binding.layout.setOnClickListener {
                onItemClickListener(item)
            }
            binding.btnRemoveData.setOnClickListener {
                //TODO searchId 뭔지 알아내서 변경하기
                onDeleteClickListener(0)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemSearchRecentsearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}