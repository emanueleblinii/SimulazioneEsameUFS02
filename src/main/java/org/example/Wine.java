package org.example;

public class Wine {
    String type, name;
    double prz;

    public Wine() {
        this(null, null, 0);
    }
    public Wine(String name, String type, double prz) {
        this.name = name;
        this.type = type;
        this.prz = prz;
    }

    public String getName() {
        return name;
    }
    public double getPrz() {
        return prz;
    }
    public String toString() {
        return "Name: "+name+"\nType: "+type+"\nPrice: "+prz+"€";
    }

}
