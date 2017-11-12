package com.isa.pad.moviemanager.client;

import com.isa.pad.moviemanager.model.Movie;
import com.isa.pad.moviemanager.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/12/2017.
 */
public class Client {
    private ClientConfig clientConfig;
    private Socket socket;
    private Logger logger = Logger.getLogger(Client.class.getName());
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public Client(ClientConfig clientConfig) {

        this.clientConfig = clientConfig;
        try {
            this.socket = new Socket(InetAddress.getByName(clientConfig.getMediatorAddress()), clientConfig.getMediatorPort());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException", e);
        }

    }

    public List<Movie> sendRequest(String responseType, Request request) {
        String jsonSerializedRequest = JsonSerializer.toJson(request);
        write("Response type: " + responseType + "\n" + jsonSerializedRequest);
        logger.log(Level.INFO, "Request sent. Data: {0}", request);
        String mediatorSerializedResponse = read();
        if(responseType.endsWith(ResponseType.JSON_TYPE.getType())){
            TcpResponse tcpResponse = JsonSerializer.fromJson(mediatorSerializedResponse, TcpResponse.class);
            return tcpResponse.getMovies();
        } else {
            logger.log(Level.INFO, "Got mediator response in XML. Response: {0}", mediatorSerializedResponse);
            XmlValidator xmlValidator = new XmlValidator("tcp_response_schema.xsd", TcpResponse.class);
            if (xmlValidator.validate(mediatorSerializedResponse)) {
                TcpResponse tcpResponse = XmlSerializer.fromXml(mediatorSerializedResponse, TcpResponse.class);
                return tcpResponse.getMovies();
            }
            logger.log(Level.WARNING, "Invalid XML data.");
        }
        return new ArrayList<>();
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    private String read() {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = readLine()) != null && line.trim().length() > 0) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    private String readLine() {
        String s = "";
        try {
            s = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private void write(String data) {
        printWriter.println(data);
        printWriter.println();
        printWriter.flush();
    }
}
