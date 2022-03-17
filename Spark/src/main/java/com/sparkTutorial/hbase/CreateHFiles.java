package com.sparkTutorial.hbase;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;



import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.percent_rank;

import org.codehaus.janino.Java;
import scala.Tuple2;
import scala.util.control.Exception;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;


public class CreateHFiles {

    public static class Estate implements Serializable {
        //        MLS,Location,Price,Bedrooms,Bathrooms,Size,Price SQ Ft,Status
        private int mls;
        private String location;
        private double price;
        private int bedrooms;
        private int bathrooms;
        private double size;
        private double priceSqFt;
        private String status;

        public int getMls() {
            return mls;
        }

        public void setMls(int mls) {
            this.mls = mls;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getBedrooms() {
            return bedrooms;
        }

        public void setBedrooms(int bedrooms) {
            this.bedrooms = bedrooms;
        }

        public int  getBathrooms() {
            return bathrooms;
        }

        public void setBathrooms(int bathrooms) {
            this.bathrooms = bathrooms;
        }

        public double getSize() {
            return size;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public double getPriceSqFt() {
            return priceSqFt;
        }

        public void setPriceSqFt(double priceSqFt) {
            this.priceSqFt = priceSqFt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Estate(int mls, String location, double price, int bedrooms, int bathrooms, double size, double priceSqFt, String status) {
            this.mls = mls;
            this.location = location;
            this.price = price;
            this.bedrooms = bedrooms;
            this.bathrooms = bathrooms;
            this.size = size;
            this.priceSqFt = priceSqFt;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Input missing");
            System.exit(1);
        }
        if (args.length < 2) {
            System.err.println("Specify output path");
            System.exit(1);
        }
        String input_path = args[0];

        String output_path = args[1];
        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("hbase").setMaster("local[1]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession session = SparkSession.builder().appName("hbase").master("local[1]").getOrCreate();
        JavaRDD<String> rawDataWithHeader = sc.textFile(input_path);
        JavaRDD<String> rawData = rawDataWithHeader.filter(line ->
                !line.contains("mls")
        );

        JavaRDD<Estate> schemaAppliedData = rawData.map(line -> {
            String[] fields = line.split(",");
            return new Estate(Integer.parseInt(fields[0]),
                    fields[1],
                    Double.parseDouble(fields[2]),
                    Integer.parseInt(fields[3]),
                    Integer.parseInt(fields[4]),
                    Double.parseDouble(fields[5]),
                    Double.parseDouble(fields[6]),
                    fields[7]


            );
        });


        Dataset<Estate> estateDataset = session.createDataset(schemaAppliedData.rdd(), Encoders.bean(Estate.class));


        String[] cols = estateDataset.columns();
        Arrays.sort(cols);
        for (String c: cols){
            System.out.println(c);
        }

        JavaRDD<KeyValue> keyvalues = schemaAppliedData.flatMap(new RowToKeyValueMapper(cols));

        JavaPairRDD<ImmutableBytesWritable, KeyValue> writables = keyvalues.mapToPair(
                (KeyValue kv) -> new Tuple2<>(
                        new ImmutableBytesWritable(kv.getRow()), kv)
        );
        writables.sortByKey(true);



        writables.saveAsNewAPIHadoopFile(
                output_path,
                ImmutableBytesWritable.class,
                KeyValue.class,
                HFileOutputFormat2.class);


    }

    public static class RowToKeyValueMapper implements org.apache.spark.api.java.function.FlatMapFunction<Estate, KeyValue> {
        String[] col;

        public RowToKeyValueMapper(String[] col) {
            this.col = col;
        }

        @Override
        public Iterator<KeyValue> call(Estate estate) {

            List<KeyValue> keyValueList = new ArrayList<>();

            KeyValue kvLocation = new KeyValue(
                            Bytes.toBytes(estate.getMls()),
                            Bytes.toBytes("location"),
                            Bytes.toBytes(""),
                            Bytes.toBytes(estate.getLocation())
                    );
            KeyValue kvPrice = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("price"),
                    Bytes.toBytes("gross"),
                    Bytes.toBytes(estate.getPrice())
            );
            KeyValue kvPriceFtSq = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("price"),
                    Bytes.toBytes("perFtSq"),
                    Bytes.toBytes(estate.getPriceSqFt())
            );
            KeyValue kvBed = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("facility"),
                    Bytes.toBytes("bedroom"),
                    Bytes.toBytes(estate.getBedrooms())
            );

            KeyValue kvBath = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("facility"),
                    Bytes.toBytes("bathroom"),
                    Bytes.toBytes(estate.getBathrooms())
            );

            KeyValue kvSize = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("size"),
                    Bytes.toBytes(""),
                    Bytes.toBytes(estate.getSize())
            );
            KeyValue kvSale = new KeyValue(
                    Bytes.toBytes(estate.getMls()),
                    Bytes.toBytes("status"),
                    Bytes.toBytes(""),
                    Bytes.toBytes(estate.getStatus())
            );
            List<KeyValue> kvl = Arrays.asList(kvBath, kvLocation, kvPrice, kvSale, kvSize);
//            kvl.sort(new KvComparator());
//
//
//
//

            keyValueList.addAll(kvl);


            return keyValueList.iterator();
        }
    }
    public static class KvComparator implements Comparator<KeyValue> {

        @Override
        public int compare(KeyValue keyValue, KeyValue t1) {
            return  keyValue.getFamilyArray()[1] - t1.getFamilyArray()[1];
        }
    }
}