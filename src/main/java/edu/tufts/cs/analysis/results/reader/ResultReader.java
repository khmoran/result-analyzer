package edu.tufts.cs.analysis.results.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tufts.cs.analysis.results.Record;
import edu.tufts.cs.analysis.results.parser.TdfParser;

public class ResultReader {
  /** The document header. */
  protected List<String> header;

  /**
   * Default constructor.
   */
  public ResultReader() {

  }

  /**
   * Get a List of the Records.
   * @param f
   * @return
   * @throws IOException
   */
  public List<Record> readResults( File f ) throws IOException {
    List<List<String>> rows = TdfParser.parseTdf( f );

    if ( rows.size() >= 1 ) {
      header = rows.get( 0 );
    }
    List<Record> records = new ArrayList<Record>();
    for ( int i = 1; i < rows.size(); i++ ) { // exclude header
      Record r = new Record( rows.get( i ) );
      records.add( r );
    }

    return records;
  }

  /**
   * Get the header.
   * @return
   */
  public List<String> getHeader() {
    return header;
  }
}
