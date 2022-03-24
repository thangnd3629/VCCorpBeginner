package filters;

// cc PageFilterExample Example using a filter to paginate through rows
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import util.HBaseHelper;

public class PageFilterExample {

  // vv PageFilterExample
  private static final byte[] POSTFIX = new byte[] { 0x00 };
  // ^^ PageFilterExample

  public static void main(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();

//    HBaseHelper helper = HBaseHelper.getHelper(conf);
//    helper.dropTable("testtable");
//    helper.createTable("testtable", "colfam1");
//    System.out.println("Adding rows to table...");
//    helper.fillTable("testtable", 1, 1000, 10, "colfam1");

    Connection connection = ConnectionFactory.createConnection(conf);
    Table table = connection.getTable(TableName.valueOf("testtable"));

    // vv PageFilterExample
    Filter filter = new PageFilter(15);

    byte[] startRow = new byte[]{};
    while (true)
    {
      Scan scan = new Scan();
      scan.setFilter(filter);
      startRow = Bytes.add(startRow, new byte[]{0x00});
      scan.setStartRow(startRow);
      ResultScanner results = table.getScanner(scan);
      int count = 0;
      for (Result result : results){
        System.out.println(Bytes.toString(result.getRow()));
        startRow = result.getRow();
      }
      count++;
      if (count == 0){
        break;
      }



    }




//    int totalRows = 0;
//    byte[] lastRow = null;
//    while (true) {
//      Scan scan = new Scan();
//      scan.setFilter(filter);
//      if (lastRow != null) {
//        System.out.println("Current : " +Bytes.toString(lastRow));
//        System.out.println("---DEBUG");
//        for (byte b : lastRow) {
//          System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
//        }
//        byte[] startRow = Bytes.add(lastRow, POSTFIX);//prevent current row duplication
//        System.out.println("---DEBUG");
//        for (byte b : startRow) {
//          System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
//        }
//        System.out.println("start row: " +
//          Bytes.toStringBinary(startRow));
//        scan.setStartRow(startRow);
//      }
//      ResultScanner scanner = table.getScanner(scan);
//      int localRows = 0;
//      Result result;
//      while ((result = scanner.next()) != null) {
//        System.out.println(localRows++ + ": " + result);
//        totalRows++;
//        lastRow = result.getRow();
//
//      }
//      scanner.close();
//      if (localRows == 0) break;
//    }

    // ^^ PageFilterExample
  }
}
