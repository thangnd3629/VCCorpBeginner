package com.sparkTutorial.rdd.nasaApacheWebLogs;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class SameHostsProblem {

    public static void main(String[] args) throws Exception {

        /* "in/nasa_19950701.tsv" file contains 10000 log lines from one of NASA's apache server for July 1st, 1995.
           "in/nasa_19950801.tsv" file contains 10000 log lines for August 1st, 1995
           Create a Spark program to generate a new RDD which contains the hosts which are accessed on BOTH days.
           Save the resulting RDD to "out/nasa_logs_same_hosts.csv" file.

           Example output:
           vagrant.vf.mmc.com
           www-a1.proxy.aol.com
           .....

           Keep in mind, that the original log files contains the following header lines.
           host	logname	time	method	url	response	bytes

           Make sure the head lines are removed in the resulting RDD.
         */
        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("wordCounts").setMaster("local[3]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> dayOne = sc.textFile("in/nasa_19950701.tsv");
        JavaRDD<String> dayTwo = sc.textFile("in/nasa_19950801.tsv");
        filterHeader(dayOne);
        filterHeader(dayTwo);
        JavaRDD<String> julyFirstHosts = dayOne.map(line -> line.split("\t")[0]);

        JavaRDD<String> augustFirstHosts = dayTwo.map(line -> line.split("\t")[0]);
        julyFirstHosts.intersection(augustFirstHosts).saveAsTextFile("out/intersect.txt");


    }
    public static void filterHeader(JavaRDD<String> data){
        data.filter(line -> !line.split("\t")[0].equals("host"));
    }
}
