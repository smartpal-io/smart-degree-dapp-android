package io.smartpal.smartdegree.contract

import android.net.Uri
import io.smartpal.smartdegree.helpers.ContractLoader.ContractModel


class SmartDegreeModel : ContractModel {

    var degreeId: String = ""
    var studentName: String = ""
    var birthday: String = ""
    var graduationDate: String = ""
    var degreeLabel: String = ""
    var contractAddress: String = ""

    constructor()

    constructor(degreeId: String, studentName: String, birthday: String, graduationDate: String,
                degreeLabel: String, contractAddress: String) {
        this.degreeId = degreeId
        this.studentName = studentName
        this.birthday = birthday
        this.graduationDate = graduationDate
        this.degreeLabel = degreeLabel
        this.contractAddress = contractAddress

    }

    companion object {
        fun fromUri(uriString: String): ContractModel {
            val uri = Uri.parse(uriString)
            return SmartDegreeModel(
                    degreeId = uri.getQueryParameter(SCANNER_DEGREE_ID_INDEX),
                    studentName = String.format("%s %s", uri.getQueryParameter(SCANNER_STUDENT_FIRST_NAME_INDEX),
                            uri.getQueryParameter(SCANNER_STUDENT_SURNAME_INDEX)),
                    birthday = uri.getQueryParameter(SCANNER_BIRTHDAY_INDEX),
                    graduationDate = uri.getQueryParameter(SCANNER_GRADUATION_DATE_ID_INDEX),
                    degreeLabel = uri.getQueryParameter(SCANNER_DEGREE_LABEL_INDEX),
                    contractAddress = uri.getQueryParameter(SCANNER_DEGREE_ADDR_INDEX)
            )
        }

        private const val SCANNER_DEGREE_ID_INDEX = "registrationNumber"
        private const val SCANNER_DEGREE_ADDR_INDEX = "address"
        private const val SCANNER_STUDENT_FIRST_NAME_INDEX = "studentFirstname"
        private const val SCANNER_STUDENT_SURNAME_INDEX = "studentSurname"
        private const val SCANNER_BIRTHDAY_INDEX = "studentBirthDate"
        private const val SCANNER_GRADUATION_DATE_ID_INDEX = "graduationDate"
        private const val SCANNER_DEGREE_LABEL_INDEX = "degreeLabel"
    }

}