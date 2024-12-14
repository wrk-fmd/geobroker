# How to build and run geobroker using docker

This is a guide to waive the installation of dependencies and using docker instead.

Inspect the scripts in this directory for the actual shell commands.

For a pre-built docker image you can also run:
`docker run -it --rm -p 127.0.0.1:8080:8080 wrkfmdit/geobroker` for the latest build on Docker Hub.

## Requirements

* Server
  * Docker engine
* Client
  * Web Browser

## Build the project locally using a maven container

These steps build the deployable `.war` file and a docker image.

* `./build_war` builds the deployable; optional parameters are
	* `./build_war_cached` (alternative command) keeps the cache after building for faster re-builds.
* `./build_app` builds `geobroker-app`.

## Deployment

Run either of the following commands.
The `geobroker` is then available at http://localhost:8080 (locally only).

* `docker run -d --rm -p 127.0.0.1:8080:8080 --name geobroker-app geobroker-app`
* `./run_target`
	* runs directly after `./build_war`

Note that keeping a stopped container is not feasible - nothing is persisted in any way.
