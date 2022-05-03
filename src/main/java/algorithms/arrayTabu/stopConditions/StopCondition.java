package algorithms.arrayTabu.stopConditions;

public interface StopCondition {
    boolean shouldStop(long iterationCount, long runtime);
}
