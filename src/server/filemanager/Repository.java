package server.filemanager;

public interface Repository<T> {
    void saveInLog(T text);
    T readFromLog();
}
