package io.smartpal.smartdegree.contract

interface SmartDegreeListener {

    fun onResponseReceived(isValidDegree : Boolean)

    fun onError(message: String?)
}