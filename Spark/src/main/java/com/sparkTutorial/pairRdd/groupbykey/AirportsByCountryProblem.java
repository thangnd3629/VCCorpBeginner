package com.sparkTutorial.pairRdd.groupbykey;

import com.sparkTutorial.rdd.commons.Utils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;
import scala.Tuple2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AirportsByCountryProblem {

    public static void main(String[] args) throws Exception {

        /* Create a Spark program to read the airport data from in/airports.text,
           output the the list of the names of the airports located in each country.

           Each row of the input file contains the following columns:
           Airport ID, Name of airport, Main city served by airport, Country where airport is located, IATA/FAA code,
           ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format

           Sample output:

           "Canada", ["Bagotville", "Montreal", "Coronation", ...]
           "Norway" : ["Vigra", "Andenes", "Alta", "Bomoen", "Bronnoy",..]
           "Papua New Guinea",  ["Goroka", "Madang", ...]
           ...
         */
        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("airports").setMaster("local[1]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("in/airports.text");

        JavaPairRDD<String, String> countryAndAirportNameAndPair =
                lines.mapToPair( airport -> new Tuple2<>(airport.split(Utils.COMMA_DELIMITER)[3],
                        airport.split(Utils.COMMA_DELIMITER)[1]));
        countryAndAirportNameAndPair.groupByKey().sortByKey(
                new KeyComp()

        ).saveAsTextFile("out/cannotserialized");

    }
    public static class KeyComp implements Comparator<String>, Serializable
    {
        @Override
        public int compare(String s, String t1) {
            return s.toLowerCase().charAt(1)- t1.toLowerCase().charAt(1);
        }
    }

}
