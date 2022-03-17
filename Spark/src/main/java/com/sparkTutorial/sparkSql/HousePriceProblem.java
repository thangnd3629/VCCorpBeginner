package com.sparkTutorial.sparkSql;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.functions.col;

public class HousePriceProblem {

    private static final String PRICE = "Price";
    private static final String PRICE_SQ_FT = "Price SQ Ft";
    private static final String MLS = "MLS";
    public static void main(String[] args) throws Exception {

        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkSession session = SparkSession.builder().appName("HousePriceSolution").master("local[1]").getOrCreate();

        Dataset<Row> realEstate = session.read().option("header", "true").csv("in/RealEstate.csv");
        realEstate = realEstate.withColumn(MLS, col(MLS).cast("int"));

        realEstate.collectAsList().forEach((Row row) ->{
            Integer x =row.getInt(0);
            System.out.println(x);
        });

    }
}
