package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealsItemBinding
import com.example.foodapp.pojo.MealByCategory

class CategoryMealsAdapter() : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    var mealsList = ArrayList<MealByCategory>()

    fun setMealsList(mealsList: List<MealByCategory>) {
        this.mealsList = mealsList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }
    class CategoryMealsViewHolder( val binding: MealsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val binding = MealsItemBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryMealsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meals = mealsList.get(position)
        Glide.with(holder.itemView).load(meals.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meals.strMeal
    }
}