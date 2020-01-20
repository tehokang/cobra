# Specify the minimum version for CMake
cmake_minimum_required(VERSION 2.8)

find_package(Java)
include(UseJava)

# Project's name
set(project_name cobra_java_server_example)
project(${project_name} Java)

# Set the output folder where your program will be created
message("CMAKE_SOURCE_DIR : " ${CMAKE_SOURCE_DIR})

# The following folder will be included
set(classpath_ ${CMAKE_SOURCE_DIR}/server/java/src)
set(rootdir ${CMAKE_SOURCE_DIR}/server/java/src/com/humaxdigital)
set(external_libdir ${CMAKE_SOURCE_DIR}/server/java/libs)

set(sources
	${rootdir}/cobra/zsample/SampleMain.java
)

set(CMAKE_JAVA_TARGET_OUTPUT_DIR ${CMAKE_BINARY_OUT})
add_jar(${project_name} 
	SOURCES ${sources} 
	INCLUDE_JARS ${CMAKE_BINARY_DIR}/cobra_java_server.jar ${external_libdir}/libjson.jar ${external_libdir}/webbit-all-in-one.jar
	VERSION 1.0
	OUTPUT_DIR ${CMAKE_BINARY_DIR}
)

#get_property(TARGET ${project_name}
#	PROPERTY CLASS_DIR ${rootdir}
#)	



