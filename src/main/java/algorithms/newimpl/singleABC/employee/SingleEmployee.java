package algorithms.newimpl.singleABC.employee;

import algorithms.newimpl.singleABC.SingleFlower;
import algorithms.newimpl.singleABC.SingleMeadow;
import main.TSPData;

public abstract class SingleEmployee {

    protected final SingleMeadow singleMeadow;
    protected final TSPData data;

    public SingleEmployee(SingleMeadow singleMeadow, TSPData data) {
        this.singleMeadow = singleMeadow;
        this.data = data;
    }

    public void sendBees() {
        for(SingleFlower flower : singleMeadow.getFlowers()) {
            processFlower(flower);
        }
    }

    public abstract void processFlower(SingleFlower flower);
}
