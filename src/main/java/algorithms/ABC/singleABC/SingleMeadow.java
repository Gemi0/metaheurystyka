package algorithms.ABC.singleABC;

import java.util.ArrayList;

public class SingleMeadow {

    private final ArrayList<SingleFlower> flowers = new ArrayList<>();
    private SingleFlower bestFlower = null;

    public SingleMeadow(){}

    public ArrayList<SingleFlower> getFlowers() {
        return flowers;
    }

    public SingleFlower getBestFlower() {
        return bestFlower;
    }

    public void setBestFlower(SingleFlower bestFlower){
        this.bestFlower = bestFlower;
    }
}
