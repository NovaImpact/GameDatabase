package com.example.demo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class Game implements Serializable {
    private static final long serialVersionUID = 2L;
    private int rank;
    private String title;
    private double sales; // in millions
    private LocalDate releaseDate;
    private transient String imagePath; // For game cover art
    private static ArrayList<Game> allGames = new ArrayList<>();

    public Game(int rank, String title, double sales, LocalDate releaseDate) {
        this.rank = rank;
        this.title = title;
        this.sales = sales;
        this.releaseDate = releaseDate;
        this.imagePath = null;
        allGames.add(this);
    }

    // Static collection methods
    public static ArrayList<Game> getAllGames() {
        return allGames;
    }

    public static void setAllGames(ArrayList<Game> games) {
        allGames = games;
    }

    public static void clearAllGames() {
        allGames.clear();
    }

    // Getters and Setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    static void saveData() throws Exception {
        FileOutputStream fileOut = new FileOutputStream("SavedGameFile");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(allGames);
        objectOut.close();
        fileOut.close();
    }

    public void stop() throws Exception {
        Game.saveData();
    }

    static void restoreData() throws Exception {
        FileInputStream fileIn = new FileInputStream("SavedGameFile");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        allGames = (ArrayList<Game>)objectIn.readObject();
        objectIn.close();
        fileIn.close();
    }
    public void load() throws Exception {
        Game.restoreData();
    }
    //game restoring


    @Override
    public String toString() {
        return "Rank #" + rank + " \"" + title + "\" - " + sales + "M copies sold, released: " + releaseDate;
    }
}
//oka