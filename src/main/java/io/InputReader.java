package io;

import domain.Car;
import domain.Intersection;
import domain.ProblemStatement;
import domain.Street;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InputReader {
    public InputReader() {}

    public ProblemStatement readProblemStatement(String fileName) {
        Path path = Paths.get(fileName);
        Path absolutePath = path.toAbsolutePath();
        ProblemStatement problemStatement = new ProblemStatement();

        try {
            List<String> lines = Files.readAllLines(absolutePath);
            String[] firstLine = lines.get(0).split(" ");
            //System.out.println(Arrays.deepToString(firstLine));

            int durationOfSimulation = Integer.parseInt(firstLine[0]);
            int numberOfIntersections = Integer.parseInt(firstLine[1]);
            int numberOfStreets = Integer.parseInt(firstLine[2]);
            int numberOfCars = Integer.parseInt(firstLine[3]);
            int bonusPoints = Integer.parseInt(firstLine[4]);

            Map<Integer, Intersection> intersectionMap = new HashMap<>();
            Map<String, Street> streetMap = new HashMap<>();
            List<Street> streets = new ArrayList<>();

            for(int i = 1; i < numberOfStreets+1;i++) {
                String[] streetLine = lines.get(i).split(" ");
                int startIntersection = Integer.parseInt(streetLine[0]);
                int endIntersections = Integer.parseInt(streetLine[1]);
                String nameOfStreet = streetLine[2];
                int timeToPassStreet = Integer.parseInt(streetLine[3]);

                Street street = new Street(nameOfStreet, timeToPassStreet);

                intersectionMap.putIfAbsent(startIntersection, new Intersection(startIntersection));
                intersectionMap.get(startIntersection).outgoing.add(street);

                intersectionMap.putIfAbsent(endIntersections, new Intersection(endIntersections));
                intersectionMap.get(endIntersections).incoming.add(street);

                street.setStart(intersectionMap.get(startIntersection));
                street.setEnd(intersectionMap.get(endIntersections));

                streetMap.put(nameOfStreet, street);
                streets.add(street);

           //     System.out.println(street);
            }

            List<Car> allCars = new ArrayList<>();

            for(int i = numberOfStreets+1; i < numberOfStreets + numberOfCars + 1; i++){
                String[] carline = lines.get(i).split(" ");
                int carNumberOfStreets = Integer.parseInt(carline[0]);

                List<Street> streetList = new ArrayList<>();
                Street startingStreet = null;

             //   System.out.println(Arrays.deepToString(carline));
                for(int j = 1; j < carline.length; j++) {

                   // System.out.println(carline[j]);
                  //  System.out.println(streetMap.get(carline[j]));
                    streetList.add(streetMap.get(carline[j]));

                    if(j == 1) {
                        startingStreet = streetMap.get(carline[j]);
                    }
                }

                Car car = new Car(i, carNumberOfStreets, streetList, startingStreet);
                startingStreet.getCarList().add(car);
                allCars.add(car);

               // System.out.println(car);
            }

            problemStatement.initProblem(allCars, streets, durationOfSimulation, numberOfIntersections, bonusPoints, intersectionMap, streetMap);



        } catch (IOException e) {
            e.printStackTrace();
        }

        return problemStatement;

    }
}
