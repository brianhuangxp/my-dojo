package com.ringcentral.service;

import com.ringcentral.command.AskPlayerCommand;
import com.ringcentral.command.Command;
import com.ringcentral.command.JoinPlayerCommand;
import com.ringcentral.command.ResultCommand;
import com.ringcentral.domain.BlackJackPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameDispatchService {
    GameService gameService;
    JoinPlayerCommand joinPlayerCommand;
    AskPlayerCommand askPlayerCommand;
    ResultCommand resultCommand;

    public GameDispatchService() {
        gameService = new GameService(this);
        joinPlayerCommand = new JoinPlayerCommand(this);
        askPlayerCommand = new AskPlayerCommand(this);
        resultCommand = new ResultCommand(this);
        gameService.resetGame(true);
    }

    private Command getCurrentCommand() {
        switch (gameService.getStatus()) {
            case INITIAL:
                return joinPlayerCommand;
            case SEND_CARD:
                return askPlayerCommand;
            case COMPLETED:
                return resultCommand;
        }
        return null;
    }


    public void showDescription() {
        Command command = getCurrentCommand();
        if (command != null) {
            command.showDescription(gameService);
        }
    }

    public void commandDispatch(String... params) {
        String key = params[0];

        List<String> paramList = Arrays.stream(params).skip(1).collect(Collectors.toList());

        try {
            Command command = getCurrentCommand();
            if (command != null) {
                command.commandDispatch(gameService, key, paramList);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
