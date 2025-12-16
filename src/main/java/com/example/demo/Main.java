package com.example.demo;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=".repeat(70));
            System.out.println("VIDEO GAME DATA EDITOR - CONSOLE TEST");
            System.out.println("=".repeat(70));
            System.out.println();

            System.out.println("Attempting to load serialized data...");
            try {
                Game.loadAllGames();
                GameCopies.loadAllGameCopies();
                GameRating.loadAllRatings();
            } catch (Exception e) {
                System.out.println("No serialized data found. Loading from text files...");
                GameCopies.readGameCopiesData();
                GameRating.readGameRatingData();
            }

            System.out.println();
            System.out.println("=".repeat(70));
            System.out.println("DATA LOADING SUMMARY");
            System.out.println("=".repeat(70));
            System.out.println("Total Games:        " + Game.getAllGames().size());
            System.out.println("GameCopies Entries: " + GameCopies.getAllGameCopies().size());
            System.out.println("GameRating Entries: " + GameRating.getAllRatings().size());
            System.out.println("=".repeat(70));
            System.out.println();

            System.out.println("=".repeat(70));
            System.out.println("ALL GAMES (" + Game.getAllGames().size() + " entries)");
            System.out.println("=".repeat(70));
            for (Game game : Game.getAllGames()) {
                System.out.println(game);
                System.out.println();
            }

            System.out.println("=".repeat(70));
            System.out.println("ONLY GAME COPIES (" + GameCopies.getAllGameCopies().size() + " entries)");
            System.out.println("=".repeat(70));
            for (GameCopies game : GameCopies.getAllGameCopies()) {
                System.out.println(game);
                System.out.println();
            }

            System.out.println("=".repeat(70));
            System.out.println("ONLY GAME RATINGS (" + GameRating.getAllRatings().size() + " entries)");
            System.out.println("=".repeat(70));
            for (GameRating rating : GameRating.getAllRatings()) {
                System.out.println(rating);
                System.out.println();
            }

            System.out.println("=".repeat(70));
            System.out.println("TESTING SERIALIZATION...");
            System.out.println("=".repeat(70));

            Game.saveAllGames();
            GameCopies.saveAllGameCopies();
            GameRating.saveAllRatings();

            System.out.println("Data saved successfully!");
            System.out.println("=".repeat(70));
            System.out.println("TEST COMPLETED SUCCESSFULLY!");
            System.out.println("=".repeat(70));

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}