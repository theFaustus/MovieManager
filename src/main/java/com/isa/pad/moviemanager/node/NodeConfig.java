/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.pad.moviemanager.node;

/**
 *
 * @author Faust
 */
public class NodeConfig {

    private String discoverAddress = "233.3.3.3";
    private int discoverPort = 9999;

    public String getDiscoverAddress() {
        return discoverAddress;
    }

    public void setDiscoverAddress(String discoverAddress) {
        this.discoverAddress = discoverAddress;
    }

    public int getDiscoverPort() {
        return discoverPort;
    }

    public void setDiscoverPort(int discoverPort) {
        this.discoverPort = discoverPort;
    }

}
