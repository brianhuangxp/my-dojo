package com.ringcentral;

import com.ringcentral.service.GameDispatchService;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        GameDispatchService dispatch = new GameDispatchService();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            dispatch.commandDispatch(sc.nextLine().split(" "));
        }
    }
}
