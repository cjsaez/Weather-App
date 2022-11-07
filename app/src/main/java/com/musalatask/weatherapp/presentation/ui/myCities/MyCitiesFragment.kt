package com.musalatask.weatherapp.presentation.ui.myCities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.musalatask.weatherapp.common.Constants
import com.musalatask.weatherapp.databinding.FragmentMyCitiesBinding
import com.musalatask.weatherapp.presentation.ui.cityWeather.CityWeatherActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * This fragment show to the user a list of the cities which he already searched
 * ordered alphabetically.
 */
@AndroidEntryPoint
class MyCitiesFragment : Fragment() {

    private var _binding: FragmentMyCitiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyCitiesViewModel
    private lateinit var adapter: MyCitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyCitiesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyCitiesViewModel::class.java]

        adapter = MyCitiesAdapter(
            deleteAction = viewModel::deleteCityByName,
            selectAction = this::selectCity
        )
        binding.cities.layoutManager = LinearLayoutManager(requireContext())
        binding.cities.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.items.collect{

                //A new list of cities is ready to submit to the adapter.
                adapter.submitList(it)
            }
        }

        //Set search view logic for this screen in particular
        (requireActivity() as CityWeatherActivity).requestCitiesWhichContainsAText = {
            //Receive the text and filter the cities by it.
            viewModel.searchByText(it)
        }
        (requireActivity() as CityWeatherActivity).submitTextForSearch = {
            //Receive the text and filter the cities by it.
            viewModel.searchByText(it)
        }
    }

    /**
     * This is called whe the user select a city.
     *
     * @param[cityName] the name of the city which the user select.
     */
    private fun selectCity(cityName: String){
        restoreTheAppBarState(cityName)

        //Setting the result in the stateHandle of the previousBackStackEntry before navigating back to CityWeatherFragment
        //will allow CityWeatherFragment to access the result in the stateHandle of its currentBackStackEntry
        val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(Constants.SELECTED_CITY_KEY, cityName)
        findNavController().navigateUp()
    }

    private fun restoreTheAppBarState(cityName: String){
        (requireActivity() as CityWeatherActivity).supportActionBar?.title = cityName
        (requireActivity() as CityWeatherActivity).setSelectCityMenuItemVisibleState(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as CityWeatherActivity).requestCitiesWhichContainsAText = null
    }
}