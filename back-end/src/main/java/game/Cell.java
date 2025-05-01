package game;

public class Cell {
    private final int x;
    private final int y;
    private final String text;
    private final boolean playable;

    public Cell(int x, int y, String text, boolean playable) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public String getText() { return text; }

    public boolean isPlayable() { return playable; }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",
                    "playable": %b,
                    "x": %d,
                    "y": %d
                }
                """.formatted(text, playable, x, y);
    }
}
