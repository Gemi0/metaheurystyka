package algorithms.newimpl.multiABC.onlooker;

import algorithms.newimpl.multiABC.MultiFlower;
import algorithms.newimpl.multiABC.MultiMeadow;
import algorithms.newimpl.multiABC.employee.MultiEmployee;
import main.TSPData;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class MultiOnlooker {

    protected final MultiMeadow multiMeadow;
    protected final MultiEmployee multiEmployee;
    protected final TSPData data;

    public MultiOnlooker(MultiMeadow multiMeadow, TSPData data, MultiEmployee multiEmployee) {
        this.multiMeadow = multiMeadow;
        this.multiEmployee = multiEmployee;
        this.data = data;
    }

    public void sendBees() {
        /*ArrayList<MultiFlower> rouletteResults = new ArrayList<>();
        for(int i = 0; i < multiMeadow.getFlowers().size(); i++) {
            rouletteResults.add(roulette());
        }*/
        ArrayList<MultiFlower> rouletteResults = IntStream.rangeClosed(1, multiMeadow.getFlowers().size()).parallel().mapToObj(v -> roulette()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        //TODO: Take care of overlapping
        /*
        HashMap<MultiFlower, Integer> map = new HashMap<>();
        for(MultiFlower flower : rouletteResults) {
            Integer integer = map.get(flower);
            map.put(flower, integer == null ? 1 : integer + 1);
        }
        */

        rouletteResults.parallelStream().forEach(multiEmployee::processFlowerSynchronized);

        /*
        for(MultiFlower flower : rouletteResults) {
            multiEmployee.processFlower(flower);
        }
         */
    }

    public abstract MultiFlower roulette();
}
