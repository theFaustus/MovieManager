package com.isa.pad.moviemanager.client;

/**
 * Created by Faust on 11/12/2017.
 */
public class ClientConfig {
    private String mediatorAddress = "localhost";
    private int mediatorPort = 7777;

    public ClientConfig(String mediatorAddress, int mediatorPort) {
        this.mediatorAddress = mediatorAddress;
        this.mediatorPort = mediatorPort;
    }

    public ClientConfig() {
    }

    public String getMediatorAddress() {
        return mediatorAddress;
    }

    public void setMediatorAddress(String mediatorAddress) {
        this.mediatorAddress = mediatorAddress;
    }

    public int getMediatorPort() {
        return mediatorPort;
    }

    public void setMediatorPort(int mediatorPort) {
        this.mediatorPort = mediatorPort;
    }
}
