package com.example.foodapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel() : ViewModel() {
    var mealsLiveData = MutableLiveData<List<MealByCategory>>()
    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(
            object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    response.body()?.let { mealsList ->
                        mealsLiveData.postValue(mealsList.meals)
                    }

                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    return error(t.message.toString())
                }

            }
        )
    }

    fun observerMealsLiveData() : LiveData<List<MealByCategory>> = mealsLiveData
}