package io.smartpal.smartdegree.view.fragment

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.smartpal.smartdegree.R
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatButton
import io.smartpal.smartdegree.contract.SmartDegreeModel.Companion.fromUri
import io.smartpal.smartdegree.view.activity.MainActivity
import io.smartpal.smartdegree.view.model.ContractViewModel
import com.google.zxing.client.android.CaptureActivity


class ScanFragment : Fragment() {


    private lateinit var viewModel: ContractViewModel

    private lateinit var scanQrCode: AppCompatButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_scan, container, false)
        setupViewModel()
        setupContent(rootView)
        return rootView
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(ContractViewModel::class.java)
    }

    private fun setupContent(view: View) {
        scanQrCode = view.findViewById(R.id.start_scan)
        scanQrCode.setOnClickListener {
            startScan()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
                it.visibility = View.INVISIBLE
            }
        }
    }

    private fun startScan() {
        val intent = Intent(context, CaptureActivity::class.java)
        intent.action = "com.google.zxing.client.android.SCAN"
        intent.putExtra("SAVE_HISTORY", false)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = data!!.getStringExtra("SCAN_RESULT")
                if (contents != null && !contents.isEmpty()) {
                    viewModel.getContractModel().value = fromUri(contents)
                    nextPage()
                }
            }
        }
    }

    private fun nextPage() {
        activity?.runOnUiThread {
            (activity as MainActivity).nextItem()
        }
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(): ScanFragment {
            return ScanFragment()
        }
    }

}
