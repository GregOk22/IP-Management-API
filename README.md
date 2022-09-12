# IP Management API
An IP Management API created with Spring Boot, Spring Data JPA, and PostgreSQL!

This is a simple REST API supporting a few functionalities for an IP Address class containing two pieces of data (an address, and a status).


Data Object

IPAddress = {"address": (String), "status": (String)}


Endpoints

There are four Available Endpoints to work with:
1. localhost:8080/api/v1/GetAllAddresses
 - A GET request that returns a list of all IP Addresses in the database

2. localhost:8080/api/v1/CreateCIDRBlock
 - A POST request that expects a parameter of 'fullAddress', which accepts an ip address with CIDR (eg. 10.0.0.0/24)
 - The incoming address does some simple validation using regular expressions to verify that the address is correctly formatted
 - For simplicity, this application currently only supports CIDR block /24 - /30
 - Incoming CIDR blocks containing an address already in the database will not be created
 - After recieving a valid CIDR block, every IPv4 address within the block (including the network & broadcast address) are crated as IP Address objects and inserted into the database with status values of "available"

3. localhost:8080/api/v1/AcquireAddresses
 - A PUT request that expects a parameter of 'address', which accepts a standard IPv4 addresss (eg. 10.0.0.1)
 - The incoming address is verified to exist in the database, then has it's status updated to "acquired"

4. localhost:8080/api/v1/ReleaseAddresses
 - A PUT request that expects a parameter of 'address', which accepts a standard IPv4 addresss (eg. 10.0.0.1)
 - The incoming address is verified to exist in the database, then has it's status updated to "available"

 
Architecture
 
The application is broken down into 3 primary layers
 - API Layer (containing the API endpoint mappings)
 - Service Layer (containing the business logic)
 - Data Layer (Spring Data JPA + Repository communicating with PostgreSQL)
 
 
Initial Load
 
 - The application utilizes a PostgreSQL database titled 'ipmanager'
 - The database contains one table titled 'ipaddress' which resets after each run (for simplicity)
 - The table preloads 6 IP Addresses into the database upon load for quick viewing of data (10.0.0.0 - 10.0.0.5)
