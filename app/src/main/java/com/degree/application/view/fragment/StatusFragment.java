package com.degree.application.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.degree.application.R;
import com.degree.application.view.MainActivity;

import io.rmiri.buttonloading.ButtonLoading;

import static com.degree.application.service.ContractContext.isVerificationStatus;


public class StatusFragment extends Fragment {


    private TextView scanTitle;
    private ButtonLoading back;

    public StatusFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StatusFragment newInstance() {
        return new StatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        scanTitle = rootView.findViewById(R.id.section_label);

        back = rootView.findViewById(R.id.back);
        back.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                nextPage();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(isVerificationStatus()){
                scanTitle.setText(R.string.degree_ok);
            }else{
                scanTitle.setText(R.string.degree_nok);
            }
        }
    }



    private void nextPage() {
        ((MainActivity) getActivity()).firstItem();
    }
}
