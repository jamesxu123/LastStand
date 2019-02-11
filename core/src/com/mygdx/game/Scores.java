package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/*
Handles asynchronous reading and writing of scores from a text file
 */

public class Scores {
    public static void writeScore(int score, LastStand game) {
        //Appends a score to the end of a text file
        CompletableFuture.runAsync(() -> {
            try {
                FileHandle fileHandle = new FileHandle("highscores.txt");
                fileHandle.writeString(game.player.getLevel() + "\n", true);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    public static CompletableFuture<ArrayList<Integer>> readScores() {
        //Returns a Future containing the numbers from the text file
        return CompletableFuture.supplyAsync(() -> {
            FileHandle fileHandle = new FileHandle("highscores.txt");
            Scanner scanner = new Scanner(fileHandle.reader());
            ArrayList<Integer> scores = new ArrayList<>();
            while (scanner.hasNextInt()) {
                scores.add(scanner.nextInt());
            }
            return scores;
        });
    }
}
