package com.example.demo;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class GameCopies extends com.example.demo.Game {
    private String series;
    private String platforms;
    private int releaseYear;
    private String developer;
    private String publisher;
    private static ArrayList<GameCopies> allGameCopies = new ArrayList<>();

    public GameCopies(int rank, String title, double sales, LocalDate releaseDate,
                      String series, String platforms, int releaseYear,
                      String developer, String publisher) {
        super(rank, title, sales, releaseDate);
        this.series = series;
        this.platforms = platforms;
        this.releaseYear = releaseYear;
        this.developer = developer;
        this.publisher = publisher;
        allGameCopies.add(this);
    }

    public static ArrayList<GameCopies> getAllGameCopies() {
        return allGameCopies;
    }

    public static void setAllGameCopies(ArrayList<GameCopies> gameCopies) {
        allGameCopies = gameCopies;
    }

    public static void clearAllGameCopies() {
        allGameCopies.clear();
    }

    // Getters and Setters
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "GameCopies - " + super.toString() +
                "\n  Series: " + series +
                " | Platforms: " + platforms +
                " | Year: " + releaseYear +
                "\n  Developer: " + developer +
                " | Publisher: " + publisher;
    }



    public static void readGameCopiesData() throws Exception {
        File file = new File("GameCopiesData");
        Scanner sc = new Scanner(file);


        if (sc.hasNextLine()) {
            String header = sc.nextLine();
            System.out.println("Loading GameCopies data...");
            System.out.println("Header: " + header);
        }

        int count = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split("\t");

            if (parts.length < 8) {
                System.out.println("Skipping incomplete line: " + line);
                continue;
            }

            try {
                int rank = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                double sales = Double.parseDouble(parts[2].trim());
                String series = parts[3].trim();
                String platforms = parts[4].trim();

                int releaseYear;
                try {
                    releaseYear = Integer.parseInt(parts[5].trim());
                } catch (NumberFormatException e) {
                    releaseYear = 2000; // default year
                }

                String developer = parts[6].trim();
                String publisher = parts[7].trim();

                LocalDate releaseDate = LocalDate.of(releaseYear, 1, 1);

                new GameCopies(rank, title, sales, releaseDate, series, platforms,
                        releaseYear, developer, publisher);
                count++;

            } catch (Exception e) {
                System.out.println("Error parsing line: " + line);
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("Loaded " + count + " GameCopies entries.");
    }
}