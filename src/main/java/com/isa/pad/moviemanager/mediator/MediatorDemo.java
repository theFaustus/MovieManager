/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.pad.moviemanager.mediator;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faust
 */
public class MediatorDemo {

    private static Logger logger = Logger.getLogger(MediatorDemo.class.getName());

    public static void main(String[] args) {
        MavenDetector mavenDetector = new MavenDetector(new MediatorConfig());
        mavenDetector.discoverNodes();
        logger.log(Level.INFO, "Nodes: {0}", mavenDetector.getNodes().toString());
        System.out.println(Arrays.toString(mavenDetector.getNodes().toArray()));
    }
}
