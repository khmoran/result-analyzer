package edu.tufts.cs.analysis.results.drivers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.tufts.cs.analys.results.ResultInterpolator;
import edu.tufts.cs.analysis.results.Record;
import edu.tufts.cs.analysis.results.reader.ResultReader;
import edu.tufts.cs.analysis.results.writer.ResultWriter;
import edu.tufts.cs.exception.CommandLineArgumentException;

public class InterpolateResults {
  /** The Logger. */
  private static final Logger LOG =  Logger.getLogger(
      InterpolateResults.class.getName() );
  /** An instance of the class. */
  protected static InterpolateResults instance;

  /**
   * Private constructor for executable class.
   */
  protected InterpolateResults() {
    // purposely not instantiable
  }

  /**
   * Print the configuration to the console.
   * @param cmd
   */
  protected static void printInfo( InterpolateResultsArguments cmd ) {
    LOG.log( Level.INFO, "Running Interpolate Results with: " +
      "\n\tInput file:\t" + cmd.getInputFile() +
      "\n\tOutput file:\t" + cmd.getOutputFile() );
  }


  /**
   * @param args
   * @throws CommandLineArgumentException
   * @throws IOException
   */
  public static void main( String[] args )
    throws CommandLineArgumentException, IOException {
    InterpolateResultsArguments cmd = new InterpolateResultsArguments( args );
    printInfo( cmd );

    /*
     * Get the result file.
     */
    File inputFile = cmd.getInputFile();
    ResultReader reader = new ResultReader();
    List<Record> records = reader.readResults( inputFile );

    /*
     * Select the features per the user's specification.
     */
    List<Record> interpolated =
        ResultInterpolator.interpolateResults( records );

    /*
     * Write the output.
     */
    File outFile = cmd.getOutputFile();
    ResultWriter writer = new ResultWriter( reader.getHeader() );
    writer.writeResults( outFile, interpolated );

    LOG.log( Level.INFO, "Complete!" );
  }
}
