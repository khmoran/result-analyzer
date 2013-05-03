package edu.tufts.cs.analysis.results.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tufts.cs.analysis.results.TdfFormattable;


public class TdfParser {
  /**
   * Parse a tab-delimited file.
   * @param filename
   * @return
   * @throws IOException
   */
  public static final List<List<String>> parseTdf( File file )
    throws IOException {

    FileReader fr = new FileReader( file );
    BufferedReader br = new BufferedReader( fr );

    List<List<String>> rows = new ArrayList<List<String>>();

    String line = null;
    while ( ( line = br.readLine() ) != null ) {
      String[] cols = line.split( "\t" );

      List<String> row = new ArrayList<String>();

      row = Arrays.asList( cols );

      rows.add( row );
    }

    br.close();
    fr.close();

    return rows;
  }

  /**
   * Format the rows as a TDF String.
   * @param rows
   */
  public static final <E extends TdfFormattable> String formatTdf(
      List<E> rows ) {
    StringBuilder sb = new StringBuilder();

    for ( TdfFormattable row : rows ) {
      sb.append( row.toStringTDF() + "\n" );
    }

    return sb.toString();
  }

  /**
   * Private constructor for utility class.
   */
  private TdfParser() {
    // purposely not instantiable
  }
}
