package client;

public interface ClientView {
    void showMessage(String message);
    void disconnectFromServer();
    void setClientController(ClientController clientController);
}
