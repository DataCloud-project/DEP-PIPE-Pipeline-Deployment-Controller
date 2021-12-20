# DEP-PIPE-translator
This sections describes the DEP-PIPE-translator as also its functionalities.

[//]: # (Translator of the descriptor provided by ADA-PIPE in order to be deployed through DEP-PIPE)

DEP-PIPE-translator aids the communication between the ADA-PIPE and DEP-PIPE DATACLOUD components.
This achieved by exposing a handful of Rest APIs that are consumed by the aforementioned components. 

The main functionalities are:

- A POST API that the ADA-PIPE can utilize to validate its descriptor before sending it to the DEP-PIPE.
- A POST API that is used to parse ADA-PIPE descriptor and translate to the one that DEP-PIPE can consume.
- A Kafka consumer that can accept ADA-PIPE descriptors and translate to the one that DEP-PIPE can consume.

The APIs are prune to changes until the finalization of the project, as also to support future possible functionalities.