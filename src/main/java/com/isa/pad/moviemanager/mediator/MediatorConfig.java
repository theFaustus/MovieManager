/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.pad.moviemanager.mediator;

/**
 *
 * @author Faust
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
