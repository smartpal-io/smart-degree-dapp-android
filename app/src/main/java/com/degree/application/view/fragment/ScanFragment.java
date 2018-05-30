package com.degree.application.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.degree.application.R;
import com.degree.application.service.ContractContext;
import com.degree.application.view.MainActivity;
import com.google.zxing.client.android.CaptureActivity;

import io.rmiri.buttonloading.ButtonLoading;

import static android.app.Activity.RESULT_OK;

public class ScanFragment extends Fragment {

    private ButtonLoading scanQrCode;

    public ScanFragment() {
    }

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        scanQrCode = rootView.findViewById(R.id.start_scan);
        scanQrCode.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                startScan();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
            }
        });
        return rootView;
    }


    private void startScan() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        intent.setAction("com.google.zxing.client.android.SCAN");
        intent.putExtra("SAVE_HISTORY", false);
        startActivityForResult(intent, 0);
        scanQrCode.setProgress(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                if (contents != null && !contents.isEmpty()) {
                    ContractContext.buildFromUri(contents);
                    ((MainActivity)getActivity()).nextCurrentItem();
                }
            }
        }
    }
}
