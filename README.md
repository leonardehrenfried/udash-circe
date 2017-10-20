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

## Limitations

At the moment this implementation is pretty clumsy and has to copy
a few classes from the Udash code in order for it to work.

I'm working on solving this: [https://github.com/UdashFramework/udash-core/issues/124](https://github.com/UdashFramework/udash-core/issues/124)
