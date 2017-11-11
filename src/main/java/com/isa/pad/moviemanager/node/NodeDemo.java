
package com.isa.pad.moviemanager.node;

import com.isa.pad.moviemanager.mediator.MediatorDemo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/11/2017.
 */
public class NodeDemo {

    private static Logger logger = Logger.getLogger(MediatorDemo.class.getName());

    public static void main(String[] args) {
//        if (args.length < 1) {
//            logger.log(Level.SEVERE, "No name was provided!");
//            System.exit(0);
//        }
        Node node = new Node(new NodeConfig(), NodeNames.FMOVIES.name());
        node.start();
        logger.log(Level.INFO, "Node " + node.getNodeName() + " is up...");
    }
}
