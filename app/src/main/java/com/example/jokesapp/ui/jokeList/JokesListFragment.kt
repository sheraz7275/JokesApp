package com.example.jokesapp.ui.jokeList

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jokesapp.R
import com.example.jokesapp.dataModels.Joke
import com.example.jokesapp.databinding.CustomDialogBinding
import com.example.jokesapp.databinding.FragmentJokesListBinding
import com.example.jokesapp.utils.ApiStatus
import com.example.jokesapp.utils.SetItemClickListener
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@FragmentScoped
@AndroidEntryPoint
class JokesListFragment : Fragment() {

    private lateinit var viewModel: JokesViewModel
    private lateinit var binding: FragmentJokesListBinding
    private lateinit var itemClickListener: SetItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJokesListBinding.inflate(inflater)

        (activity as AppCompatActivity).supportActionBar?.show()

        val viewModel1: JokesViewModel by viewModels()
        viewModel = viewModel1

        itemListener()

        setObservers()

        swipeRefresh()

        return binding.root
    }

    //Observing data stream from network
    private fun setObservers() {

        lifecycleScope.launch {
            viewModel.jokesList.collectLatest {
                binding.recJokes.adapter =
                    it?.let { it1 -> JokesListAdapter(it1, itemClickListener) }
            }
        }

        lifecycleScope.launch {
            viewModel.apiError.collect {
                Snackbar.make(binding.root, it, LENGTH_SHORT).show()
                binding.swipeRefresh.isRefreshing = false

            }
        }
        lifecycleScope.launch {
            viewModel.apiSuccess.collect {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    // setting listener for recyclerview
    private fun itemListener() {
        itemClickListener = object : SetItemClickListener {
            override fun onItemClick(joke: Joke) {
                showJokeDialog(joke)
            }
        }
    }

    // joke detail dialog
    fun showJokeDialog(joke: Joke) {
        val dialog = Dialog(requireContext());
        val dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root);

        if (joke.joke != null) {
            dialogBinding.JokeSetup.text = getString(R.string.strJoke)
            dialogBinding.txtJokeSetup.text = joke.joke
            dialogBinding.txtJokeDelivery.visibility = View.INVISIBLE
            dialogBinding.delivery.visibility = View.INVISIBLE

        } else {
            dialogBinding.txtJokeSetup.text = joke.setup
            dialogBinding.txtJokeDelivery.text = joke.delivery
        }

        dialog.show()

    }

    // setting up swipe refresh layout
    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.apiCall()
        }

    }

}