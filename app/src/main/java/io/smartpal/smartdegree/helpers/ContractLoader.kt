package io.smartpal.smartdegree.helpers

import android.content.Context
import android.util.Log
import org.web3j.crypto.Hash
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract

abstract class ContractLoader {

    /**
     * call contract
     */
    fun call(context: Context, model: ContractModel, listener: ContractLoadListener) {
        Thread(Runnable {
            try {
                val web3 = Web3jFactory.build(HttpService(Prefs(context).endpoint))
                web3.web3ClientVersion().observable().subscribe({ _ ->
                    listener.onContractLoaded(loadContract(web3, model), model)
                })
            }catch (exception: Exception){
                listener.onError(exception.message)
            }
        }).start()
    }

    /**
     * This method allow to load the contract with web3j
     * @param web3j
     * @param model of the contract
     * @return contract loaded
     */
    abstract fun loadContract(web3j: Web3j, model: ContractModel): Contract

    /**
     * This method will build a hash in order to be store in the network
     *
     * @param params
     * @return sha3
     */
    protected fun buildHash(vararg params: String): ByteArray {
        val stringBuilder = StringBuilder()
        for (param in params) {
            stringBuilder.append(param.replace(" ", ""))
        }
        Log.d(javaClass.name,"HASH "+stringBuilder.toString())

        return Hash.sha3(stringBuilder.toString().toByteArray())
    }

    interface ContractLoadListener {

        /**
         * Called when the contract is loaded
         */
        fun onContractLoaded(contract: Contract, model: ContractModel)

        /**
         * Called when an error occurs
         */
        fun onError(message: String?)
    }

    /**
     * Defines contract data
     */
    interface ContractModel

    abstract fun onContractLoaded(contract: Contract, model: ContractModel)
}
