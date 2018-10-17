package com.ringcentral.command;

import com.ringcentral.domain.BlackJackPlayer;
import com.ringcentral.domain.GameStatus;
import com.ringcentral.service.GameDispatchService;
import com.ringcentral.service.GameService;

import java.util.List;

public class AskPlayerCommand implements Command {
    private GameDispatchService dispatch;

    public AskPlayerCommand(GameDispatchService dispatch) {
        this.dispatch = dispatch;
    }

    public void showDescription(GameService gameService) {
        if (!gameService.hasNextPlayer()) {
            gameService.statuesOnChange(GameStatus.COMPLETED);
            return;
        }

        BlackJackPlayer player = gameService.nextPlayer();
        System.out.println("Enter 'card': to aks for a new card;'skip': to stop asking card");
        player.showCards();
    }

    public void commandDispatch(GameService gameService, String key, List<String> params) {
        BlackJackPlayer player = gameService.getCurrentPlayer();
        switch (key.toLowerCase()) {
            case "card":
                gameService.sendCard(player);
                player.showCards();
                break;
            case "skip":
                player.setRequestCard(false);
                break;
            default:
                throw new RuntimeException("Invalid key");
        }

        nextPlayer();
    }

    private void nextPlayer() {
        System.out.println();
        dispatch.showDescription();
    }
}
