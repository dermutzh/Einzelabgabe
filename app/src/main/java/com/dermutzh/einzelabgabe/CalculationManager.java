package com.dermutzh.einzelabgabe;

import java.util.ArrayList;
import java.util.Collections;

class CalculationManager {

    CalculationManager() {
    }

    String performCalculation(char[] inputChars) {

        ArrayList<Character> toSort = new ArrayList<>();

        for (char c : inputChars) {
            if (!isPrime(c)) {
                toSort.add(c);
            }
        }

        Collections.sort(toSort);

        return toSort.toString();
    }


    private boolean isPrime(char cx) {

        int c = Character.getNumericValue(cx);

        if ((c != 0 && c <= 3) || c == 5 || c == 7) {
            return true;
        }

        return false;
    }
}
