package com.knoldus.services

import com.knoldus.model.Car
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.json4s.{DefaultFormats, _}
import org.json4s.native.JsonMethods

import java.util.Properties

/**
 * KafkaService is a class that create kafka consumer.
 */
class KafkaService {

  /**
   * Creating environment for kafka that consume stream message from kafka topic.
   *
   * @param environment Flink Stream Execution Environment.
   * @return DataStream of type [[Car]].
   */
  def kafkaStreamConsumer(environment: StreamExecutionEnvironment): DataStream[Car] = {
    import org.apache.flink.streaming.api.scala._
    implicit lazy val formats: DefaultFormats.type = org.json4s.DefaultFormats
    //Open Kafka connection and Streaming car data through topic.
    val properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092");
    properties.setProperty("group.id", "testKafka");
    val kafkaConsumer = new FlinkKafkaConsumer[String]("car.create", new SimpleStringSchema(), properties)
    environment.addSource(kafkaConsumer).flatMap(raw => JsonMethods.parse(raw).toOption)
      .map(_.extract[Car])
  }

}
