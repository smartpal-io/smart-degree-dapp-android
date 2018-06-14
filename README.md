# Smart Degree

Smart degree aims to provide a decentralized solution to manage degree. The main idea is for a school, or any entity managing diplomas, to store diplomas validation proof on a blockchain system. Smart Degree currently uses Ethereum as backing blockchain. Once a degree is committed on the blockchain, anyone is able to verify if a degree has been validated by the entity. For example, an employer can verify qualification of a candidate during a job interview.

## Getting Started

Smart Degree Dapp Android repository contains :

* android application to interact with the smart degree contract
* see https://github.com/SmartContractCompanion/smart-degree

### Prerequisites

Android SDK installed

## Running android test application

This android application call a ethereum smart contract to verify degree's hash 

![ANDROID_APP](img/Screenshot_android_app_1.jpg) ![ANDROID_APP_2](img/Screenshot_android_app_2.jpg)

![ANDROID_APP](img/Screenshot_android_app_3.jpg) ![ANDROID_APP_2](img/Screenshot_android_app_4.jpg)

```
web3j truffle generate [LOCATION]/smart-degree/build/contracts/SmartDegree.json -o [LOCATION]/smart-degree-dapp-andrpod/app/src/main/java/ -p com.degree.application.contract
gradle assemble -PETH_NETWORK_URL="http://[IP]:[PORT]/"
```

You can scan a QR Code like this to fill in the form

![QRCODE](img/qr_code.jpg)

## Authors

Adbelhamid Bakhta

Karim Taam

Ludovic Maréchal

## License

