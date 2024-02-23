package com.example.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.adapters.PopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.ui.activity.MealActivity
import com.example.foodapp.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: PopularAdapter


    companion object {
        const val MEAL_ID = "com.example.foodapp.ui.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.ui.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.ui.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        popularItemAdapter = PopularAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        homeViewModel.getPopularItems()
        preparePopularItemRecyclerView()


        observerPopularIteamMeals()
        onClickPopularItem()

    }

    private fun onClickPopularItem() {
        popularItemAdapter.onItemClick = {meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerPopularIteamMeals() {
        homeViewModel.observerPopularItemLiveData().observe(
            viewLifecycleOwner, {mealList -> popularItemAdapter.setData(mealList = mealList as ArrayList<CategoryMeals>)}
        )
        Log.e("popularItemAdapter", popularItemAdapter.toString())
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter

        }

        binding.recViewMealsPopular.adapter = popularItemAdapter
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }


    }

    private fun observerRandomMeal() {
        homeViewModel.observerRandonMealLiveData().observe(
            viewLifecycleOwner
                , object: Observer<Meal> {
                override fun onChanged(value: Meal) {
                    Glide.with(this@HomeFragment)
                        .load(value.strMealThumb)
                        .into(binding.imgRandomMeal)

                    randomMeal = value
                }



            }

        )
    }

}