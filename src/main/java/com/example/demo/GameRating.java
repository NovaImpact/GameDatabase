package com.example.demo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class GameRating extends Game implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rating;
    private int metascore;
    private String developerPublisher;

    private static ArrayList<GameRating> allRatings = new ArrayList<>();
    private static final String RATINGS_FILE = "gameratings.dat";

    public GameRating(int rank, String title, double sales, LocalDate releaseDate,
                      String rating, int metascore, String developerPublisher) {
        super(rank, title, sales, releaseDate);
        this.rating = rating;
        this.metascore = metascore;
        this.developerPublisher = developerPublisher;
        allRatings.add(this);
    }

    public static ArrayList<GameRating> getAllRatings() {
        return allRatings;
    }

    public static void setAllRatings(ArrayList<GameRating> ratings) {
        allRatings = ratings;
    }

    public static void clearAllRatings() {
        allRatings.clear();
    }

    public static void saveAllRatings() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(RATINGS_FILE))) {
            oos.writeObject(allRatings);
            System.out.println("Saved " + allRatings.size() + " game ratings to " + RATINGS_FILE);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadAllRatings() throws IOException, ClassNotFoundException {
        File file = new File(RATINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(RATINGS_FILE))) {
                allRatings = (ArrayList<GameRating>) ois.readObject();
                System.out.println("Loaded " + allRatings.size() + " game ratings from " + RATINGS_FILE);
            }
        } else {
            System.out.println("No saved game ratings file found. Starting fresh.");
        }
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public String getDeveloperPublisher() {
        return developerPublisher;
    }

    public void setDeveloperPublisher(String developerPublisher) {
        this.developerPublisher = developerPublisher;
    }

    @Override
    public String toString() {
        return "GameRating - " + super.toString() +
                "\n  Rating: " + rating +
                " | Metascore: " + metascore +
                " | Dev/Publisher: " + developerPublisher;
    }

    public static void readGameRatingData() throws Exception {
        File file = new File("GameRatingData");
        Scanner sc = new Scanner(file);

        if (sc.hasNextLine()) {
            String header = sc.nextLine();
            System.out.println("Loading GameRating data...");
            System.out.println("Header: " + header);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);
        int count = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split("\t");
            if (parts.length < 6) {
                System.out.println("Skipping incomplete line: " + line);
                continue;
            }

            try {
                int rank = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                LocalDate date;
                try {
                    date = LocalDate.parse(parts[2].trim(), formatter);
                } catch (Exception e) {
                    date = LocalDate.of(2000, 1, 1);
                }

                String ratingStr = parts[3].trim();
                int metascore = Integer.parseInt(parts[4].trim());
                String developerPublisher = parts[5].trim();
                double sales = 0.0;

                new GameRating(rank, title, sales, date, ratingStr, metascore, developerPublisher);
                count++;
            } catch (Exception e) {
                System.out.println("Error parsing line: " + line);
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Loaded " + count + " GameRating entries.");
    }
}