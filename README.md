# udash-circe

[![Latest version](https://index.scala-lang.org/leonardehrenfried/udash-circe/udash-circe/latest.svg)](https://index.scala-lang.org/leonardehrenfried/udash-circe/udash-circe)

A Udash `RESTFramework` that uses Circe instead of upickle.

## Installation

```
libraryDependencies += "io.leonard" %%% "udash-circe" % "0.0.1"
```

## Usage

```scala
val restServer = io.leonard.udash.CirceServerREST[Api](
  dom.window.location.hostname,
  port,
  "/"
)
```

## Udash versions

- For Udash 0.5.0 use version 0.0.4
- For Udash 0.6.0 use version 0.0.7
