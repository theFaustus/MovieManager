/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.pad.moviemanager.mediator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faust
 */
public class MavenDetector implements Runnable {

    private MediatorConfig config;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private List<String> nodes = Collections.synchronizedList(new ArrayList<>());

    private Logger logger = Logger.getLogger(MavenDetector.class.getName());
    private DatagramSocket socket;

    public MavenDetector(MediatorConfig config) {
        this.config = config;
    }

    public void discoverNodes() {
        try {
            socket = new DatagramSocket();
            final byte[] buf = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setAddress(InetAddress.getByName(config.getNodeDiscoverAddress()));
            packet.setPort(config.getNodeDiscoverPort());
            packet.setData("SYN".getBytes());
            socket.send(packet);
            executorService.submit(this);
            Thread.sleep(config.getTimeout());
            executorService.shutdownNow();
            socket.close();
        } catch (SocketException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < config.getTimeout()) {
            final byte[] buf = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                nodes.add(new String(packet.getData()));
                logger.log(Level.INFO, "Nodes: " + nodes);
                logger.log(Level.INFO, new String(packet.getData()));
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Socket closed", ex);

            }
        }
    }

    public MediatorConfig getConfig() {
        return config;
    }

    public void setConfig(MediatorConfig config) {
        this.config = config;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

}
