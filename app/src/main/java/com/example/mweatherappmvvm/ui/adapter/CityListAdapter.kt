package com.example.mweatherappmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mweatherapplication.databinding.CityRowBinding
import com.example.mweatherappmvvm.data.model.CityDetailsResponse

/**
 * Adapter class to show city list
 */
class CityListAdapter(val selectCityCallback:(city:CityDetailsResponse)-> Unit) :
    ListAdapter<CityDetailsResponse, CustomViewHolder>(Companion) {

        companion object : DiffUtil.ItemCallback<CityDetailsResponse>() {
            override fun areItemsTheSame(
                oldItem: CityDetailsResponse,
                newItem: CityDetailsResponse
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: CityDetailsResponse,
                newItem: CityDetailsResponse
            ): Boolean {
                return oldItem == newItem
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityRowBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        val itemBinding = holder.binding as CityRowBinding
        itemBinding.cityDetails = currentItem

        itemBinding.onCLickListner = View.OnClickListener { v ->
            navigateToWeatherScreen(v, currentItem)
        }
    }

    private fun navigateToWeatherScreen(view: View, item: CityDetailsResponse) {
        selectCityCallback(item)
    view.findNavController().navigateUp()
    }
}

class CustomViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
