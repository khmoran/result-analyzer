package edu.tufts.cs.analys.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.tufts.cs.analysis.results.Record;

public class ResultInterpolator {
  /** The default step size. */
  protected static final int DEFAULT_STEP_SIZE = 25;

  /**
   * Interpolate the results from multiple runs into an average.
   * @param multipleRuns
   * @return
   */
  public static List<Record> interpolateResults( List<Record> multipleRuns ) {
    List<Record> averagedResults = new ArrayList<Record>();

    Collection<List<Record>> bins = binByRun( multipleRuns );
    int maxBinSize = getMaxBinSize( bins );

    for ( int i = 0; i < maxBinSize; i += DEFAULT_STEP_SIZE ) {
      Set<Record> allRunsAtStep = new HashSet<Record>();
      for ( List<Record> run : bins ) {
        // get an approximation of the value for this step for this run
        allRunsAtStep.add( interpolateStep( run, i ) );
      }
      // average across all runs for this step
      Record average = Record.average( allRunsAtStep );
      average.setDataset( multipleRuns.get( 0 ).getDataset() );
      average.setRun( -1 );
      average.setNumLabels( i );
      averagedResults.add( average );
    }

    return averagedResults;
  }

  /**
   * Interpolate over the bin for this step.
   * @param bin
   * @param step
   * @return
   */
  protected static Record interpolateStep( List<Record> bin, int step ) {
    int idx = Collections.binarySearch( bin, step );

    if ( idx >= 0 ) { // the exact step size was found
      return bin.get( idx );
    } else {
      idx = Math.min( ~idx, bin.size()-1 ); // exact step not found, get closest
      if ( idx > 0 && Math.abs( bin.get( idx - 1 ).getNumLabels() - step ) <=
          Math.abs( bin.get( idx ).getNumLabels() - step ) ) {
        idx = idx-1;
      }

      Set<Record> closest = new HashSet<Record>();
      closest.add( bin.get( idx ) );
      if ( idx > 0 ) closest.add( bin.get( idx - 1 ) );
      if ( idx < bin.size() - 1 ) closest.add( bin.get( idx+1 ) );

      Record i = Record.average( closest );
      i.setDataset( bin.get( 0 ).getDataset() );
      i.setRun( bin.get( 0 ).getRun() );
      i.setNumLabels( step );
      return i;
    }
  }

  /**
   * Get the size of the largest bin.
   * @param bins
   * @return
   */
  protected static int getMaxBinSize( Collection<List<Record>> bins ) {
    int max = 0;
    for ( List<Record> bin : bins ) {
      int binMax = bin.get( bin.size() - 1 ).getNumLabels();
      if ( binMax > max ) {
        max = binMax;
      }
    }

    return max;
  }

  /**
   * Bin the records by run.
   * @param multipleRuns
   * @return
   */
  protected static Collection<List<Record>> binByRun(
      List<Record> multipleRuns ) {
    SortedMap<Integer, List<Record>> runMap =
      new TreeMap<Integer, List<Record>>();

    for ( Record r : multipleRuns ) {
      List<Record> run = runMap.get( r.getRun() );
      if ( run == null ) {
        run = new ArrayList<Record>();
        runMap.put( r.getRun(), run );
      }
      run.add( r );
    }

    return runMap.values();
  }

  /**
   * Private constructor for utility class.
   */
  private ResultInterpolator() {
    // purposely not instantiable
  }
}
