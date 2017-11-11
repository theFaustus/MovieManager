
package com.isa.pad.moviemanager.node;

/**
 * Created by Faust on 11/11/2017.
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
