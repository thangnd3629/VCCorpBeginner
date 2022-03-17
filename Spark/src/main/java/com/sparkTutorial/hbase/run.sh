#!/bin/zsh
cd ~/Documents/VCCorpBeginner/Spark
gradle clean jar
"$HADOOP_HOME"/bin/hdfs dfs -rm -r -f /hfile_output

#"$SPARK_HOME"/bin/spark-submit --class com.sparkTutorial.hbase.CreateHFiles ~/Documents/VCCorpBeginner/Spark/build/libs/HbaseJob.jar hdfs://localhost:54310/hbase/RealEstate.csv hdfs://localhost:54310/hfile_output



