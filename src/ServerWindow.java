import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String LOG_PATH = "src/log.txt";

    List<ClientGUI> clientsList;
    JButton startButton;
    JButton stopButton;
    boolean work;
    JTextArea log;

    public ServerWindow() {
        clientsList = new ArrayList<>();

        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setTitle("Server");
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(createLog());
        add(createButtonsPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    public void appendLog(String text) {
        log.append(text + "\n");
    }

    public boolean connectUserToServer(ClientGUI client) {
        if (!work) {
            return false;
        } else {
            clientsList.add(client);
            return true;
        }
    }

    public void disconnectUser(ClientGUI client) {
        clientsList.remove(client);
        if (client != null) {
            client.disconnectFromTheServer();
        }
    }

    public void message(String msg) {
        if (!work) {
            return;
        }
        appendLog(msg);
        saveInLog(msg);
    }

    private void saveInLog(String text) {
        try (FileWriter fw = new FileWriter(LOG_PATH, true);){
            fw.write(text + "\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getLog() {
        return readFromLog();
    }

    private String readFromLog() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_PATH))) {
            int c;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Component createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (work) {
                    appendLog("Server is already running");
                } else {
                    work = true;
                    appendLog("Server is now running");
                }
                System.out.println(work);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!work) {
                    appendLog("Server is already stopped");
                } else {
                    work = false;
                    while (!clientsList.isEmpty()) {
                        disconnectUser(clientsList.getLast());
                    }
                    appendLog("Server is now stopped");
                }
            }
        });

        panel.add(startButton);
        panel.add(stopButton);
        return panel;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);

        return new JScrollPane(log);
    }
}
