package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(val mealDatabase: MealDatabase) : ViewModel() {
    private  var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealByCategory>>()
    private var categoryListLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
    private val bottomSheetMeals = MutableLiveData<Meal>()
    private var searchMealListLiveData = MutableLiveData<List<Meal>>()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
                Log.e("TAG", "onFailure: ", )
            }

        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.body() != null) {
                    popularItemLiveData.value = response.body()!!.meals
                    Log.e("popularItemLiveData", popularItemLiveData.value.toString() )
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                return error(t.message.toString())
            }

        })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body().let {
                    categoryList -> categoryListLiveData.postValue(categoryList?.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getMealById(id : String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(
            object : Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val meal = response.body()?.meals?.first()
                    meal?.let {
                        meal ->
                        bottomSheetMeals.postValue(meal)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    fun searchMeals(s : String) {
        RetrofitInstance.api.searchMeals(s).enqueue(
            object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    response.body().let {
                        mealList ->
                        searchMealListLiveData.postValue(mealList?.meals)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    TODO("Not yet implemented")
                    return error(t.message.toString())
                }
            }
        )
    }
    fun delete(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun observerRandonMealLiveData() : LiveData<Meal> = randomMealLiveData

    fun observerPopularItemLiveData() : LiveData<List<MealByCategory>> = popularItemLiveData

    fun observerCategoriesLiveData() :LiveData<List<Category>> = categoryListLiveData
    fun observerFavoriteMealLiveData() : LiveData<List<Meal>> = favoriteMealLiveData

    fun observerMealBottomSheetLiveData() : LiveData<Meal> = bottomSheetMeals

    fun observerSearchMealsLiveData() : LiveData<List<Meal>> = searchMealListLiveData
}


