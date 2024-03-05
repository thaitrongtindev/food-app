package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.viewmodel.HomeViewModel


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
  private lateinit var categoriesAdapter: CategoriesAdapter
  private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observerCategoryLiveData()
    }

    private fun observerCategoryLiveData() {
        viewModel.observerCategoriesLiveData().observe(viewLifecycleOwner,
            Observer { category -> categoriesAdapter.setCategories(category) })
    }

    private fun prepareRecyclerView() {
       binding.rvCategory.apply {
           layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
           adapter = categoriesAdapter
       }
    }


}