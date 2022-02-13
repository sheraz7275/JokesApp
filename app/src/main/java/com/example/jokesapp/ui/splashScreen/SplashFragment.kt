package com.example.jokesapp.ui.splashScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.jokesapp.R
import com.example.jokesapp.dataStore.PreferenceDataStore
import com.example.jokesapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FragmentScoped
@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(inflater)

        (activity as AppCompatActivity).supportActionBar?.hide()

        val viewModel1: SplashScreenViewModel by viewModels()
        viewModel = viewModel1

        setObservers()

        lifecycleScope.launch {
            delay(3000L)
            makeNavigation()
        }

        return binding.root
    }

    private suspend fun makeNavigation() {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_splashFragment2_to_jokesListFragment)
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.setAppCountData()

            viewModel.appCounter.collect {
                binding.txtAppOpen.text = it.toString()
            }

        }
    }
}