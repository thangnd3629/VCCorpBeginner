package com.sparkTutorial.rdd.airports;

import org.apache.ivy.util.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import py4j.StringUtil;

public class AirportsInUsaProblem {

    public static void main(String[] args) throws Exception {

        /* Create a Spark program to read the airport data from in/airports.text, find all the airports which are located in United States
           and output the airport's name and the city's name to out/airports_in_usa.text.

           Each row of the input file contains the following columns:
           Airport ID, Name of airport, Main city served by airport, Country where airport is located, IATA/FAA code,
           ICAO Code, Latitude, Longitude, Altitude, Timezone, DST, Timezone in Olson format

           Sample output:
           "Putnam County Airport", "Greencastle"
           "Dowagiac Municipal Airport", "Dowagiac"
           ...
         */

        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("AirportCity").setMaster("local[3]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("in/airports.text");
        System.out.println(System.getProperty("usr.dir"));
        JavaRDD<String> usFilter = lines.filter(line -> line.split(",")[3].equals("\"United States\""));
        JavaRDD<String> sol = usFilter.map(usAirport ->{
            String[] fields = usAirport.split(",");
            return fields[1]+ " : " + fields[2];
        });
        sol.saveAsTextFile("out/airport.txt");


    }
}
