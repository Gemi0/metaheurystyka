package algorithms.arrayTabu.stopCondition;

public class IterationStop implements StopCondition {

    private long maxIterations;

    public IterationStop(long maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public boolean checkStop(long startTime, long iterations) {
        return iterations >= maxIterations;
    }
}
