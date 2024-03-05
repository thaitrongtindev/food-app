package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.MealByCategory

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    private var mealList = ArrayList<MealByCategory>()
    public lateinit var onItemClick: ((MealByCategory) -> Unit)
    lateinit var onItemLongClick : ((MealByCategory) -> Unit)

    fun setData(mealList: ArrayList<MealByCategory>) {
        this.mealList.clear()
        this.mealList.addAll(mealList)
        notifyDataSetChanged()
    }

    class PopularViewHolder(val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val meal = mealList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(meal)
            true
        }
    }
}


