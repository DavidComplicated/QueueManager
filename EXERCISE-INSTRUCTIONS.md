# Aircraft Queue Manager Challenge

## Challenge description
The goal of this exercise is to demonstrate programming ability by developing a simple software subsystem. 
Work should be done in Java. 

A software subsystem of an air-traffic control system is defined to manage a queue of aircraft (AC) in an airport.  
The aircraft queue is managed by a process that responds to three types of requests: 
 - System boot used to start the system.
 - Enqueue aircraft used to insert a new AC into the system. 
 - Dequeue aircraft used to remove an AC from the system.

AC’s have at least (but are not limited to having) the following properties: 
 - AC type:  Passenger or Cargo
 - AC size:  Small or Large

The process that manages the queue of AC’s satisfies the following: 
 - There is no limit to the number of AC’s it can manage.
 - Dequeue aircraft requests result in selection of one AC for removal such that:
    - Passenger AC’s have removal precedence over Cargo AC’s.
    - Large AC’s of a given type have removal precedence over Small AC’s of the same type.
    - Earlier enqueued AC’s of a given type and size have precedence over later enqueued AC’s of the same type and size.

System Implementation Requirements
 - Develop one or more data structures to hold the state of an individual AC. 
 - Develop one or more data structures to hold the state of the AC queue. 
 - Define data structures and/or constants needed to represent requests.
 - Develop the code for a REST endpoint called aqmRequestProcess() which accepts any of the three defined requests and follows the above guidelines to implement an aircraft queue manager.  
 - To the greatest extent possible, show all of your code.  Feel free to use standard libraries.

## Running Spring Boot

This project should be able to run with a version of Maven at or greater than `3.6.1`. Use the command `mvn spring-boot:run` to start Spring Boot.
