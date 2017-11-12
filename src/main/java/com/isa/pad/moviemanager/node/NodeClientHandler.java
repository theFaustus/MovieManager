package com.isa.pad.moviemanager.node;

import com.isa.pad.moviemanager.model.Movie;
import com.isa.pad.moviemanager.model.MovieDataSource;
import com.isa.pad.moviemanager.util.JsonSerializer;
import com.isa.pad.moviemanager.util.TcpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Faust on 11/12/2017.
 */
public class NodeClientHandler implements Runnable{
    private final Socket nodeClientSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String nodeName;
    private Logger logger = Logger.getLogger(NodeClientHandler.class.getName());

    public NodeClientHandler(Socket nodeClientSocket, String nodeName) {
        this.nodeClientSocket = nodeClientSocket;
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(nodeClientSocket.getInputStream()));
            this.printWriter = new PrintWriter(nodeClientSocket.getOutputStream());
            this.nodeName = nodeName;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    @Override
    public void run() {
        String mediatorRequest = read(bufferedReader);
        List<Movie> nodeDataList = new ArrayList<>();
        for(URI u: MovieDataSource.INSTANCE.getConnectionsFor(nodeName)){
            try {
                Socket s = new Socket(InetAddress.getByName(u.getHost()), u.getPort());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());
                write(mediatorRequest, printWriter);
                String nodeResponse = read(bufferedReader);
                TcpResponse tcpResponse = JsonSerializer.fromJson(nodeResponse, TcpResponse.class);
                nodeDataList.addAll(tcpResponse.getMovies());
                bufferedReader.close();
                printWriter.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Can't send request to peer node", e);
            }
        }
        TcpResponse tcpResponse = new TcpResponse(nodeDataList);
        tcpResponse.getMovies().addAll(MovieDataSource.INSTANCE.getNodeDataListFor(nodeName));
        write(JsonSerializer.toJson(tcpResponse), printWriter);
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
