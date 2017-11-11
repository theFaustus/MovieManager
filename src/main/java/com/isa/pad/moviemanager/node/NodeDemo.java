/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.pad.moviemanager.node;

import com.isa.pad.moviemanager.mediator.MediatorDemo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faust
 */
public class NodeDemo {

    private static Logger logger = Logger.getLogger(MediatorDemo.class.getName());

    public static void main(String[] args) {
        NodeServer nodeServer = new NodeServer(new NodeConfig());
        nodeServer.start();
        logger.log(Level.INFO, "Node is up...");
    }
}
