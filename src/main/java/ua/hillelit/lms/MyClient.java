package ua.hillelit.lms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Dmytro Trotsenko on 12.12.2022
 */
public class MyClient {

  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  public void startConnection(String ip, int port) {
    try {
      clientSocket = new Socket(ip, port);
      System.out.println("CLIENT is started at socket[" + port + "]");
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      Thread thread = new Thread(this::readFromServer);
      thread.start();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void readFromServer() {
    String inputLine;
    while (true){
      try {
        if ((inputLine = in.readLine()) == null) {
          break;
        }
        System.out.println(inputLine);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void sendMessage(String msg) {
    try {
      out.println(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stopConnection() {
    try {
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
