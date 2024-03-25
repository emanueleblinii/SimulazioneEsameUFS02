package org.example;

import java.util.ArrayList;

public class CellarManager {
    ArrayList<Wine> wines;

    public CellarManager() {
        this.wines = new ArrayList<>();
    }

    public void add(Wine wine) {
        wines.add(wine);
    }

}
