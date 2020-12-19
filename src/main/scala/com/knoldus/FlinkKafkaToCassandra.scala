package com.knoldus

import com.knoldus.model.Car
import com.knoldus.services.{CassandraService, KafkaService}
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, createTypeInformation}
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods

/**
* FlinkKafkaToCassandra is ans  that integrates Flink Streaming
* with kafka and cassandra.
*/
object FlinkKafkaToCassandra {

  def main(args: Array[String]): Unit = {

    val environment = StreamExecutionEnvironment.getExecutionEnvironment

    //Instantiating KafkaService Class and Consume message from kafka topic.
    val carStream = new KafkaService().kafkaStreamConsumer(environment)

    //Do something with carDataStream

    //Instantiating CassandraService Class and sinking data into CassandraDB.
    val cassandraService = new CassandraService()
    cassandraService.sinkToCassandraDB(carStream)

    environment.execute("Flink Kafka to cassandra app")

  }

}
