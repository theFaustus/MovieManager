package com.isa.pad.moviemanager.mediator;

import com.isa.pad.moviemanager.util.JsonSerializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/11/2017.
 */
public class MavenDetector implements Runnable {

    private MediatorConfig config;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private List<UdpResponse> udpResponses = Collections.synchronizedList(new ArrayList<>());
    private volatile Maven maven;

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
            logger.log(Level.SEVERE, "Socket closed!", ex);
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        detectMaven();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < config.getTimeout()) {
            final byte[] buf = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                udpResponses.add(JsonSerializer.fromJson(new String(packet.getData()), UdpResponse.class));
                logger.log(Level.INFO, "Nodes: " + udpResponses);
            } catch (Exception ex) {
                logger.log(Level.INFO, "Reached time out limit. Socket closed");
            }
        }
    }

    private void detectMaven() {
        Optional<Maven> maven = udpResponses.stream().sorted().map(Maven::new).findFirst();
        maven.ifPresent(m -> this.maven = m);
        if (!maven.isPresent())
            logger.log(Level.WARNING, "Maven not found.");
    }

    public Maven getMaven() {
        return maven;
    }

    public void setMaven(Maven maven) {
        this.maven = maven;
    }

    public MediatorConfig getConfig() {
        return config;
    }

    public void setConfig(MediatorConfig config) {
        this.config = config;
    }

    public List<UdpResponse> getUdpResponses() {
        return udpResponses;
    }

    public void setUdpResponses(List<UdpResponse> udpResponses) {
        this.udpResponses = udpResponses;
    }

}
