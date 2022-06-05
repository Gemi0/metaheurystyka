package algorithms.newimpl.multiABC.employee;

import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.multiABC.MultiFlower;
import main.TSPData;

public abstract class MultiEmployee {

    protected final MultiMeadow multiMeadow;
    protected final TSPData data;

    public MultiEmployee(MultiMeadow multiMeadow, TSPData data) {
        this.multiMeadow = multiMeadow;
        this.data = data;
    }

    public void sendBees() {
        multiMeadow.getFlowers().parallelStream().forEach(this::processFlower);
    }

    public abstract void processFlower(MultiFlower flower);
    public abstract void processFlowerSynchronized(MultiFlower flower);
}
