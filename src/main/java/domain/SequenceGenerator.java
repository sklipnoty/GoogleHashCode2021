package domain;

import javax.sound.midi.Sequence;
import java.util.*;

public class SequenceGenerator {

    public Map<List<Integer>,  Map<Integer, List<Integer>>> duoMap = new HashMap<>();

    public SequenceGenerator(Integer durationOfSimulation) {
        makeMaps(durationOfSimulation);
    }

    public void makeMaps(Integer durationOfSimulation) {

        // first make all 2 sequences permutation with a limit of 100
        int limit = 100;

        for(int i = 1; i < limit; i++){
            for(int j = 1; j < limit;j++) {
                Map<Integer, List<Integer>> sequenceMap = generate(Arrays.asList(i, j), durationOfSimulation);
                duoMap.put(Arrays.asList(i, j), sequenceMap);
            }
        }

    }

    public Map<Integer, List<Integer>> generate(List<Integer> durations, Integer durationOfSimulation) {

        Map<Integer, List<Integer>> sequenceMap = new HashMap<>();

        Integer durationStep = 0;

        while(durationStep < durationOfSimulation) {

            for(int j = 0; j < durations.size(); j++) {

                for(int i = 0; i < durations.get(j); i++) {

                    sequenceMap.putIfAbsent(j, new ArrayList<>());
                    sequenceMap.get(j).add(durationStep+i);

                    if(durationStep >= durationOfSimulation)
                        break;
                }

                durationStep += durations.get(j);


                if(durationStep >= durationOfSimulation)
                    break;

            }
        }

        return sequenceMap;
    }

    public  List<Integer> findBestSequenceDuo(List<List<Integer>> sequences) {

        int maxNumberOfRetained = 0;
        List<Integer> bestCombination = null;

        for(List<Integer> combination : duoMap.keySet()) {
            Map<Integer, List<Integer>> map = duoMap.get(combination);

            int totalRetained = 0;
          //  System.out.println("Testing combination " + combination);
            for(int i = 0; i < sequences.size(); i++ ) {
                List<Integer> sequenceCopy = new ArrayList<>(sequences.get(i));
                sequenceCopy.retainAll(map.get(i));
                totalRetained += sequenceCopy.size();
            }

            if(totalRetained > maxNumberOfRetained) {
                maxNumberOfRetained = totalRetained;
                //System.out.println("Best combo " + combination + " " + maxNumberOfRetained);
                bestCombination = combination;
            }
        }

        return bestCombination;
    }
}
