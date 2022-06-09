package algorithms.ABC.multiABC.scout;

import algorithms.ABC.multiABC.MultiFlower;
import algorithms.ABC.multiABC.MultiMeadow;
import main.TSPData;

public abstract class MultiScout {

    protected final MultiMeadow multiMeadow;
    protected final TSPData data;
    protected final int maxRetries;

    public MultiScout(MultiMeadow multiMeadow, TSPData data, int maxRetries) {
        this.multiMeadow = multiMeadow;
        this.data = data;
        this.maxRetries = maxRetries;
    }

    public void sendBees() {
        multiMeadow.getFlowers().parallelStream().forEach(this::processFlower);
    }

    public abstract void processFlower(MultiFlower flower);
}
