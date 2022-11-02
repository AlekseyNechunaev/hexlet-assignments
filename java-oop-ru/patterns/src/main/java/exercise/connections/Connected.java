package exercise.connections;

import exercise.TcpConnection;

public class Connected implements Connection {

    private static final String INVALID_STATE_CONNECTED = "Error: Invalid state, the connection is already established";
    private static final String SUCCESS_DISCONNECT = "Connection successfully terminated: ";
    private final TcpConnection tcpConnection;

    public Connected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }

    @Override
    public void connect() {
        System.out.println(INVALID_STATE_CONNECTED);
    }

    @Override
    public void disconnect() {
        this.tcpConnection.setState(new Disconnected(this.tcpConnection));
        System.out.println(SUCCESS_DISCONNECT + this.tcpConnection.getHost() + ":" + this.tcpConnection.getPort());
    }

    @Override
    public void write(String text) {
        this.tcpConnection.getBuffer().append(text);
    }

    @Override
    public String getCurrentState() {
        return "connected";
    }
}
