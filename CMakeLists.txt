# Specify the minimum version for CMake
cmake_minimum_required(VERSION 2.8)

# Project's name
set(project_name cobra)
project(${project_name} Java)

set(CMAKE_VERBOSE_MAKEFILE ON)
set(CMAKE_BINARY_DIR ${CMAKE_SOURCE_DIR}/build/out/)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set(CMAKE_JAVA_TARGET_OUTPUT_DIR ${CMAKE_BINARY_DIR})

# Set the output folder where your program will be created

# The following folder will be included
#add_subdirectory(client)
add_subdirectory(server/java/src)
add_subdirectory(server/java/example)
