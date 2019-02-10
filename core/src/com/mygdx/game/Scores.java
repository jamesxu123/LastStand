package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Scores {
    public static void writeScore(int score, LastStand game) {
        CompletableFuture.runAsync(() -> {
            try {
                FileHandle fileHandle = new FileHandle("highscores.txt");
                fileHandle.writeString(game.player.getLevel() + "\n", true);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

    public static CompletableFuture<ArrayList<Integer>> getScores() {
        CompletableFuture<ArrayList<Integer>> arrayListCompletableFuture = CompletableFuture.supplyAsync(() -> {
            FileHandle fileHandle = new FileHandle("highscores.txt");
            Scanner scanner = new Scanner(fileHandle.reader());
            ArrayList<Integer> scores = new ArrayList<>();
            while (scanner.hasNextInt()) {
                scores.add(scanner.nextInt());
            }
            return scores;
        });
        return arrayListCompletableFuture;
    }
}
