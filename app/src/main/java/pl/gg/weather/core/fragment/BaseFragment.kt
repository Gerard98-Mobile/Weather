package pl.gg.weather.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

open class BaseFragment<T: ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding : T? = null
    val binding get() = _binding!!

    val navController: NavController
        get(){
            return findNavController()
        }

    fun back(){
        if(!isVisible) return
        navController.navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val Int.str get() = getString(this)

}