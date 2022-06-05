package algorithms.newimpl.singleABC.scout;

import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

public abstract class SingleScout {

    protected final SingleMeadow singleMeadow;
    protected final TSPData data;
    protected final int maxRetries;

    public SingleScout(SingleMeadow singleMeadow, TSPData data, int maxRetries) {
        this.singleMeadow = singleMeadow;
        this.data = data;
        this.maxRetries = maxRetries;
    }

    public void sendBees() {
        for(SingleFlower flower : singleMeadow.getFlowers()) {
            processFlower(flower);
        }
    }

    public abstract void processFlower(SingleFlower flower);
}
