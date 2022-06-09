package algorithms.ABC.singleABC.onlooker;

import algorithms.ABC.singleABC.SingleFlower;
import algorithms.ABC.singleABC.SingleMeadow;
import algorithms.ABC.singleABC.employee.SingleEmployee;
import main.TSPData;

import java.util.ArrayList;

public abstract class SingleOnlooker {

    protected final SingleMeadow singleMeadow;
    protected final SingleEmployee singleEmployee;
    protected final TSPData data;

    public SingleOnlooker(SingleMeadow singleMeadow, TSPData data, SingleEmployee singleEmployee) {
        this.singleMeadow = singleMeadow;
        this.singleEmployee = singleEmployee;
        this.data = data;
    }

    public void sendBees() {
        ArrayList<SingleFlower> rouletteResults = new ArrayList<>();
        for(int i = 0; i < singleMeadow.getFlowers().size(); i++) {
            rouletteResults.add(roulette());
        }

        //TODO: Take care of overlapping
        /*
        HashMap<MultiFlower, Integer> map = new HashMap<>();
        for(MultiFlower flower : rouletteResults) {
            Integer integer = map.get(flower);
            map.put(flower, integer == null ? 1 : integer + 1);
        }
        */

        for(SingleFlower flower : rouletteResults) {
            singleEmployee.processFlower(flower);
        }
    }

    public abstract SingleFlower roulette();
}
