package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel() : ViewModel() {

    private  var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetails(i: String) {
        RetrofitInstance.api.getMealDetails(i).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.message.toString() )
            }
        })
    }

    fun observerMealDetailsLivedata() : LiveData<Meal> {
        return  mealDetailsLiveData
    }
}