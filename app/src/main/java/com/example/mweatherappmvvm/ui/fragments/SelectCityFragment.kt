package com.example.mweatherappmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mweatherapplication.R
import com.example.mweatherapplication.databinding.FragmentSelectCityBinding
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.viewmodel.SearchCityState
import com.example.mweatherappmvvm.viewmodel.WeatherViewModel
import com.example.mweatherappmvvm.ui.adapter.CityListAdapter
import com.example.mweatherappmvvm.utils.SharedPrefs
import com.example.mweatherappmvvm.utils.showGenericAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectCityFragment : Fragment() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var binding: FragmentSelectCityBinding
    private lateinit var cityListAdapter: CityListAdapter
    private val viewModel by viewModels<WeatherViewModel>()

    override fun onStart() {
        super.onStart()
        viewModel.resetSearchState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true) // Enable options menu in the fragment
        // Inflate the layout for this fragment
        binding = FragmentSelectCityBinding.bind(
            inflater.inflate(R.layout.fragment_select_city, container, false)
        )
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        cityListAdapter = CityListAdapter(::setSelectedCityDetails)
        binding.cityListRv.adapter = cityListAdapter
    }

    private fun updateCityList(list: List<CityDetailsResponse>) {
        cityListAdapter.submitList(list)
    }

    private fun setSelectedCityDetails(city: CityDetailsResponse) {
        sharedPrefs.saveCityDetails(city)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchUIState
                    .onEach { state -> handleStateChange(state) }
                    .collect()
            }
        }
    }


    private fun handleStateChange(state: SearchCityState) {
        when (state) {
            is SearchCityState.Idle -> Unit
            is SearchCityState.Success -> {
                updateCityList(state.data)
                handleLoading(false)
            }

            is SearchCityState.Loading -> {
                handleLoading(true)
            }

            is SearchCityState.Fail -> handleFail(state.failMsg)
        }
    }


    private fun handleFail(failMsg: String?) {
        context?.showGenericAlertDialog(
            failMsg ?: resources.getString(R.string.unknown_reason_try_again)
        )
        handleLoading(isLoading = false)
    }

    private fun handleLoading(isLoading: Boolean = false) {
        binding.searchCityPb.isVisible = isLoading
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search operation here
                skipSearchIfEmptyString(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform search operation as the text changes
                skipSearchIfEmptyString(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun skipSearchIfEmptyString(query:String){
        if(query.isEmpty()) return
        viewModel.getCityDetailsByQuery(query)

    }
}