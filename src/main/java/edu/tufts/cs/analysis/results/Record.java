package edu.tufts.cs.analysis.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Record implements TdfFormattable, Comparable<Integer> {
  /** The dataset. */
  protected String dataset;
  /** The run number. */
  protected int run;
  /** The number of unique labels observed during this iteration. */
  protected int numLabels;
  /** The remaining columns. */
  protected List<Double> remainingCols;

  /**
   * Empty constructor.
   */
  protected Record() {

  }

  /**
   * Default constructor.
   * @param dataset
   * @param run
   * @param numLabels
   * @param remainingCols
   */
  public Record( String dataset, int run, int numLabels,
      List<Double> remainingCols ) {
    super();
    this.dataset = dataset;
    this.run = run;
    this.numLabels = numLabels;
    this.remainingCols = remainingCols;
  }

  /**
   * Parse constructor.
   * @param row
   */
  public Record( List<String> row ) {
    parse( row );
  }

  /**
   * Parse the record.
   * @param row
   * @return
   */
  public void parse( List<String> row ) {
    dataset = row.get( 0 );
    run = Integer.valueOf( row.get( 1 ) );
    numLabels = Integer.valueOf( row.get( 2 ) );
    remainingCols = new ArrayList<Double>();

    for ( int i = 3; i < row.size(); i++ ) {
      remainingCols.add( Double.parseDouble( row.get( i ) ) );
    }
  }

  /**
   * Get the dataset.
   * @return
   */
  public String getDataset() {
    return this.dataset;
  }

   /**
    * Get the run number.
    * @return
    */
  public int getRun() {
    return this.run;
  }

  /**
   * Get the number of labels observed for this iteration.
   * @return
   */
  public int getNumLabels() {
    return this.numLabels;
  }

  /**
   * Get the remaining (unnamed) columns.
   * @return
   */
  public List<Double> getRemainingCols() {
    return this.remainingCols;
  }

  /**
   * Set the remaining columns.
   * @param remainingCols
   */
  public void setRemainingCols( List<Double> remainingCols ) {
    this.remainingCols = remainingCols;
  }

  @Override
  public String toString() {
    return this.toStringTDF();
  }

  /**
   * Format the Record as a tab-delineated String.
   */
  public String toStringTDF() {
    StringBuilder sb = new StringBuilder();

    sb.append( this.dataset + "\t" );
    sb.append( this.run + "\t" );
    sb.append( this.numLabels + "\t" );

    for ( Double col : remainingCols ) {
      sb.append( col + "\t" );
    }

    return sb.toString();
  }

  /**
   * Average the records.
   * @param records
   * @return
   */
  public static Record average( Collection<Record> records ) {
    Record record = new Record();

    // first total the columns
    List<Double> totaledCols = new ArrayList<Double>();
    for ( Record r : records ) {
      List<Double> theseCols = r.getRemainingCols();
      for ( int i = 0; i < theseCols.size(); i++ ) {
        if ( i < totaledCols.size() ) {
          double prevTotal = totaledCols.get( i );
          totaledCols.set( i, prevTotal + theseCols.get( i ) );
        } else {
          totaledCols.add( theseCols.get( i ) );
        }
      }
    }

    // then divide them by the number of records
    for ( int i = 0; i < totaledCols.size(); i++ ) {
      double prevTotal = totaledCols.get( i );
      totaledCols.set( i, prevTotal / (double) records.size() );
    }

    record.setRemainingCols( totaledCols );

    return record;
  }

  /**
   * Set the run.
   * @param run
   */
  public void setRun( int run ) {
    this.run = run;
  }

  /**
   * Set the dataset.
   * @param dataset
   */
  public void setDataset( String dataset ) {
    this.dataset = dataset;
  }

  /**
   * Set the number of labels.
   * @param numLabels
   */
  public void setNumLabels( int numLabels ) {
    this.numLabels = numLabels;
  }

  /**
   * Compare this Record to an Integer.
   */
  public int compareTo( Integer o ) {
    return new Integer( numLabels ).compareTo( o );
  }
}
