package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.FavoritesMealsAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.viewmodel.HomeViewModel


class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: FavoritesMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = FavoritesMealsAdapter()
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        binding.searchBtn.setOnClickListener {
            Toast.makeText(context, "on click", Toast.LENGTH_LONG).show()
            searchMeals()
        }
        observerSearchMealsLiveData()
    }

    private fun observerSearchMealsLiveData() {
        viewModel.observerSearchMealsLiveData().observe(
            viewLifecycleOwner, Observer {
                mealsList ->
                searchAdapter.differ.submitList(mealsList)
            }
        )
    }

    private fun searchMeals() {
        val s = binding.edSearchBox.text.toString()
        if (s.isNotEmpty()){
            viewModel.searchMeals(s)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvSearchMeal.apply {
            layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }


}