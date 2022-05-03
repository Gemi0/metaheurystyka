package algorithms.arrayTabu.stopConditions;

public class IterationStopCondition implements StopCondition{

    public final long iterations;

    public IterationStopCondition(long iterations) {
        this.iterations = iterations;
    }

    @Override
    public boolean shouldStop(long iterationCount, long runtime) {
        return iterationCount >= iterations;
    }
}
