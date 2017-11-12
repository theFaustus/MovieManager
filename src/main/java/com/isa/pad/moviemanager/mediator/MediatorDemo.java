
package com.isa.pad.moviemanager.mediator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/11/2017.
 */
public class MediatorDemo {

    private static Logger logger = Logger.getLogger(MediatorDemo.class.getName());

    public static void main(String[] args) {
        Mediator mediator = new Mediator(new MediatorConfig());
    }
}
