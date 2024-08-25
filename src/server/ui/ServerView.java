package server.ui;

import server.controller.ServerController;

public interface ServerView {
    void showMessage(String text);
    void setServerController(ServerController serverController);
}
