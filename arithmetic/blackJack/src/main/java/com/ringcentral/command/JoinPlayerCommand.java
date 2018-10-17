package com.ringcentral.command;

import com.ringcentral.domain.BlackJackPlayer;
import com.ringcentral.service.GameDispatchService;
import com.ringcentral.service.GameService;

import java.util.List;

public class JoinPlayerCommand implements Command {
    private GameDispatchService dispatch;

    public JoinPlayerCommand(GameDispatchService dispatch) {
        this.dispatch = dispatch;
    }

    public void showDescription(GameService gameService) {
        System.out.println("Please wait some players to join this game.");
        System.out.println("Enter 'join xxx' to join the game as player's name 'xxx'; Enter 'start' to start the game!");
    }

    @Override
    public void commandDispatch(GameService gameService, String key, List<String> params) {
        switch (key.toLowerCase()) {
            case "join":
                gameService.joinPlayer(new BlackJackPlayer(params.get(0)));
                break;
            case "start":
                gameService.startGame();
                break;
            default:
                throw new RuntimeException("Invalid key");
        }
    }
}
