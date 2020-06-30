name := "Gcp_Scala_Test"

version := "0.1"

scalaVersion := "2.12.9"
val scioVersion = "0.8.0-beta1"
val beamVersion = "2.14.0"
val scalaMacrosVersion = "2.1.1"
val avroVersion = "1.8.2"

libraryDependencies ++= Seq(
  "com.spotify" %% "scio-core" % scioVersion,
  "com.spotify" %% "scio-bigquery" % scioVersion,
  "com.spotify" %% "scio-test" % scioVersion % "test",
  "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
  "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion,
  "org.slf4j" % "slf4j-simple" % "1.7.28",
  //"org.apache.avro" % "avro" % avroVersion exclude ("com.thoughtworks.paranamer", "paranamer"),
  "com.spotify" %% "scio-avro" % scioVersion,
  "com.google.cloud" % "google-cloud-storage" % "1.88.0"
)

addCompilerPlugin("org.scalamacros" % "paradise" % scalaMacrosVersion cross CrossVersion.full)
