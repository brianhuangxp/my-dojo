package com.ringcentral.command;

import com.ringcentral.service.GameDispatchService;
import com.ringcentral.service.GameService;

import java.util.List;

public class ResultCommand implements Command {
    private GameDispatchService dispatch;

    public ResultCommand(GameDispatchService dispatch) {
        this.dispatch = dispatch;
    }

    public void showDescription(GameService gameService) {
        gameService.showResult();
        System.out.println("Enter 'start' to restart a new game; Enter 'reset' to restart this game application; Any others to exit this game!");
    }

    public void commandDispatch(GameService gameService, String key, List<String> params) {
        switch (key.toLowerCase()) {
            case "start":
                gameService.resetGame(false);
                gameService.startGame();
                break;
            case "reset":
                gameService.resetGame(true);
                break;
            default:
                System.exit(0);
        }
    }
}