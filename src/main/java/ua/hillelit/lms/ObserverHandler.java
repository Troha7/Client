package ua.hillelit.lms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * {@link ObserverHandler} is a class which starts the new thread
 * for receive message from the server.
 *
 * @author Dmytro Trotsenko on 26.12.2022
 */
public class ObserverHandler extends Thread {
  private final Socket clientSocket;
  private BufferedReader textReader;
  public ObserverHandler(Socket socket){
    this.clientSocket = socket;
  }

  /**
   * Run the new thread for receive message from the server.
   */
  @Override
  public void run() {
    try {
      textReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      readFromServer();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Read text message from the server and print to console.
   */
  private void readFromServer() {
    String inputLine;
    while (true) {
      try {
        if ((inputLine = textReader.readLine()) == null) {
          break;
        }
        System.out.println(inputLine);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
