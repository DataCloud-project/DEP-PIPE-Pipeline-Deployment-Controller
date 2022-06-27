<p align="center"><img width=50% src="https://raw.githubusercontent.com/DataCloud-project/DEP-PIPE-translator/main/img/DEPPIPE_Logo_TransparentBackground_White.png"></p>&nbsp;

[![GitHub Issues](https://img.shields.io/github/issues/DataCloud-project/DEP-PIPE-translator.svg)](https://github.com/DataCloud-project/DEP-PIPE-translator/issues)
[![License](https://img.shields.io/badge/license-Apache2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# DEP-PIPE Translator

This sections describes the DEP-PIPE-translator as also its functionalities.

[//]: # (Translator of the descriptor provided by ADA-PIPE in order to be deployed through DEP-PIPE)

DEP-PIPE-translator aids the communication between the ADA-PIPE and DEP-PIPE DATACLOUD components.
This achieved by exposing a handful of Rest APIs that are consumed by the aforementioned components. 

## Functionalities :

- A POST API that the ADA-PIPE can utilize to validate its descriptor before sending it to the DEP-PIPE.
- A POST API that is used to parse ADA-PIPE descriptor and translate to the one that DEP-PIPE can consume.
- A DELETE call that allows the removal of a deployed pipeline step
- A Kafka consumer that can accept ADA-PIPE descriptors and translate to the one that DEP-PIPE can consume.

> List of the APIS can be found in the api folder.

> The APIs are prune to changes until the finalization of the project, as also to support future possible functionalities.




## Setup and Usage
This module will be able to be used by a)downloading the dedicated container image, or b) by following the provided instructions in order to build the software from code.

> DEP-PIPE translator is **currently part of the DEP-PIPE core module and not a standalone module**. However by the **first release of DEP-PIPE** (October 2022) this code provided in this repository will be able to be used as a **standalone service**.




> For the final release of this module, the communication between the DEP-PIPE-translator and any other Datacloud components will be based on Keycloak .


