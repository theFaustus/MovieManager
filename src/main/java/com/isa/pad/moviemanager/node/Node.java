
package com.isa.pad.moviemanager.node;

import com.isa.pad.moviemanager.util.UdpResponse;
import com.isa.pad.moviemanager.model.MovieDataSource;
import com.isa.pad.moviemanager.util.JsonSerializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/11/2017.
 */
public class Node implements Runnable {

    private Logger logger = Logger.getLogger(Node.class.getName());

    private NodeConfig config;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private MulticastSocket socket;
    private String nodeName;
    private NodeDataExchangeServer nodeDataExchangeServer;

    public Node(NodeConfig config, String nodeName) {
        this.config = config;
        try {
            this.socket = new MulticastSocket(config.getDiscoverPort());
            this.socket.joinGroup(InetAddress.getByName(config.getDiscoverAddress()));
            this.nodeName = nodeName;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void start() {
        executorService.submit(this);
        nodeDataExchangeServer = new NodeDataExchangeServer(config, executorService, nodeName);
    }

    public void stop() {
        try {
            socket.leaveGroup(InetAddress.getByName(config.getDiscoverAddress()));
            executorService.shutdownNow();
            socket.close();
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                final byte[] buf = new byte[4096];
                final DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                socket.receive(datagramPacket);
                String data = new String(datagramPacket.getData());
                logger.log(Level.INFO, data);
                final DatagramPacket responseDatagram = new DatagramPacket(buf, buf.length);
                responseDatagram.setAddress(datagramPacket.getAddress());
                responseDatagram.setPort(datagramPacket.getPort());
                MovieDataSource movieDataSource = MovieDataSource.INSTANCE;
                responseDatagram.setData(JsonSerializer.toJson(
                        new UdpResponse(
                                movieDataSource.getNodeDataListSizeFor(getNodeName()),
                                movieDataSource.getNodeNumberOfConnectionsFor(getNodeName()),
                                movieDataSource.getNodeAddressFor(getNodeName()),
                                movieDataSource.getNodePortFor(getNodeName())
                                )).getBytes());
                socket.send(responseDatagram);
                logger.log(Level.INFO, "Sent response.");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
