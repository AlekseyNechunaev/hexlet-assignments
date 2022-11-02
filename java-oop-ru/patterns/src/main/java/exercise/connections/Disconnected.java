package exercise.connections;

import exercise.TcpConnection;

public class Disconnected implements Connection {


    private static final String NOT_CONNECTED = "Error: there is no connection";
    private static final String INVALID_STATE_DISCONNECTED = "Error: Invalid state, connections are already broken";
    private static final String SUCCESS_CONNECT = "The connection has been successfully established to: ";
    private final TcpConnection tcpConnection;

    public Disconnected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }

    @Override
    public void connect() {
        tcpConnection.setState(new Connected(this.tcpConnection));
        System.out.println(SUCCESS_CONNECT + this.tcpConnection.getHost() + ":" + this.tcpConnection.getPort());
    }

    @Override
    public void disconnect() {
        System.out.println(INVALID_STATE_DISCONNECTED);
    }

    @Override
    public void write(String text) {
        System.out.println(NOT_CONNECTED);
    }

    @Override
    public String getCurrentState() {
        return "disconnected";
    }
}
