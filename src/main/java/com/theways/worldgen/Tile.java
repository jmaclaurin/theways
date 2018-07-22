package com.theways.worldgen;

import org.apache.commons.lang3.ArrayUtils;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tile {
    private String resourceName;
    private int spawnability;
    private int rotation;

    public Tile(String resourceName, int spawnability, int rotation) {
        this.resourceName = resourceName;
        this.spawnability = spawnability;
        this.rotation = rotation;
    }

    public Tile clone() {
        return new Tile(this.resourceName, this.spawnability, 0);
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public int getSpawnability() {
        return this.spawnability;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int rotationIn) {
        this.rotation = rotationIn;
    }

    public List<Byte> getEdges() {
        List<Byte> rotatedList = Arrays.asList(ArrayUtils.toObject(resourceName.getBytes()));
        Collections.rotate(rotatedList, this.rotation);

        return rotatedList;
    }

    /**
     * Based on edge requirement, return a list containing this tile in all possible configurations
     * that would meet the requirement.
     *
     * @param edgeRequirement
     * @return
     */
    public ArrayList<Tile> getMatches(byte[] edgeRequirement) {
        ArrayList<Tile> matchedTiles = new ArrayList<>();
        List<Byte> edgeRequirementList = Arrays.asList(ArrayUtils.toObject(edgeRequirement));
        List<Byte> myEdges = new ArrayList<>(getEdges());

        for(int x = 0; x < myEdges.size(); x++) {
            Boolean isMatch = true;
            for(int y = 0; y < myEdges.size(); y++) {
                if(!myEdges.get(y).equals(edgeRequirementList.get(y)) && edgeRequirementList.get(y) != ' ') {
                    isMatch = false;
                }
            }

            if(isMatch) {
                Tile matchedTile = this.clone();
                matchedTile.setRotation(x);
                matchedTiles.add(matchedTile);
            }
            Collections.rotate(myEdges, 1);
        }

        return matchedTiles;
    }
}
