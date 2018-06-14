package io.smartpal.smartdegree.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.smartpal.smartdegree.R
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatEditText
import android.widget.Toast
import io.smartpal.smartdegree.contract.SmartDegreeService
import io.smartpal.smartdegree.contract.SmartDegreeModel
import io.smartpal.smartdegree.contract.SmartDegreeListener
import io.smartpal.smartdegree.helpers.ContractLoader
import io.smartpal.smartdegree.helpers.LoadingDialog.displayLoadingDialog
import io.smartpal.smartdegree.helpers.LoadingDialog.hideLoadingDialog
import io.smartpal.smartdegree.view.activity.MainActivity
import io.smartpal.smartdegree.view.model.ContractViewModel


class CheckFragment : Fragment() {


    private lateinit var viewModel: ContractViewModel

    private lateinit var degreeId: AppCompatEditText
    private lateinit var studentName: AppCompatEditText
    private lateinit var birthday: AppCompatEditText
    private lateinit var graduationDate: AppCompatEditText
    private lateinit var degreeLabel: AppCompatEditText
    private lateinit var contractAddress: AppCompatEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_check, container, false)
        setupViewModel()
        setupForm(rootView)
        return rootView
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(ContractViewModel::class.java)
    }

    private fun setupForm(view: View) {
        with(view) {
            degreeId = findViewById(R.id.degreeId)
            studentName = findViewById(R.id.studentName)
            birthday = findViewById(R.id.birthday)
            graduationDate = findViewById(R.id.graduationDate)
            degreeLabel = findViewById(R.id.degreeLabel)
            contractAddress = findViewById(R.id.contractAddress)
        }

        viewModel.getContractModel().observe(this, Observer { model: ContractLoader.ContractModel? ->
            var modelSmartDegree = model as SmartDegreeModel
            degreeId.setText(modelSmartDegree.degreeId)
            studentName.setText(modelSmartDegree.studentName)
            birthday.setText(modelSmartDegree.birthday)
            graduationDate.setText(modelSmartDegree.graduationDate)
            degreeLabel.setText(modelSmartDegree.degreeLabel)
            contractAddress.setText(modelSmartDegree.contractAddress)
        })

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
                it.visibility = View.VISIBLE
                it.display
                it.setOnClickListener {
                    context?.let {
                        displayLoadingDialog(it)
                        callContract(it)
                    }
                }
            }
        }
    }

    private fun callContract(context: Context) {
        var model = SmartDegreeModel(
                degreeId.text.toString(),
                studentName.text.toString(),
                birthday.text.toString(),
                graduationDate.text.toString(),
                degreeLabel.text.toString(),
                contractAddress.text.toString()
        )

        SmartDegreeService.verify(context,
                model, object : SmartDegreeListener {
            override fun onResponseReceived(isValidDegree: Boolean) {
                viewModel.getCallContractResult().postValue(isValidDegree)
                nextPage()
            }

            override fun onError(message: String?) {
                if (message != null) {
                    showError(message)
                }
            }
        })
    }

    private fun nextPage() {
        activity?.runOnUiThread {
            hideLoadingDialog()
            (activity as MainActivity).nextItem()
        }
    }

    private fun showError(message: String) {
        activity?.runOnUiThread {
            hideLoadingDialog()
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(): CheckFragment {
            return CheckFragment()
        }
    }

}
