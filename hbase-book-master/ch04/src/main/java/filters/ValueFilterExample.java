package filters;

// cc ValueFilterExample Example using the value based filter
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import util.HBaseHelper;

public class ValueFilterExample {

  public static void main(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();

//    HBaseHelper helper = HBaseHelper.getHelper(conf);
//    helper.dropTable("testtable");
//    helper.createTable("testtable", "colfam1", "colfam2");
//    System.out.println("Adding rows to table...");
//    helper.fillTable("testtable", 1, 10, 10, "colfam1", "colfam2");

    Connection connection = ConnectionFactory.createConnection(conf);
    Table table = connection.getTable(TableName.valueOf("testtable"));
    // vv ValueFilterExample
    Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, // co ValueFilterExample-1-Filter Create filter, while specifying the comparison operator and comparator.
      new SubstringComparator(".4"));
    Filter filter1 = new ValueFilter(CompareFilter.CompareOp.EQUAL, // co ValueFilterExample-1-Filter Create filter, while specifying the comparison operator and comparator.
            new SubstringComparator(".6"));

    Scan scan = new Scan();


    FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(filter, filter1));

    scan.setFilter(list); // co ValueFilterExample-2-SetFilter Set filter for the scan.
    ResultScanner scanner = table.getScanner(scan);
    // ^^ ValueFilterExample
    System.out.println("Results of scan:");
    // vv ValueFilterExample
    for (Result result : scanner) {
      System.out.println(result);


    }
    scanner.close();

    Get get = new Get(Bytes.toBytes("row-5"));
    get.setFilter(filter); // co ValueFilterExample-4-SetFilter2 Assign same filter to Get instance.
    Result result = table.get(get);

    // ^^ ValueFilterExample
    System.out.println("Result of get: ");
    // vv ValueFilterExample
    for (Cell cell : result.rawCells()) {
      System.out.println("Cell: " + cell + ", Value: " +
        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
          cell.getValueLength()));
    }
    // ^^ ValueFilterExample
  }
}
