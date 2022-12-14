package ua.hillelit.lms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
  private static DataOutputStream fileWriter;

  public void startConnection(String ip, int port) {
    try {
      clientSocket = new Socket(ip, port);
      System.out.println("CLIENT is started at socket[" + port + "]");
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      fileWriter = new DataOutputStream(clientSocket.getOutputStream());

      Thread thread = new Thread(this::readFromServer);
      thread.start();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void readFromServer() {
    String inputLine;
    while (true) {
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
      String path;
      if (msg.startsWith("file")) {
        path = msg.replaceFirst("file", "").trim();
        sendFile(path);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendFile(String path) throws Exception {
    int bytes;
    File file = new File(path);
    FileInputStream fileIn = new FileInputStream(file);

    // send file size
    fileWriter.writeLong(file.length());
    // break file into chunks
    byte[] buffer = new byte[4 * 1024];
    while ((bytes = fileIn.read(buffer)) != -1) {
      fileWriter.write(buffer, 0, bytes);
      fileWriter.flush();
    }
    fileIn.close();
  }

  public void stopConnection() {
    try {
      fileWriter.close();
      in.close();
      out.close();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
