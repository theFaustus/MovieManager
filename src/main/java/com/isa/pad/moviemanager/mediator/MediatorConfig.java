package com.isa.pad.moviemanager.mediator;

/**
 * Created by Faust on 11/11/2017.
 */
public class MediatorConfig {

    private String nodeDiscoverAddress = "233.3.3.3";
    private int nodeDiscoverPort = 9999;
    private int timeout = 10_000;
    private int clientPort = 8888;

    public String getNodeDiscoverAddress() {
        return nodeDiscoverAddress;
    }

    public void setNodeDiscoverAddress(String nodeDiscoverAddress) {
        this.nodeDiscoverAddress = nodeDiscoverAddress;
    }

    public int getNodeDiscoverPort() {
        return nodeDiscoverPort;
    }

    public void setNodeDiscoverPort(int nodeDiscoverPort) {
        this.nodeDiscoverPort = nodeDiscoverPort;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

}
