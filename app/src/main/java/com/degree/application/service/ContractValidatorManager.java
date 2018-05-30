package com.degree.application.service;

import android.util.Log;

import com.degree.application.BuildConfig;
import com.degree.application.contract.SmartDegree;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.ReadonlyTransactionManager;

import static com.degree.application.service.ContractContext.getContractAddr;
import static com.degree.application.service.ContractContext.setVerificationStatus;
import static org.web3j.crypto.Hash.sha3;

public class ContractValidatorManager {

    private static final ContractValidatorManager instance = new ContractValidatorManager();

    public static ContractValidatorManager getInstance() {
        return instance;
    }

    private ContractValidatorManager() {

    }

    /**
     * Call SmartDegree contract in order to verify the validity of a degree
     */
    public void verify(ValidatorListener listener, String degreeId, String studentName,
                       String birthday, String graduationDate, String degreeLabel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Web3j web3 = Web3jFactory.build(new HttpService(getEndpoint()));
                web3.web3ClientVersion().observable().subscribe(x -> {
                    try {

                        SmartDegree contract = SmartDegree.load(
                                getContractAddr(),
                                web3,
                                new ReadonlyTransactionManager(web3, ""),
                                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

                        byte[] verifyDegreeId = buildHash(degreeId);
                        byte[] verifyHash = buildHash(
                                degreeId, studentName, birthday, graduationDate, degreeLabel
                        );

                        Boolean result = contract.verify(
                                verifyDegreeId,
                                verifyHash
                        ).send();

                        setVerificationStatus(result);

                        if (result) {
                            listener.onDegreeValid();
                        } else {
                            listener.onDegreeInvalid();
                        }

                    } catch (Exception e) {
                        Log.e(getClass().getName(), e.getMessage(), e);
                        listener.onError(e.getMessage());
                    }
                });
            }
        }).start();
    }

    private String getEndpoint(){
        return (ContractContext.getEndpoint()==null)
                ?BuildConfig.ETH_NETWORK_URL
                : ContractContext.getEndpoint();
    }


    /**
     * This method will build a hash in order to be store in the network
     *
     * @param params
     * @return sha3
     */
    private byte[] buildHash(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String param : params) {
            stringBuilder.append(param.replaceAll(" ", ""));
        }
        Log.d("test","hash "+stringBuilder.toString());
        return sha3(stringBuilder.toString().getBytes());
    }

    public interface ValidatorListener {

        void onDegreeValid();

        void onDegreeInvalid();

        void onError(String message);
    }
}
