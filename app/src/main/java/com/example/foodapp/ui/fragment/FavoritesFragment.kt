package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.adapters.FavoritesMealsAdapter
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Objects

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter
    lateinit var binding: FragmentFavoritesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        favoritesMealsAdapter = FavoritesMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        observerFavoritesMealsLiveData()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.delete(favoritesMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal Deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo", View.OnClickListener {
                        viewModel.insertMeal(favoritesMealsAdapter.differ.currentList[position])
                    }).show()

            }

        }


        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }



    private fun prepareRecyclerView() {
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,
                 3, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesMealsAdapter
        }
    }
    private fun observerFavoritesMealsLiveData() {
        viewModel.observerFavoriteMealLiveData().observe(
            requireActivity(), Observer {
                favoritesMealsAdapter.differ.submitList(it)
            }
        )
    }


}