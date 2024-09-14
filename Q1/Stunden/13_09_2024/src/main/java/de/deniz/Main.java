package de.deniz;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);
    final Game game = new Game(scanner);

    game.start();
  }
}