package pl.gg.weather.ui.forecast

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import pl.gg.weather.BuildConfig
import pl.gg.weather.MainActivity
import pl.gg.weather.R
import pl.gg.weather.core.fragment.BaseFragment
import pl.gg.weather.databinding.FragmentForecastBinding
import pl.gg.weather.rest.ApiErrorUtils
import pl.gg.weather.functional.State

@AndroidEntryPoint
class ForecastFragment : BaseFragment<FragmentForecastBinding>(FragmentForecastBinding::inflate){

    private val viewModel by viewModels<ForecastViewModel>()
    private val args by navArgs<ForecastFragmentArgs>()

    private var prevToolbarTitle: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.run {
        super.onViewCreated(view, savedInstanceState)
        if(args.query.isEmpty()) back()

        updateToolbarTitle(args.query)

        viewModel.state.observe(viewLifecycleOwner,{
            binding.loader.isVisible = false
            when(it){
                is State.Loading -> {
                    binding.loader.isVisible = it.value
                }
                is State.Error -> {
                    val error = ApiErrorUtils.parseApiError(it.error)

                    AlertDialog.Builder(view.context)
                        .setTitle(R.string.error)
                        .setMessage(error)
                        .setPositiveButton(R.string.close
                        ) { dialogInterface, _ -> dialogInterface?.dismiss() }.show()

                    back()
                }
                is State.Result -> {
                    binding.dataSource.isVisible = BuildConfig.DEBUG
                    binding.dataSource.text = if(viewModel.isDataFromCache()) R.string.data_from_cache.str else R.string.data_from_api.str

                    val data = it.data.data?.forecastDays?.forecastDay?.firstOrNull()?.hours
                    items.adapter = ForecastHourAdapter(view.context, data ?: listOf(R.string.no_data.str))
                }
            }
        })

        viewModel.querySavedState.observe(viewLifecycleOwner, {
            viewModel.loadWeather()
        })
        viewModel.querySavedState.value = args.query
    }

    private fun updateToolbarTitle(newTitle: String){
        (activity as? MainActivity)?.let{
            prevToolbarTitle = it.toolbarTitle
            it.toolbarTitle = newTitle
        }
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.toolbarTitle = prevToolbarTitle
    }
}