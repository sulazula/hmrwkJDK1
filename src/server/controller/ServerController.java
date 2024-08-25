package server.controller;

import client.ClientController;
import client.ClientGUI;
import server.filemanager.Repository;
import server.ui.ServerView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private ServerView serverView;
    private Repository<String> repository;
    List<ClientController> clientsList;
    private boolean work;

    public ServerController(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;

        clientsList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void setServerView(ServerView serverView){
        this.serverView = serverView;
    }
    public void setRepo(Repository repository){
        this.repository = repository;
    }

    // добавил вывод переписки в оба чата, что бы на сервер можно было не смотреть и все работало как чат
    public void message(String text) {
        if (!work) {
            return;
        } else {
            for (ClientController client : clientsList) {
                client.showInfo(text);
            }
            showInfo(text);
            save(text);
        }
    }

    public void start() {
        if (work) {
            showInfo("Server is already running");
        } else {
            work = true;
            showInfo("Server is running");
        }
    }
    public void stop() {
        if (!work) {
            showInfo("Server is already stopped");
        } else {
            work = false;
            showInfo("Server is stopped");
        }
    }

    public boolean connectUserToServer(ClientController client) {
        if (!work) {
            return false;
        } else {
            clientsList.add(client);
            showInfo(client.getClientName() + " connected");
            return true;
        }
    }

    public void disconnectUserFromServer(ClientController client) {
        clientsList.remove(client);
        if (client != null) {
            client.disconnectFromServer();
            showInfo( client.getClientName() + " disconnected");
        }
    }

    public String getLog() {
        return repository.readFromLog();
    }

    private void showInfo(String text) {
        serverView.showMessage(text + "\n");
    }

    private void save(String text) {
        repository.saveInLog(text);
    }
}
