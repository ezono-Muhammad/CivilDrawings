package com.example.civildrawings.ui.fragments.sites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.civildrawings.R

class SitesFragment : Fragment() {

    companion object {
        fun newInstance() = SitesFragment()
    }

    private lateinit var viewModel: SitesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SitesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}