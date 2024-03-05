package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealsItemBinding
import com.example.foodapp.pojo.Meal

class FavoritesMealsAdapter() :
    RecyclerView.Adapter<FavoritesMealsAdapter.FavoritesMealsViewHolder>() {

    class FavoritesMealsViewHolder(val binding: MealsItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this , differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        val binding = MealsItemBinding.inflate(LayoutInflater.from(parent.context))
        return FavoritesMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meals = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meals.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meals.strMeal
    }
}