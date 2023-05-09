package com.github.polomarcus.utils

import com.github.polomarcus.model.News
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.{Dataset, SaveMode}

object PostgresService {
  val logger = Logger(PostgresService.getClass)
  // Save it to Postgres into the table called "news"
  val tablename = "news"
  // These information help you to connect to postgres
  val user = "user"
  val password = "password"
  val dbname = "metabase"
  val dbHost = sys.env.getOrElse("postgres", "localhost")
  val url = s"jdbc:postgresql://$dbHost:5432/$dbname"
  val dbServer = s"$dbHost:5432/$dbname"

  def save(dataset: Dataset[News]) = {
    logger.info(
      s"""
         |Saving news json inside Postgres database with this config
         |server: $dbServer
         |user : $user
         |password : $password
         |""".stripMargin)

    dataset.write.
      format("jdbc")
      .option("url", url)
      .option("dbtable", tablename)
      .option("user", user)
      .option("password", password)
      .save()

    logger.info("Saved news inside PG database")

  }
}
