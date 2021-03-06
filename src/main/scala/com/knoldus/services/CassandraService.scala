package com.knoldus.services

import com.knoldus.model.Car

import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.connectors.cassandra.CassandraSink

/**
 * CassandraService is a class that sinks DataStream into CassandraDB.
 */
class CassandraService {

  /**
   * Creating environment for Cassandra and sink some Data of car stream into CassandraDB
   *
   * @param sinkCarStream  DataStream of type Car.
   */
  def sinkToCassandraDB(sinkCarStream: DataStream[Car]): Unit = {

    import org.apache.flink.streaming.api.scala._
    createTypeInformation[(String, Option[Long], Option[Long])]

    //Creating car data to sink into cassandraDB.
    val sinkCarDataStream = sinkCarStream.map(car => (car.Name, car.Cylinders.orNull,
                                                      car.Horsepower.orNull))
    //Open Cassandra connection and Sinking car data into cassandraDB.
    CassandraSink.addSink(sinkCarDataStream)
      .setHost("127.0.0.1")
      .setQuery("INSERT INTO example.car(Name, Cylinders, Horsepower) values (?, ?, ?);")
      .build
  }
}
