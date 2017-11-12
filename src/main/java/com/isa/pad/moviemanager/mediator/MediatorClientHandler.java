package com.isa.pad.moviemanager.mediator;

import com.isa.pad.moviemanager.util.JsonSerializer;
import com.isa.pad.moviemanager.util.TcpResponse;
import com.isa.pad.moviemanager.util.XmlSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/12/2017.
 */
public class MediatorClientHandler implements Runnable {

    private final Socket clientSocket;
    private Socket mavenSocket;
    private final Maven maven;
    private BufferedReader clientBufferedReader;
    private PrintWriter clientPrintWriter;
    private BufferedReader mavenBufferedReader;
    private PrintWriter mavenPrintWriter;
    private Logger logger = Logger.getLogger(MediatorClientHandler.class.getName());

    public MediatorClientHandler(Socket clientSocket, Maven maven) {
        this.clientSocket = clientSocket;
        this.maven = maven;
        try {
            this.clientBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.clientPrintWriter = new PrintWriter(clientSocket.getOutputStream());
            this.mavenSocket = new Socket(InetAddress.getByName(maven.getAddress()), maven.getPort());
            this.mavenBufferedReader = new BufferedReader(new InputStreamReader(mavenSocket.getInputStream()));
            this.mavenPrintWriter = new PrintWriter(mavenSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String jsonSerializedClientRequest = read(clientBufferedReader);
        logger.log(Level.INFO, "Got client request in JSON. Request: {0}", jsonSerializedClientRequest);
        write(jsonSerializedClientRequest, mavenPrintWriter);
        String mavenJsonSerializedResponse = read(mavenBufferedReader);
        logger.log(Level.INFO, "Got maven response in JSON. Response: {0}", mavenJsonSerializedResponse);
        TcpResponse tcpResponse = JsonSerializer.fromJson(mavenJsonSerializedResponse, TcpResponse.class);
        String mavenXmlSerializedResponse = XmlSerializer.toXml(tcpResponse, TcpResponse.class);
        write(mavenXmlSerializedResponse, clientPrintWriter);
        logger.log(Level.INFO, "Maven response converted in XML sent. Response: {0}", mavenXmlSerializedResponse);
    }

    private String read(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = readLine(bufferedReader)) != null && line.trim().length() > 0){
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    private String readLine(BufferedReader bufferedReader) {
        String s = "";
        try {
            s = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private void write(String data, PrintWriter printWriter) {
        printWriter.println(data);
        printWriter.println();
        printWriter.flush();
    }
}
