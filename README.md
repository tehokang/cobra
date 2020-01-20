[![Build Status](https://travis-ci.com/tehokang/cobra.svg?token=tukzKYpBaAzTzcPeGfcg&branch=master)](https://travis-ci.com/tehokang/cobra)
.
# What is cobra?
cobra is a library to support RPC request of javascript client application <br>
Orinally, we(JW.Kim, DI.Kim, TH.Kang) tried to make the name "CORBA" but it was not. <br>
It means that we just want to sarcastic. <br>

# Architecture
## - Block Diagram 
(1) BlockDiagram <br>
![Block Diagram](https://github.com/tehokang/cobra/blob/master/cobra_block_diagram_1.png)
(2) BlockDiagram <br>
![Block Diagram](https://github.com/tehokang/cobra/blob/master/cobra_block_diagram_2.png)
(3) BlockDiagram <br>
![Block Diagram](https://github.com/tehokang/cobra/blob/master/cobra_block_diagram_3.png)

## - Class Diagram 
![Class Diagram](https://github.com/tehokang/cobra/blob/master/cobra_class_diagram.png)

## - Sequence Diagram
![Sequence Diagram](https://github.com/tehokang/cobra/blob/master/cobra_sequence_diagram_new.png)
![Sequence Diagram](https://github.com/tehokang/cobra/blob/master/cobra_sequence_diagram_delete.png)
![Sequence Diagram](https://github.com/tehokang/cobra/blob/master/cobra_sequence_diagram_invoke.png)
![Sequence Diagram](https://github.com/tehokang/cobra/blob/master/cobra_sequence_diagram_registercallback.png)

## - RPC call example
![source example](https://github.com/tehokang/cobra/blob/master/cobra_source_example.png)

# How to build
At first, there are resources like below after clone.
```
.
�뵜���� CMakeLists.txt
�뵜���� LICENSE
�뵜���� README.md
�뵜���� client 
�뵒���� server 
    �뵜���� ccp (It wasn't mine, another person implemented, But I will)
    �뵒���� java (It was mine)
        �뵜���� example (example server application)
        �봻혻혻 �뵜���� CMakeLists.txt
        �봻혻혻 �뵒���� SampleMain.java
        �뵜���� libs (external library)
        �봻혻혻 �뵜���� libjson.jar
        �봻혻혻 �뵒���� webbit-all-in-one.jar
        �뵒���� src (cobra java source)
            �뵜���� CMakeLists.txt
            �뵒���� com
                �뵒���� teho
                    �뵜���� cobra
                    �봻혻혻 �뵜���� CobraObject.java
                    �봻혻혻 �뵜���� CobraObjectBroker.java
                    �봻혻혻 �뵜���� CobraObjectCollector.java
                    �봻혻혻 �뵜���� CobraServer.java
                    �봻혻혻 �뵜���� CobraServerReturn.java
                    �봻혻혻 �뵜���� CobraSession.java
                    �봻혻혻 �뵜���� CommandThread.java
                    �봻혻혻 �뵜���� EventThread.java
                    �봻혻혻 �뵜���� codec
                    �봻혻혻 �봻혻혻 �뵜���� CobraCodec.java
                    �봻혻혻 �봻혻혻 �뵜���� CobraJSONDecoder.java
                    �봻혻혻 �봻혻혻 �뵜���� CobraJSONEncoder.java
                    �봻혻혻 �봻혻혻 �뵜���� parameter
                    �봻혻혻 �봻혻혻 �뵒���� protocol
                    �봻혻혻 �뵜���� constant
                    �봻혻혻 �봻혻혻 �뵜���� COBRA_DATA_TYPE.java
                    �봻혻혻 �봻혻혻 �뵜���� COBRA_ERROR.java
                    �봻혻혻 �봻혻혻 �뵜���� COBRA_N_ENUM.java
                    �봻혻혻 �봻혻혻 �뵜���� COBRA_S_ENUM.java
                    �봻혻혻 �봻혻혻 �뵜���� MSG_ACTION_TYPE.java
                    �봻혻혻 �봻혻혻 �뵒���� MSG_KEY.java
                    �봻혻혻 �뵜���� exceptions
                    �봻혻혻 �봻혻혻 �뵒���� CobraException.java
                    �봻혻혻 �뵜���� interfaces
                    �봻혻혻 �봻혻혻 �뵜���� ICobraObjectBrokerEventListener.java
                    �봻혻혻 �봻혻혻 �뵜���� ICobraObjectEventListener.java
                    �봻혻혻 �봻혻혻 �뵜���� ICobraServerListener.java
                    �봻혻혻 �봻혻혻 �뵜���� ICobraSessionListener.java
                    �봻혻혻 �봻혻혻 �뵜���� ICommandThreadListener.java
                    �봻혻혻 �봻혻혻 �뵜���� IEventThreadListener.java
                    �봻혻혻 �봻혻혻 �뵒���� IWebServerListener.java
                    �봻혻혻 �뵜���� settings
                    �봻혻혻 �봻혻혻 �뵒���� CobraConfiguration.java
                    �봻혻혻 �뵒���� zsample
                    �봻혻혻     �뵜���� SampleMain.java
                    �봻혻혻     �뵒���� platform
                    �뵒���� util
                        �뵜���� CobraLogger.java
                        �뵜���� SlimLogger.java
                        �뵒���� WebServerWrapper.java
```

(1) Create build directory
```
#mkdir build
#cd build
```
(2) Build as debug
```
#cmake .. -Dbuild=debug 
#make
```
(3) Build as release
```
#cmake .. -Dbuild=release
#make
```
(4) Example executable and cobra library deploy in build/out
```
#cd build/out
#
```

# How to port this library
- Update (ASAP) 

# External Dependencies
libjson.jar : json codec library
webbit-all-in-one.jar : websocket and http server library

# Example
```
#mkdir build
#cd build
#make
#cd out
#
```

# TODO
- Adding Client sources
- Adding example to run
- <del> Adding class/block diagram</del> : Updated

