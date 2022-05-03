package algorithms.arrayTabu.stopCondition;

public class TimeStop implements StopCondition {

    private long maxTime;

    public TimeStop(long maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public boolean checkStop(long startTime, long iterations) {
        return System.nanoTime() >= maxTime + startTime;
    }
}
