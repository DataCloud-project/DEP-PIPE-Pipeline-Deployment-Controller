<p align="center"><img width=50% src="https://raw.githubusercontent.com/DataCloud-project/toolbox/master/docs/img/datacloud_logo.png"></p>&nbsp;

[![GitHub Issues](https://img.shields.io/github/issues/DataCloud-project/DEP-PIPE-translator.svg)](https://github.com/DataCloud-project/DEP-PIPE-translator/issues)
[![License](https://img.shields.io/badge/license-Apache2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# DEP-PIPE Translator

This sections describes the DEP-PIPE-translator as also its functionalities.

[//]: # (Translator of the descriptor provided by ADA-PIPE in order to be deployed through DEP-PIPE)

DEP-PIPE-translator aids the communication between the ADA-PIPE and DEP-PIPE DATACLOUD components.
This achieved by exposing a handful of Rest APIs that are consumed by the aforementioned components. 

The main functionalities are:

- A POST API that the ADA-PIPE can utilize to validate its descriptor before sending it to the DEP-PIPE.
- A POST API that is used to parse ADA-PIPE descriptor and translate to the one that DEP-PIPE can consume.
- A Kafka consumer that can accept ADA-PIPE descriptors and translate to the one that DEP-PIPE can consume.

Of course everything regarding the communication between the DEP-PIPE-translator will be authenticated and authorized 
using the platform's authentication and authorization mechanism.

The APIs are prune to changes until the finalization of the project, as also to support future possible functionalities.
