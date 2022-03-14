package com.sparkTutorial.sparkSql;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.max;

public class StackOverFlowSurvey {

    private static final String AGE_MIDPOINT = "age_midpoint";
    private static final String SALARY_MIDPOINT = "salary_midpoint";
    private static final String SALARY_MIDPOINT_BUCKET = "salary_midpoint_bucket";

    public static void main(String[] args) throws Exception {

        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkSession session = SparkSession.builder().appName("StackOverFlowSurvey").master("local[1]").getOrCreate();

        DataFrameReader dataFrameReader = session.read();

        Dataset<Row> responses = dataFrameReader.option("header","true").csv("in/2016-stack-overflow-survey-responses.csv");

        System.out.println("=== Print out schema ===");
        responses.printSchema();




        session.stop();
    }
}
