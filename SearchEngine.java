import java.io.IOException;
import java.net.URI;
import java.io.*;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int arrayCounter = 0;
    int storage = 1;
    String[] strArray = new String[storage];
    String[] containsArray = new String[storage];
    String[] temp = new String[storage];

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return ("Strings stored: " + (Arrays.toString(strArray)));
        } 
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i = 0 ; i < strArray.length ; i++) {
                        if (strArray[i].contains(parameters[1])) {
                            containsArray[i] = strArray[i];
                        }
                    }
                }
            return ("Found: " + (Arrays.toString(containsArray)));
            } 
            else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if (arrayCounter == strArray.length) {
                        for (int i = 0; i < strArray.length; i++) {
                            temp[i] = strArray[i];
                        }
                        storage++;
                        strArray = new String[storage];
                        for (int i = 0; i < temp.length; i++) {
                            strArray[i] = temp[i];
                        }
                    }
                    strArray[arrayCounter] = parameters[1];
                    arrayCounter++;
                    return (parameters[1] + " stored! Strings stored: " + (Arrays.toString(strArray)));
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}