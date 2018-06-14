package io.smartpal.smartdegree.view.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.smartpal.smartdegree.helpers.ContractLoader.ContractModel


class ContractViewModel : ViewModel() {

    private var contractModel: MutableLiveData<ContractModel> = MutableLiveData()

    private var callContractResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getContractModel(): MutableLiveData<ContractModel> {
        return contractModel
    }

    fun getCallContractResult(): MutableLiveData<Boolean>{
        return callContractResult
    }
}
