package com.seven.assignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seven.assignment.R
import com.seven.assignment.data.Status
import com.seven.assignment.data.models.Movie
import com.seven.assignment.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : DaggerFragment(), MovieClickListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var viewModel: MainFragmentViewModel
    private val notificationAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, providerFactory).get(MainFragmentViewModel::class.java)
        list.adapter = notificationAdapter
        subscribeUI()
    }


    private fun subscribeUI() {
        viewModel.notifications.observe(viewLifecycleOwner, Observer {
            notificationAdapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    Log.d(TAG, "loading")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "success")
                }
                Status.FAILED -> {
                    Log.d(TAG, "failed")
                }
                Status.SUCCESS_EMPTY -> {
                    Log.d(TAG, "success empty")
                }
                Status.FIRST_TIME_RUNNING -> {
                    Log.d(TAG, "first time loading")
                }
            }
        })
        viewModel.getMovies()
    }

    override fun onClick(movie: Movie) {
        // TODO("Not yet implemented")
    }

    companion object {
        const val TAG = "MainFragment"
    }
}