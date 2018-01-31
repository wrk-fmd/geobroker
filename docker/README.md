# How to build and run geobroker using docker

This is a guide to waive the installation of dependencies and using docker instead.

Inspect the scripts in this directory for the actual shell commands. Later,
let's use docker compose.


## Requirements

* Server
  * Docker engine
* Client
  * Web Browser

## Build the project using a maven container

These steps build the deployable .war-file and a docker images.

* `./build_war` builds the deployable; optional parameters are
	* `./build_war_cached` (alternative command) keeps the cache after building for faster re-builds.
	* `clean` removes artefacts before compiling and packaging.
* `./build_app` builds `geobroker-app`.

## Deployment

Run either of the following commands. Geobroker is then available at http://localhost:8080 (locally only).

* docker run -d --rm -p 127.0.0.1:8080:8080 --name geobroker-app geobroker-app
* `./run_target`
	* runs directly after `./build_war`

Note that keeping a stopped container is not feasible - nothing is persisted anyway.
