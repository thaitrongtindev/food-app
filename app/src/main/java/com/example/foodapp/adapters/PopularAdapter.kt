package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.CategoryMeals

class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    private lateinit var binding: PopularItemBinding
    private var mealList = ArrayList<CategoryMeals>()
    class PopularViewHolder(var binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context))
       return PopularViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)
    }
}