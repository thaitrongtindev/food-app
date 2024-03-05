package com.example.foodapp.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentMealBottomSheetBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var meal: MealByCategory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        obserserMealsBottomSheetLiveData()
    }
    fun setMeal(meal: MealByCategory) {
        this.meal = meal
    }

    private fun obserserMealsBottomSheetLiveData() {
        viewModel.observerMealBottomSheetLiveData().observe(
            viewLifecycleOwner, Observer { meal ->
                Glide.with(this)
                    .load(meal.strMealThumb).into(binding.imgBottomSheet)
                binding.tvBottomSheetArea.text = meal.strArea
                binding.tvBottomSheetName.text = meal.strMeal
                binding.tvBottomSheetCategory.text = meal.strCategory
            }
        )
    }


}