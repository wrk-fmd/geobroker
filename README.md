# GeoBroker

Public endpoint for CoCeSo to collect, cache and share position data.
To share and get position data, the client need to provide its unique unit ID and an authentication token.
For security reasons the unit ID of the geobroker must not be the same ID as in CoCeSo.

The documentation of the public endpoint can be found at: [geobroker/README.md](geobroker/README.md).
The full documentation of the API can be found in the [Swagger UI YAML file](geobroker/src/main/resources/static/api/v1/geobroker.yaml).

## Build

* Run `mvn clean package` to build a tomcat package

## Installation

* Install the built `geobroker.war` on a Tomcat webserver.

## Run in IDE

* Activate maven profile `local-tomcat`.
* Run `GeoBrokerBootstrapper` to start a stand-alone tomcat instance.
