import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private ServerWindow server;
    private boolean connected;
    private String clientName;

    JTextArea log;
    JTextField IPAdress, PortAdress, login, message;
    JPasswordField password;
    JButton connectButton;
    JButton send;

    JPanel header;
    JPanel footer;

    public ClientGUI(ServerWindow server) {
        this.server = server;

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createPanel();

        setVisible(true);
    }

    private void appendLog(String text) {
        log.append(text + "\n");
    }

    private void message() {
        if (connected) {
            String text = message.getText();
            server.message(clientName + ": " + text);
            message.setText("");
        } else {
            appendLog("Server not connected");
        }
    }

    public void connectToTheServer() {
        if (server.connectUserToServer(this)) {
            connected = true;
            appendLog("You are connected successfully");

            header.setVisible(false);

            clientName = login.getText();
            String log = server.getLog();

            if (log != null) {
                appendLog(log);
            }

        } else {
            appendLog("You are not logged in");
        }
        System.out.println(connected);
    }
    public void disconnectFromTheServer() {
        if (connected) {
            connected = false;
            header.setVisible(true);

            server.disconnectUser(this);

            appendLog("You are disconnected or server is down");
        }
    }

    private void createPanel() {
        add(createHeader(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeader() {
        header = new JPanel(new GridLayout(2, 3));
        IPAdress = new JTextField("127.0.0.1");
        PortAdress = new JTextField("1488");
        login = new JTextField("John Doe");
        password = new JPasswordField("123qwerty");

        connectButton = new JButton("Connect");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connectToTheServer();
            }
        });

        header.add(IPAdress);
        header.add(PortAdress);
        header.add(login);
        header.add(password);
        header.add(connectButton);
        return header;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);

        return new JScrollPane(log);
    }

    private Component createFooter() {
        footer = new JPanel(new GridLayout(1, 2));

        message = new JTextField();
        send = new JButton("Send");

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                message();
            }
        });

        footer.add(message);
        footer.add(send);

        return footer;
    }


}
