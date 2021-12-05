package pl.gg.weather.ui.location

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.gg.weather.BuildConfig
import pl.gg.weather.core.fragment.BaseFragment
import pl.gg.weather.databinding.FragmentPassLocationBinding
import pl.gg.weather.functional.validator.InputValidator
import pl.gg.weather.util.blockClick


@AndroidEntryPoint
class PassLocationFragment : BaseFragment<FragmentPassLocationBinding>(FragmentPassLocationBinding::inflate) {

    val viewModel by viewModels<PassLocationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.run {
        super.onViewCreated(view, savedInstanceState)
        binding.city.inputValidator = InputValidator.NOT_EMPTY

        apply.setOnClickListener {
            it.blockClick {
                if(!listOf(binding.city).all { it.validate() }){
                    return@setOnClickListener
                }

                val action = PassLocationFragmentDirections.showForecastFragment(binding.city.text)
                navController.navigate(action)
            }
        }

        resetCache.isVisible = BuildConfig.DEBUG
        resetCache.setOnClickListener {
            viewModel.clearCache()
        }
    }
}