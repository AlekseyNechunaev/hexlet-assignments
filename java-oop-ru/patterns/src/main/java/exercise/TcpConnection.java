package exercise;

import exercise.connections.Connection;
import exercise.connections.Disconnected;

public class TcpConnection {

    private Connection state;
    private final String host;
    private final int port;

    private final StringBuffer buffer;

    public TcpConnection(String host, int port) {
        this.state = new Disconnected(this);
        this.host = host;
        this.port = port;
        this.buffer = new StringBuffer();
    }

    public void connect() {
        state.connect();
    }

    public void disconnect() {
        state.disconnect();
    }


    public void write(String text) {
        state.write(text);
    }

    public String getCurrentState() {
        return state.getCurrentState();
    }

    public void setState(Connection state) {
        this.state = state;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public StringBuffer getBuffer() {
        return buffer;
    }
}