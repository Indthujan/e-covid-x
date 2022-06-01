package com.app.ecovidx.view.fragment.cart

import com.app.ecovidx.utils.SwipeHelper
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ecovidx.R
import com.app.ecovidx.adapter.CartAdapter
import com.app.ecovidx.databinding.FragmentCartBinding
import com.app.ecovidx.db.entities.Cart
import com.app.ecovidx.view.activity.HomeActivity
import com.app.ecovidx.viewmodel.CartViewModel

class CartFragment : Fragment(R.layout.fragment_cart) {

    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: CartViewModel
    lateinit var adapter: CartAdapter
    lateinit var deletedItem: Cart
    private var itemList = mutableListOf<Cart>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentCartBinding = FragmentCartBinding.bind(view)
        binding = fragmentCartBinding

        viewModel = (activity as HomeActivity).cartViewModel

        adapter = CartAdapter(listOf(), viewModel)

        binding.rvCartItems.layoutManager = LinearLayoutManager(activity)
        binding.rvCartItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(viewLifecycleOwner, Observer {

            if (it.isEmpty()) binding.checkOut.visibility = View.GONE
            adapter.items = it
            itemList = it.toMutableList()
            adapter.notifyDataSetChanged()

            swipeToDelete()
        })

        binding.checkOut.setOnClickListener {
            val currentFragment = requireActivity().supportFragmentManager.findFragmentByTag("fragment_cart_to_checkout")
            if (currentFragment != null) {
                requireActivity().supportFragmentManager.commit {
                    remove(currentFragment)
                }
            }
            requireActivity().supportFragmentManager.commit {
                replace(R.id.cart_to_checkout_container, CheckOutFragment(), "fragment_cart_to_checkout")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
            }
        }
    }

    private fun swipeToDelete() {
        val touchHelper = object : SwipeHelper(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        deletedItem = itemList[position]
                        viewModel.delete(deletedItem)
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(binding.rvCartItems)
    }
}