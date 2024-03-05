package com.example.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.PopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.MealByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.ui.activity.CategoryMealsActivity
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.activity.MealActivity
import com.example.foodapp.ui.fragment.bottomsheet.MealBottomSheetFragment
import com.example.foodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: PopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "com.example.foodapp.ui.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.ui.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.ui.fragment.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodapp.ui.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemAdapter = PopularAdapter()
        categoriesAdapter = CategoriesAdapter()
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

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        preparePopularItemRecyclerView()


        observerPopularIteamMeals()
        onClickPopularItem()

        /// categories list
        prepareCategoriesItemRecyclerView()
        viewModel.getCategories()
        observerCategories()

        onClickCategory()
        onLongClickPopularItem()

        onClickSearchMeals()

    }

    private fun onClickSearchMeals() {
        binding.imgSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onLongClickPopularItem() {
        popularItemAdapter.onItemLongClick = {
            meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment()
            mealBottomSheetFragment.setMeal(meal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")

        }
    }

    private fun prepareCategoriesItemRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategories() {
        viewModel.observerCategoriesLiveData().observe(
            viewLifecycleOwner, Observer {
               categories ->  categoriesAdapter.setCategories(categories)

            }
        )
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

    private fun onClickCategory() {
        categoriesAdapter.onItemClick = {meal ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,meal.strCategory)

            startActivity(intent)
        }
    }

    private fun observerPopularIteamMeals() {
        viewModel.observerPopularItemLiveData().observe(
            viewLifecycleOwner, {mealList -> popularItemAdapter.setData(mealList = mealList as ArrayList<MealByCategory>)}
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
        viewModel.observerRandonMealLiveData().observe(
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