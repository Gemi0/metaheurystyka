package algorithms.arrayTabu.neighborhoodBrowser.stopCondition;

public interface StopCondition {

    boolean checkStop(long time, long iterations);
}
