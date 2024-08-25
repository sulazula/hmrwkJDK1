package server.filemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileStorage implements Repository<String>{
    public static final String LOG_PATH = "src/log.txt";

    @Override
    public void saveInLog(String text) {
        try (FileWriter fw = new FileWriter(LOG_PATH, true);){
            fw.write(text + "\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String readFromLog() {
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
}
