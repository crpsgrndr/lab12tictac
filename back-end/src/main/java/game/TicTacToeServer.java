package game;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper; 

import fi.iki.elonen.NanoHTTPD;

public class TicTacToeServer extends NanoHTTPD {

    private Game game;

    public TicTacToeServer() throws IOException {
        super(8080);
        this.game = new Game();
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("Server started at http://localhost:8080");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();

        if (uri.equals("/newgame")) {
            this.game = new Game();
        } else if (uri.equals("/play")) {
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            this.game = this.game.play(x, y);        
        } else if (uri.equals("/undo")) {
            this.game = this.game.undo();
        }

        GameState state = GameState.forGame(game);

        try {
            ObjectMapper mapper = new ObjectMapper(); 
            String json = mapper.writeValueAsString(state);

            return NanoHTTPD.newFixedLengthResponse(
                Response.Status.OK,
                "application/json",
                json
            );
        } catch (IOException e) {
            e.printStackTrace();
            return NanoHTTPD.newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                "text/plain",
                "Failed to serialize JSON"
            );
        }
    }
}
