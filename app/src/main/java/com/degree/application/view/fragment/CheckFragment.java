package com.degree.application.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.degree.application.R;
import com.degree.application.service.ContractContext;
import com.degree.application.service.ContractValidatorManager;
import com.degree.application.service.ContractValidatorManager.ValidatorListener;
import com.degree.application.view.MainActivity;

import io.rmiri.buttonloading.ButtonLoading;

import static android.widget.Toast.LENGTH_LONG;
import static com.degree.application.utils.LoadingDialogUtils.displayLoadingDialog;
import static com.degree.application.utils.LoadingDialogUtils.hideLoadingDialog;


public class CheckFragment extends Fragment implements ValidatorListener {

    private ContractValidatorManager contractValidatorManager;

    private EditText verifyDegreeId, studentName,
            birthday, graduationDate, degreeLabel;
    private String contractAddress;

    private ButtonLoading verify;

    public CheckFragment() {
        contractValidatorManager = ContractValidatorManager.getInstance();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CheckFragment newInstance() {
        return new CheckFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check, container, false);

        verifyDegreeId = rootView.findViewById(R.id.degreeId);
        studentName = rootView.findViewById(R.id.studentName);
        birthday = rootView.findViewById(R.id.birthday);
        graduationDate = rootView.findViewById(R.id.graduationDate);
        degreeLabel = rootView.findViewById(R.id.degreeLabel);

        verify = rootView.findViewById(R.id.verify);
        verify.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                displayLoadingDialog(getActivity());
                contractValidatorManager.verify(
                        CheckFragment.this,
                        verifyDegreeId.getText().toString(),
                        studentName.getText().toString(),
                        birthday.getText().toString(),
                        graduationDate.getText().toString(),
                        degreeLabel.getText().toString()
                );
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
            verifyDegreeId.setText(ContractContext.getDegreeId());
            studentName.setText(ContractContext.getStudentName());
            birthday.setText(ContractContext.getBirthday());
            graduationDate.setText(ContractContext.getGraduationDate());
            degreeLabel.setText(ContractContext.getDegreeLabel());

        }
    }

    @Override
    public void onDegreeValid() {
        nextPage(getString(R.string.button_ok));
    }

    @Override
    public void onDegreeInvalid() {
        nextPage(getString(R.string.degree_nok));
    }

    @Override
    public void onError(String message) {
        getActivity().runOnUiThread(() -> {
            verify.setProgress(false);
            hideLoadingDialog();
            displayMessage("Error during validation " + message);
        });
    }

    private void displayMessage(String message) {
        Toast.makeText(getContext(), message, LENGTH_LONG).show();
    }

    private void nextPage(String message) {
        getActivity().runOnUiThread(() -> {
            hideLoadingDialog();
            ((MainActivity) getActivity()).nextCurrentItem();
            displayMessage(message);
            verify.setProgress(false);
        });
    }
}