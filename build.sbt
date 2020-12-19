name := "Integrate-flink-streaming-with-kafka-and-cassandra-using-scala"

version := "0.1"

scalaVersion := "2.12.2"

mainClass in compile := Some("com.knoldus.FlinkKafkaToCassandra")

libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-connector-kafka" % "1.9.0",


  "org.apache.flink" %% "flink-streaming-scala" % "1.9.0" ,
   "org.json4s" %% "json4s-native" % "3.6.10",

  // cassandra
  "org.apache.flink" %% "flink-connector-cassandra" % "1.9.0"
)

