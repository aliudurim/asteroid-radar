package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.network.ResponseHandler
import com.udacity.asteroidradar.persistence.AsteroidRadarDatabase

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        MainAdapter(MainItemListener { item ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    item
                )
            )
        })
    }

    private val responseHandler by lazy {
        ResponseHandler()
    }

    private val mainRepository by lazy {
        MainRepository(
            responseHandler,
            AsteroidRadarDatabase.getInstance(requireContext())
        )
    }

    private val viewModel: MainViewModel by viewModels {
        val application = requireNotNull(this.activity).application
        MainViewModel.Factory(application, mainRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        _binding?.viewModel = viewModel
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.rcvAsteroid.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) { data ->
            if (data != null)
                Glide.with(this).load(data.url).into(binding.imgOfTheDay)
        }
        viewModel.showLoader.observe(viewLifecycleOwner) {
            binding.srlAsteroid.isRefreshing = it
        }
        binding.srlAsteroid.setOnRefreshListener {
            viewModel.getAsteroidsAndPictureOfTheDay()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}