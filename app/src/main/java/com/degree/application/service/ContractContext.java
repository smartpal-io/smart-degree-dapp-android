package com.degree.application.service;

import android.net.Uri;
import android.util.Log;

public final class ContractContext {

    private static final String SCANNER_DREGREE_ID_INDEX = "registrationNumber";
    private static final String SCANNER_STUDENT_FIRST_NAME_INDEX = "studentFirstname";
    private static final String SCANNER_STUDENT_SURNAME_INDEX = "studentSurname";
    private static final String SCANNER_BIRTHDAY_INDEX = "studentBirthDate";
    private static final String SCANNER_GRADUATION_DATE_ID_INDEX = "graduationDate";
    private static final String SCANNER_DREGREE_LABEL_INDEX = "degreeLabel";
    private static final String SCANNER_DREGREE_ADDR_INDEX = "address";

    private static String degreeId, studentName, birthday, graduationDate, degreeLabel, contractAddr;

    private static String ip;

    private static boolean verificationStatus;

    public static void buildFromUri(String uriString){
        Uri uri = Uri.parse(uriString);
        Log.d("test","->"+uriString);
        degreeId = uri.getQueryParameter(SCANNER_DREGREE_ID_INDEX);
        studentName = String.format("%s %s", uri.getQueryParameter(SCANNER_STUDENT_FIRST_NAME_INDEX),
                uri.getQueryParameter(SCANNER_STUDENT_SURNAME_INDEX));
        birthday = uri.getQueryParameter(SCANNER_BIRTHDAY_INDEX);
        graduationDate = uri.getQueryParameter(SCANNER_GRADUATION_DATE_ID_INDEX);
        degreeLabel = uri.getQueryParameter(SCANNER_DREGREE_LABEL_INDEX);
        contractAddr = uri.getQueryParameter(SCANNER_DREGREE_ADDR_INDEX);
    }

    public static String getContractAddr() {
        return contractAddr;
    }

    public static String getEndpoint() {
        return ip;
    }

    public static void setIp(String ip) {
        ContractContext.ip = ip;
    }

    public static boolean isVerificationStatus() {
        return verificationStatus;
    }

    public static String getDegreeId() {
        return degreeId;
    }

    public static String getStudentName() {
        return studentName;
    }

    public static String getBirthday() {
        return birthday;
    }

    public static String getGraduationDate() {
        return graduationDate;
    }

    public static String getDegreeLabel() {
        return degreeLabel;
    }

    public static void setVerificationStatus(boolean status) {
        verificationStatus = status;
    }

}
