package io.smartpal.smartdegree.contract

import android.content.Context
import io.smartpal.smartdegree.helpers.ContractLoader
import io.smartpal.smartdegree.helpers.ContractLoader.ContractLoadListener
import org.web3j.protocol.Web3j
import org.web3j.tx.Contract
import org.web3j.tx.ManagedTransaction
import org.web3j.tx.ReadonlyTransactionManager

object SmartDegreeService : ContractLoader(), ContractLoadListener {

    private var listener: SmartDegreeListener? = null

    fun verify(context: Context, model: ContractModel, listener: SmartDegreeListener) {
        this.listener = listener
        super.call(context, model, this)
    }

    override fun loadContract(web3j: Web3j, model: ContractModel): Contract {
        var smartDegreeContractParams = model as SmartDegreeModel
        return SmartDegree.load(
                smartDegreeContractParams.contractAddress,
                web3j,
                ReadonlyTransactionManager(web3j, ""),
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT)
    }

    override fun onContractLoaded(contract: Contract, model: ContractModel) {
        var smartDegreeContract = contract as SmartDegree
        var smartDegreeModel = model as SmartDegreeModel
        val verifyDegreeId = buildHash(smartDegreeModel.degreeId)
        val verifyHash = buildHash(
                smartDegreeModel.degreeId,
                smartDegreeModel.studentName,
                smartDegreeModel.birthday,
                smartDegreeModel.graduationDate,
                smartDegreeModel.degreeLabel

        )
        val result = smartDegreeContract.verify(verifyDegreeId, verifyHash).send()
        listener?.onResponseReceived(
                result
        )

    }


    override fun onError(message: String?) {
        listener?.onError(message)
    }
}