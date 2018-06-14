package io.smartpal.smartdegree.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.smartpal.smartdegree.R
import io.smartpal.smartdegree.view.activity.MainActivity
import io.smartpal.smartdegree.view.model.ContractViewModel


class StatusFragment : Fragment() {

    private lateinit var viewModel: ContractViewModel

    private lateinit var scanTitle: AppCompatTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)
        setupViewModel()
        setupContent(view)
        return view
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(ContractViewModel::class.java)
    }

    private fun setupContent(view: View){
        scanTitle = view.findViewById(R.id.section_label)

        (view.findViewById(R.id.back) as AppCompatButton).setOnClickListener{
            (activity as MainActivity).firstItem()
        }

        viewModel.getCallContractResult().observe(this, Observer { status: Boolean? ->
            if (status!!) {
                scanTitle.setText(R.string.degree_ok)
            } else {
                scanTitle.setText(R.string.degree_nok)
            }
        })

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
                it.visibility = View.INVISIBLE
            }
        }
    }

    companion object {

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(): StatusFragment {
            return StatusFragment()
        }
    }
}
