package algorithms.ABC.multiABC;

import java.util.ArrayList;

public class MultiMeadow {

    private final ArrayList<MultiFlower> flowers = new ArrayList<>();
    private MultiFlower bestFlower = null;

    public MultiMeadow(){}

    public ArrayList<MultiFlower> getFlowers() {
        return flowers;
    }

    public MultiFlower getBestFlower() {
        return bestFlower;
    }

    public void setBestFlower(MultiFlower bestFlower){
        this.bestFlower = bestFlower;
    }
}
