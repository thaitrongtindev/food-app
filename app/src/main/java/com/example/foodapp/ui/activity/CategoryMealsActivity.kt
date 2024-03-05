package com.example.foodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.ui.fragment.HomeFragment
import com.example.foodapp.viewmodel.CategoryViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCategoryMealsBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        categoryMealsAdapter = CategoryMealsAdapter()


        setUpRecyclerView()


        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        categoryViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryViewModel.observerMealsLiveData().observe(
            this, Observer {
                mealsList -> categoryMealsAdapter.setMealsList(mealsList)
                binding.tvCategoryCount.text = mealsList.size.toString()
            }
        )

    }

    private fun setUpRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2, LinearLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}