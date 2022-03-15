import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;


public class HBaseConnection {


    public final String COLUMN_FAMILY_CP = "cp";
    public final String COLUMN_FAMILY_CT = "ct";

    private Connection connection;

    /*
     * Create a new table. If it is already existed, re-create a new one.
     */

    public void createTableIfNotExists(String tableName) throws IOException {
        try (Admin admin = connection.getAdmin()) {
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
            table.addFamily(new HColumnDescriptor(COLUMN_FAMILY_CT));
            table.addFamily(new HColumnDescriptor(COLUMN_FAMILY_CP));
            if (admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println("Table [" + table.getTableName().getNameAsString()
                        + "] is already existed.");
            } else {
            System.out.print("Creating new table... ");
            admin.createTable(table);
            System.out.println("Done.");
            }
        }
    }

    public void setUp() throws IOException {
        Configuration config = HBaseConfiguration.create();
//        config.set("zookeeper.znode.parent","/hbase-unsecure");

        config.set("hbase.zookeeper.property.clientPort", "2222");
//        config.set("hbase.cluster.distributed", "true");
        connection = ConnectionFactory.createConnection(config);
    }

    public void close() throws IOException {
        if ((connection != null) && (!connection.isClosed())) connection.close();
    }


    public void write(String tableName, String[][] data) throws IOException {

        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            for (String[] datum : data) {
                Put buf = new Put(Bytes.toBytes(datum[0]));
                putIfNotNull(buf, COLUMN_FAMILY_CP, "a", datum[1]);
                putIfNotNull(buf, COLUMN_FAMILY_CP, "id", datum[2]);
                putIfNotNull(buf, COLUMN_FAMILY_CT, "a", datum[3]);
                putIfNotNull(buf, COLUMN_FAMILY_CT, "t", datum[4]);
                table.put(buf);
            }
        }
    }

    private void putIfNotNull(Put put, String CF, String qualifier,String value){
        if (value == null) return;

        put.addColumn(Bytes.toBytes(CF), Bytes.toBytes(qualifier), Bytes.toBytes(value));

    }

    public void readAll(String tableName) throws IOException {
        try (Table table = connection.getTable(TableName.valueOf(tableName));
             ResultScanner rs = table.getScanner(new Scan())) {
            for (Result ret = rs.next(); ret != null; ret = rs.next()) {
                for (Cell cell : ret.listCells()) {
                    String rkey = Bytes.toString(ret.getRow());

                    String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                    String qual = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("Row key: " + rkey +
                            ", Column Family: " + cf +
                            ", Qualifier: " + qual +
                            ", Value : " + value);
                }
            }
        }
    }

    public void read(String tableName, String row) throws IOException {
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(row));
            Result result = table.get(get);
            byte[] value = result.getValue(Bytes.toBytes(COLUMN_FAMILY_CP), Bytes.toBytes("id"));
            String convertedValue = Bytes.toString(value);
            System.out.println(convertedValue);


        }
    }
    public void doFilterOnCF(String tableName, String CF, String qualifier, String qValue, String notNullCF,String notNullQualifier) throws IOException {
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
//            Scan scan = new Scan(Bytes.toBytes("E3039-21-1494133010"));//start row
            Scan scan = new Scan();
            Filter filter = new SingleColumnValueFilter(Bytes.toBytes(CF), Bytes.toBytes(qualifier), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(qValue));
//            FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes("cr")));//lexicography
            Filter qualifierA = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("a")));

            SingleColumnValueFilter notNull = new SingleColumnValueFilter(Bytes.toBytes(notNullCF), Bytes.toBytes(notNullQualifier), CompareFilter.CompareOp.NOT_EQUAL,new NullComparator());
            notNull.setFilterIfMissing(true);//exist
            Filter col = new ColumnPrefixFilter(Bytes.toBytes("t"));

            Filter multiCol = new MultipleColumnPrefixFilter(new byte[][]{Bytes.toBytes("id"), Bytes.toBytes("a")});




            FilterList filterList = new FilterList();

            filterList.addFilter(Arrays.asList(multiCol));
            scan.setFilter(filterList);

            ResultScanner rs = table.getScanner(scan);
            for (Result ret = rs.next(); ret != null; ret = rs.next()) {
                for (Cell cell : ret.listCells()) {
                    String rkey = Bytes.toString(ret.getRow());

                    String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                    String qual = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("Row key: " + rkey +
                            ", Column Family: " + cf +
                            ", Qualifier: " + qual +
                            ", Value : " + value);

                }
            }


        }
    }

    public void delete(String tableName, String row) throws IOException {
        try (Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete get = new Delete(Bytes.toBytes(row));
            table.delete(get);



        }
    }

    public static void main(String[] args) throws IOException {
        final String TABLE_NAME = "JavaClient";
        // define sample data
        final String[][] src = {
                {"Z1234-32-1494113008", "2", "XE3025", "29", null},
                {"ASFAE-32-1494113008", "3", "XE3025", "30", "Exist"},
        };


        HBaseConnection hc = new HBaseConnection();
        hc.setUp();
//        hc.createTableIfNotExists(TABLE_NAME);
//        hc.write(TABLE_NAME, src);
//        hc.readAll(TABLE_NAME);
        hc.doFilterOnCF(TABLE_NAME, hc.COLUMN_FAMILY_CP, "id","XE3025", hc.COLUMN_FAMILY_CT, "t");
        hc.close();
    }
}