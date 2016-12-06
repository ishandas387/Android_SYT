package com.ishan387.spotyourtrain;

/**
 * Created by ishan on 7/14/2015.
 */
public class Trains {

    String code = null;
    String name = null;
    String continent = null;
    String region = null;
    Double lifeExpectancy = null;
    Double gnp = null;
    Double surfaceArea = null;
    int population = 0;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public void setPopulation(int population) {
        this.population = population;
    }



    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public Double getGnp() {
        return gnp;
    }

    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public int getPopulation() {
        return population;
    }


}
