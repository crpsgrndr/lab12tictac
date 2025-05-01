package game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new TicTacToeServer(); // серверээ ажиллуулна
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
