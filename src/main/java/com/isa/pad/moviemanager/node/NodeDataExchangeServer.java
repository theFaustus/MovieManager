package com.isa.pad.moviemanager.node;

import com.isa.pad.moviemanager.model.MovieDataSource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/12/2017.
 */
public class NodeDataExchangeServer implements Runnable {
    private NodeConfig nodeConfig;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private Logger logger = Logger.getLogger(NodeDataExchangeServer.class.getName());
    private String nodeName;

    public NodeDataExchangeServer(NodeConfig nodeConfig, ExecutorService executorService, String nodeName) {
        this.nodeConfig = nodeConfig;
        this.executorService = executorService;
        try {
            this.serverSocket = new ServerSocket(MovieDataSource.INSTANCE.getNodePortFor(nodeName));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException", e);
        }
        this.nodeName = nodeName;
        this.executorService.submit(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket nodeClientSocket = serverSocket.accept();
                NodeClientHandler nodeClientHandler = new NodeClientHandler(nodeClientSocket, nodeName);
                executorService.submit(nodeClientHandler);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception", e);
        }
    }
}
