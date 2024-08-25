package client;

import server.controller.ServerController;

public class ClientController {

    private boolean connected;
    private String clientName;
    private ClientView clientView;
    private ServerController server;

    public ClientController(ClientView clientView, ServerController server) {
        this.clientView = clientView;
        this.server = server;
        clientView.setClientController(this);
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    public void setServer(ServerController server) {
        this.server = server;
    }

    public void disconnectFromServer() {
        server.disconnectUserFromServer(this);
    }
    public boolean connectToServer(String name) {
        this.clientName = name;
        if (server.connectUserToServer(this)) {
            showInfo("You are all set");
            connected = true;
            String log = server.getLog();
            if (log != null) {
                showInfo(log);
            } else {
                System.out.println("log is empty");
            }
            return true;
        } else {
            showInfo("You was not connected");
            return false;
        }
    }

    public void message(String text) {
        if (connected) {
            if (!text.equals("")) {
                server.message(clientName + ": " + text);
            }
        } else {
            server.message("Server not connected");
        }
    }

    public void showInfo(String text) {
        clientView.showMessage(text);
    }

    public String getClientName() {
        return clientName;
    }
}
