/**
 * CommandLineOptions.java
 */
package edu.tufts.cs.analysis.results.drivers;

import java.io.File;

import edu.tufts.cs.exception.CommandLineArgumentException;


/**
 * A class containing the various command-line options for the composite-match
 * program.
 *
 * These options can also be set in a properties.xml file.
 */
public class InterpolateResultsArguments {
  /*
   * Usage statements for command-line use.
   */
  /** The default input file. */
  public static final String DEFAULT_INPUT_FILE =
      "src/test/resources/copd_combined.results.txt";
  /** The default output file. */
  public static final String DEFAULT_OUT_FILE = "interpolated.out";
  /** The argument name for the input data. */
  public static final String ARG_INPUT_FILE = "input_file";
  /** The argument name for the output file. */
  public static final String ARG_OUT_FILE = "output_file";
  /** The usage message for the truth data. */
  public static final String USAGE_TRAIN_FILE = "The name of the input file.";
  /** The usage message for the output file. */
  public static final String USAGE_OUT_FILE = "The name for the output file.";
  /** The usage message. */
  protected static String usage = "interpolate " + ARG_INPUT_FILE + " " +
    ARG_OUT_FILE + "\n\n" + ARG_INPUT_FILE +
    ":\t " + USAGE_TRAIN_FILE + "\n" + ARG_OUT_FILE + ":\t" + USAGE_OUT_FILE;

  /*
   * Argument definitions for command line use.
   */
  /** The input training file location. */
  private File inputFile;
  /** The output file location. */
  private File outFile;

  /**
   * Options from the command line arguments override default settings
   * defined in this class.
   */
  public InterpolateResultsArguments( String[] args )
    throws CommandLineArgumentException {
    if ( args.length >= 1 && args[0].toUpperCase().contains( "USAGE" ) ) {
      printUsage( "Usage:" );
    }

    String input = DEFAULT_INPUT_FILE;
    String output = DEFAULT_OUT_FILE;

    // if provided args, replace defaults
    if ( args.length >= 1 ) {
      input = args[0];

      if ( args.length >= 2 ) {
        output = args[1];
      }
    }


    inputFile = new File( input );
    outFile = new File( output );

    if ( !inputFile.exists() || !inputFile.isFile() ) {
      printUsage( CommandLineArgumentException.FILE_NOT_EXIST, input );
    }
  }

  /**
   * Print the usage and the error message.
   * @param args
   * @throws CommandLineArgumentException
   */
  public void printUsage( String... args )
    throws CommandLineArgumentException {
    System.err.println( usage );

    if ( args.length > 0 && args[0].toUpperCase().contains( "USAGE" ) ) {
      System.exit( 0 );
    }
    if ( args.length == 1 ) {
      throw new CommandLineArgumentException( args[0] );
    } else {
      throw new CommandLineArgumentException( args[0], args[1] );
    }
  }

  /**
   * Get the filename for the input data.
   *
   * @return
   */
  public File getInputFile() {
    return this.inputFile;
  }

  /**
   * Get the output filename.
   *
   * @return
   */
  public File getOutputFile() {
    return this.outFile;
  }
}
