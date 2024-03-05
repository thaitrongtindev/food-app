package com.example.foodapp.ui.activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.ui.fragment.HomeFragment
import com.example.foodapp.viewmodel.MealViewModel
import com.example.foodapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var idMeal: String
    private lateinit var nameMeal: String
    private lateinit var thumbMeal: String
    private lateinit var youtubeLink: String
    private lateinit var mealViewModel: MealViewModel
    private lateinit var mealDatabase: MealDatabase
    private lateinit var mealViewModelFactory: MealViewModelFactory
    private lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInformationFromIntent()
        setInfoInViews()

        loaddingCase()
        mealDatabase = MealDatabase.getInstance(this)
        mealViewModelFactory = MealViewModelFactory(mealDatabase)
        mealViewModel = ViewModelProvider(this, mealViewModelFactory).get(MealViewModel::class.java)
        mealViewModel.getMealDetails(idMeal)

        observerMealdDetailsLiveData()
        onYoutubeImageClick()

        onClickFavorite()

    }

    private fun onClickFavorite() {
        binding.btnSave1.setOnClickListener{
            meal.let { meal ->
                Log.e("meal", meal.toString() )
                mealViewModel.insertMeal(meal)
                Toast.makeText(this, "Insert Success", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealdDetailsLiveData() {
        mealViewModel.observerMealDetailsLivedata().observe(
            this, Observer {
                onResponseCase()
                meal = it
                binding.tvAreaInfo.text = it.strArea
                binding.tvCategoryInfo.text = it.strCategory
                binding.tvInstructions.text = it.strInstructions
                youtubeLink = it.strYoutube!!
                            }
        )
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext)
            .load(thumbMeal)
            .into(binding.imgMealDetail)


        binding.collapsingToolbar.title = nameMeal
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getInformationFromIntent() {
        val intent = intent
        idMeal = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        nameMeal = intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        thumbMeal = intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()

    }
    private fun loaddingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave1.visibility = View.INVISIBLE
        binding.tvInstructions.visibility= View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave1.visibility = View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}