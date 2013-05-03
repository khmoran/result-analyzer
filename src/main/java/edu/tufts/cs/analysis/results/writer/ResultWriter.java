package edu.tufts.cs.analysis.results.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.tufts.cs.analysis.results.Record;
import edu.tufts.cs.analysis.results.parser.TdfParser;

public class ResultWriter {
  /** The header. */
  protected List<String> header;

  /**
   * Default constructor.
   */
  public ResultWriter( List<String> header ) {
    this.header = header;
  }

  /**
   * Write the results to a File.
   * @param f
   * @param results
   * @throws IOException
   */
  public final void writeResults( File f, List<Record> results )
    throws IOException {
    FileWriter fw = new FileWriter( f );
    BufferedWriter writer = new BufferedWriter( fw );
    StringBuffer output = new StringBuffer();

    for ( String h : this.header ) {
      output.append( h + "\t" );
    }

    output.append( "\n" );
    output.append( TdfParser.formatTdf( results ) );
    writer.write( output.toString() );

    writer.close();
    fw.close();
  }

  /**
   * Set the header.
   * @param h
   */
  public final void setHeader( List<String> header ) {
    this.header = header;
  }
}
