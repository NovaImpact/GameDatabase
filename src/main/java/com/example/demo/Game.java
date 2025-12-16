package com.example.demo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rank;
    private String title;
    private double sales; // in millions
    private LocalDate releaseDate;
    private String imagePath; // For game cover art

    private static ArrayList<Game> allGames = new ArrayList<>();
    private static final String GAMES_FILE = "allgames.dat";

    public Game(int rank, String title, double sales, LocalDate releaseDate) {
        this.rank = rank;
        this.title = title;
        this.sales = sales;
        this.releaseDate = releaseDate;
        this.imagePath = null;
        allGames.add(this);
    }

    public static ArrayList<Game> getAllGames() {
        return allGames;
    }

    public static void setAllGames(ArrayList<Game> games) {
        allGames = games;
    }

    public static void clearAllGames() {
        allGames.clear();
    }

    public static void saveAllGames() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(GAMES_FILE))) {
            oos.writeObject(allGames);
            System.out.println("Saved " + allGames.size() + " games to " + GAMES_FILE);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadAllGames() throws IOException, ClassNotFoundException {
        File file = new File(GAMES_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(GAMES_FILE))) {
                allGames = (ArrayList<Game>) ois.readObject();
                System.out.println("Loaded " + allGames.size() + " games from " + GAMES_FILE);
            }
        } else {
            System.out.println("No saved games file found. Starting fresh.");
        }
    }

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

    @Override
    public String toString() {
        return "Rank #" + rank + " \"" + title + "\" - " + sales + "M copies sold, released: " + releaseDate;
    }
}