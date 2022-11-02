package exercise.connections;

public interface Connection {

    void connect();

    void disconnect();

    void write(String text);

    String getCurrentState();
}
