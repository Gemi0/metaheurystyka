package algorithms.ABC.multiABC.employee;

import algorithms.ABC.multiABC.MultiMeadow;
import algorithms.ABC.multiABC.MultiFlower;
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
    public void processFlowerSynchronized(MultiFlower flower) {
        synchronized (flower){
            processFlower(flower);
        }
    }
}
