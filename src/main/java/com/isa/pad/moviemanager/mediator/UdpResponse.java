package com.isa.pad.moviemanager.mediator;

/**
 * Created by Faust on 11/11/2017.
 */
public class UdpResponse {
    private int dataSize;
    private int numberOfConnections;
    private String address;
    private int port;

    public UdpResponse() {
    }

    public UdpResponse(int dataSize, int numberOfConnections, String address, int port) {
        this.dataSize = dataSize;
        this.numberOfConnections = numberOfConnections;
        this.address = address;
        this.port = port;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getNumberOfConnections() {
        return numberOfConnections;
    }

    public void setNumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
