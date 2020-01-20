# Specify the minimum version for CMake
cmake_minimum_required(VERSION 2.8)

find_package(Java)
include(UseJava)

# Project's name
set(project_name cobra_java_server)
project(${project_name} Java)

# Set the output folder where your program will be created
message("CMAKE_SOURCE_DIR : " ${CMAKE_SOURCE_DIR})

# The following folder will be included
set(classpath_ ${CMAKE_SOURCE_DIR}/server/java/src)
set(rootdir ${CMAKE_SOURCE_DIR}/server/java/src/com/humaxdigital)
set(external_libdir ${CMAKE_SOURCE_DIR}/server/java/libs)

set(sources
	${rootdir}/cobra/CobraObject.java
	${rootdir}/cobra/CobraObjectBroker.java
	${rootdir}/cobra/CobraObjectCollector.java
	${rootdir}/cobra/CobraServer.java
	${rootdir}/cobra/CobraServerReturn.java
	${rootdir}/cobra/CobraSession.java
	${rootdir}/cobra/CommandThread.java
	${rootdir}/cobra/EventThread.java
	${rootdir}/cobra/codec/CobraCodec.java
	${rootdir}/cobra/codec/CobraJSONDecoder.java
	${rootdir}/cobra/codec/CobraJSONEncoder.java
	${rootdir}/cobra/codec/parameter/CobraCmdParameter.java
	${rootdir}/cobra/codec/parameter/CobraEventParameter.java
	${rootdir}/cobra/codec/parameter/CobraParameter.java
	${rootdir}/cobra/codec/parameter/CobraReturn.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdDelete.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdInvoke.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdNew.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdRegCallback.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdStart.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdStop.java
	${rootdir}/cobra/codec/protocol/request/CobraCmdUnregCallback.java
	${rootdir}/cobra/codec/protocol/request/CobraCommand.java
	${rootdir}/cobra/codec/protocol/request/CobraRequest.java
	${rootdir}/cobra/codec/protocol/response/CobraCmdResult.java
	${rootdir}/cobra/codec/protocol/response/CobraEvent.java
	${rootdir}/cobra/codec/protocol/response/CobraResponse.java
	${rootdir}/cobra/constant/COBRA_DATA_TYPE.java
	${rootdir}/cobra/constant/COBRA_ERROR.java
	${rootdir}/cobra/constant/COBRA_N_ENUM.java
	${rootdir}/cobra/constant/COBRA_S_ENUM.java
	${rootdir}/cobra/constant/MSG_ACTION_TYPE.java
	${rootdir}/cobra/constant/MSG_KEY.java
	${rootdir}/cobra/exceptions/CobraException.java
	${rootdir}/cobra/interfaces/ICobraObjectBrokerEventListener.java
	${rootdir}/cobra/interfaces/ICobraObjectEventListener.java
	${rootdir}/cobra/interfaces/ICobraServerListener.java
	${rootdir}/cobra/interfaces/ICobraSessionListener.java
	${rootdir}/cobra/interfaces/ICommandThreadListener.java
	${rootdir}/cobra/interfaces/IEventThreadListener.java
	${rootdir}/cobra/interfaces/IWebServerListener.java
	${rootdir}/cobra/settings/CobraConfiguration.java
	${rootdir}/cobra/zsample/SampleMain.java
	${rootdir}/cobra/zsample/platform/Address.java
	${rootdir}/cobra/zsample/platform/Contact.java
	${rootdir}/cobra/zsample/platform/ContactBook.java
	${rootdir}/cobra/zsample/platform/ContactCollection.java
	${rootdir}/util/CobraLogger.java
	${rootdir}/util/SlimLogger.java
	${rootdir}/util/WebServerWrapper.java
)

set(CMAKE_JAVA_TARGET_OUTPUT_DIR ${CMAKE_BINARY_OUT})
add_jar(${project_name} 
	SOURCES ${sources} 
	INCLUDE_JARS ${external_libdir}/libjson.jar ${external_libdir}/webbit-all-in-one.jar
	VERSION 1.0
	OUTPUT_DIR ${CMAKE_BINARY_DIR}
)

add_custom_command(
	TARGET ${project_name}
	POST_BUILD
	COMMAND cp -f ${external_libdir}/*.jar ${CMAKE_BINARY_DIR}
)
#get_property(TARGET ${project_name}
#	PROPERTY CLASS_DIR ${rootdir}
#)	



