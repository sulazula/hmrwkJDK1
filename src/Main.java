import client.ClientController;
import client.ClientGUI;
import server.controller.ServerController;
import server.filemanager.FileStorage;
import server.ui.ServerGUI;

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerGUI(), new FileStorage());

        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);
    }
}