# Java Server Timing (JETI)
[![Build Status](https://travis-ci.org/chkal/jeti.svg?branch=master)](https://travis-ci.org/chkal/jeti)

JETI is an experimental Java framework which can be used to expose performance metrics using the
[Server Timing](https://www.w3.org/TR/server-timing/) W3C Working Draft. Browsers can use these 
metrics to display server-side timing data together with other performance details. JETI provides a generic framework which allows
to be integrated with various frameworks and technologies.

![Screenshot](https://i.imgur.com/fpJTA3f.png)

NOTE: JETI is currently in a proof-of-concept stage. So use it with caution.

## Installation

Setting up JETI is straight forward. No configuration is needed. You just have to
add a few dependencies to your project to get started.

### Step 1: Maven repository

There is no official release of JETI yet. So you will have to add
[Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/) 
to your `pom.xml` like this:

```xml
<repositories>
  <repository>
    <id>sonatype-oss-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

### Step 2: Added a JETI engine

The JETI engine is the component which sends the required HTTP response headers to the user agent.
There are currently two engines available: The Servlet 3.x and the Servlet 4.x engine.

Both engines will automatically register the required Servlet filter in your application. 
So you don't have to do anything else beside adding the dependency.

#### Servlet 3.x engine

Most users should use this engine, because Servlet 4.0 isn't widely adopted yet.
To use this engine, add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>de.chkal.jeti</groupId>
  <artifactId>jeti-engine-servlet3</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Please note that the Servlet 3.x engine will first write all the data sent to the response output
stream to a buffer. After the request has completely been processed, JETI will emit the timing HTTP
header and flush the buffer to the client. This is required because HTTP response headers must
be sent BEFORE the response body is sent. The buffering will have some small performance impact,
but this approach will allow JETI to collect metrics for the complete request lifecycle.

sends the required HTTP response headers just before the
Servlet response is committed. Therefore, you won't be able to see any metrics which are collected
after the first byte has been written to the response.

#### Servlet 4.x engine

If your container already supports Servlet 4.0, you should give the Servlet 4.x engine a try. 
To use this engine, add the following dependency:

```xml
<dependency>
  <groupId>de.chkal.jeti</groupId>
  <artifactId>jeti-engine-servlet4</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Instead of buffering the HTTP response body, this engine uses the 
HTTP trailer API available in Servlet 4.0. Trailers are basically headers which are sent AFTER the 
response body. Because they are sent after the body, you will be also able to expose metrics generated
while data is sent to the client.

Unfortunately the support for HTTP trailers is quite poor in Servlet containers and browsers. Some
containers for example support trailers only for HTTP 2.0.

### Step 3: Add plugins

JETI provides plugins which integrate with different frameworks to collect metrics about the request
processing life cycle. Currently only two plugins exist, but I hope to add more soon. To use a plugin, 
just add the corresponding dependency to your `pom.xml`:

```xml
<!-- Plugin for JAX-RS -->
<dependency>
  <groupId>de.chkal.jeti</groupId>
  <artifactId>jeti-plugin-jaxrs</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<!-- Plugin for JSF -->
<dependency>
  <groupId>de.chkal.jeti</groupId>
  <artifactId>jeti-plugin-jsf</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

### Step 4: View metrics in your browser

That's all. Now you should be able to view metrics in your browser. Google Chrome added support for the 
current draft of the Server Timing spec in version 65.

To view the JETI metrics, send a request to your app which is handled by one of the supported 
frameworks (like JAX-RS). Now select the corresponding request in the "Network" tab of your Chrome
Development tools. The "Timing" tab should show data like this:

![Screenshot](https://i.imgur.com/fpJTA3f.png)

Enjoy!
