# DronesTransportation

## Introduction
##### Welcome to the Drone Transportation Service, a REST API that allows clients to communicate with a fleet of drones (max of 10 drones). Drones are equipped for delivering small loads of medications to locations with difficult access. This service facilitates the registration of drones, loading them with medications, checking loaded medications, and monitoring drone status.


## Task Description
### Drone Specifications

#### A drone is defined by the following attributes:

* Serial number (100 characters max)
* Model (Lightweight, Middleweight, Cruiserweight, Heavyweight)
* Weight limit (500g max)
* Battery capacity (percentage)
* State (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)


#### A medication is defined by the following attributes:
* Name (allowed only letters, numbers, '-', '_')
* Code (allowed only upper case letters, underscore, and numbers)
* Weight
* Image (picture of the medication case)

### Drone endpoints
https://documenter.getpostman.com/view/23485544/2s9Ykn8hJu

### Medication endpoints
https://documenter.getpostman.com/view/23485544/2s9Ykn8hJw

###### note: these endpoints will not run because the service is not hosted on a server, it's just an example of the structure of requests body
