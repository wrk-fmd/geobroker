# Geobroker endpoint

## Data type `Position`

Properties:

* `timestamp`: ISO 8601 representation when the position data was acquired.
* (optional) `latitude` and `longitude`: floating point number of latitude and longitude.
* (optional) `accuracy`: Accuracy of the position in meters.
* (optional) `heading`: floating point number of heading in degree. 0° is North.
* (optional) `speed`: floating point number of speed in meter per second.

Example:

```json
{
  "latitude": 48.1925,
  "longitude": 16.3906,
  "timestamp": "2019-01-19T08:23:16.240Z",
  "accuracy": 200,
  "heading": 30.5,
  "speed": 8.1
}
```

## Data type `Point`

Properties:

* (optional) `latitude` and `longitude`: floating point number of latitude and longitude.

Example:

```json
{
  "latitude": 48.1922,
  "longitude": 16.3906
}
```

## Data type `Unit`

Properties:

* `id`: The ID of the unit.
* `name`: The display name of the unit.
* (optional) `lastPoint`: The [point] of the last status update of the unit.
* (optional) `targetPoint`: The destination [point] of the unit.
* (optional) `currentPosition`: The [position] which was last published by the unit.
* (optional) `isAvailableForDispatching`: A boolean value indicating if the unit can be dispatched to a new incident right now.

Example:

```json
{
  "id": "trp-3-id",
  "name": "TRP 3",
  "lastPoint": {
    "latitude": 47.588,
    "longitude": 12.638
  },
  "targetPoint": {
    "latitude": 47.649,
    "longitude": 12.797
  },
  "currentPosition": {
    "timestamp": "2019-01-01T14:06:19Z",
    "accuracy": 6.1,
    "heading": 227.0,
    "speed": 1.279,
    "latitude": 47.654,
    "longitude": 12.253
  },
  "isAvailableForDispatching": true
}
```

## Data type `Incident`

Properties:

* `id`: The ID of the incident.
* (optional) `type`: The type of the incident. CoCeSo publishes the stringified enum value of the incident type for this value.
* (optional) `priority`: Flag if the incident has high priority set.
* (optional) `blue`: Flag if the incident shall be handled with emergency light.
* (optional) `info`: Info text of the incident. The info text can also contain the human readable address representation.
* (optional) `location`: The [point] of the incident.
* `assignedUnits`: A map of the assigned units on the incident.

Example:

```json
{
  "id": "incident-1-id",
  "type": "Task",
  "priority": false,
  "blue": false,
  "info": "Lofer 358\n5090 Lofer\n\nTrip to supermarket",
  "location": {
    "latitude": 47.5838909,
    "longitude": 12.6938428
  },
  "assignedUnits": {
    "trp-3-id": "ZBO"
  }
}
```

## Public endpoint

### Update position data of unit

* Path: `/api/v1/public/positions/<unitId>`
* URL parameter: `token` to authenticate request
* HTTP method: `POST`

The request body is a JSON object of the type [Position].

If the client cannot get its own position, an update can be sent only with timestamp set.

Example request body:

```json
{
  "latitude": 48.19225,
  "longitude": 16.39066,
  "timestamp": "2019-01-19T08:23:16.240Z",
  "accuracy": 200,
  "heading": 30.5,
  "speed": 8.1
}
```

In case of a success (status code `200`) the server responds with the written [Position] object.
The timestamp might be altered, if the provided timestamp in the request is in the future.

### Get scope of unit

The scope represents all data which is currently visible for a unit.

* Path: `api/v1/public/scope/<unitId>`
* URL parameter: `token` to authenticate request
* HTTP method: `GET`

No properties on `GET` request.

Properties of response:

* `units`: A list of [units](#data-type-unit) which are visible for the requesting unit.
* `incidents`: A list of [incidents](#data-type-incident) which are visible for the requesting unit.

Example of a success response:

```json
{
  "units": [
    {
      "id": "trp-3-id",
      "name": "TRP 3",
      "lastPoint": {
        "latitude": 47.538,
        "longitude": 12.698
      },
      "targetPoint": {
        "latitude": 47.649,
        "longitude": 12.727
      },
      "currentPosition": {
        "timestamp": "2019-01-01T14:06:19Z",
        "accuracy": 6.1,
        "heading": 227.0,
        "speed": 1.279,
        "latitude": 47.554,
        "longitude": 12.723
      },
      "isAvailableForDispatching": true
    }
  ],
  "incidents": [
    {
      "id": "incident-1-id",
      "type": "Task",
      "priority": false,
      "blue": false,
      "info": "Lofer 358\n5090 Lofer\n\nTrip to supermarket",
      "location": {
        "latitude": 47.5838909,
        "longitude": 12.6938428
      },
      "assignedUnits": {
        "trp-3-id": "ZBO"
      }
    }
  ]
}
```

[Position]: #data-type-position
[position]: #data-type-position
[point]: #data-type-point