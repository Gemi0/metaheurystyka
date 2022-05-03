package algorithms.arrayTabu.stopConditions;

public class TimeStopCondition implements StopCondition {

    public final long runtime;

    public TimeStopCondition(long runtime) {
        this.runtime = runtime;
    }

    @Override
    public boolean shouldStop(long iterationCount, long runtime) {
        return runtime >= this.runtime;
    }
}
