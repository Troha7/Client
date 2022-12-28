package ua.hillelit.lms;

import java.io.IOException;
import java.net.Socket;

/**
 * {@link Client} is a class simple client,
 * which has the ability to transfer message and files to the server.
 * And can receive message from the server.
 *
 * @author Dmytro Trotsenko on 26.12.2022
 */
public class Client {

  private Socket clientSocket;
  private Notifier notifier;
  private ObserverHandler observerHandler;

  /**
   * Start connection to server.
   *
   * @param ip ip address
   * @param port port server socket
   */
  public void startConnection(String ip, int port) {
    try {
      clientSocket = new Socket(ip, port);
      System.out.println("CLIENT is connected to socket[" + port + "]");
      notifier = new Notifier(clientSocket);

      // Create and start new thread for receive the server message
      observerHandler = new ObserverHandler(clientSocket);
      observerHandler.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a message to the client.
   *
   * @param message text message
   */
  public void sendMessage(String message) {
    notifier.write(message);
  }

  /**
   * Stop the client
   */
  public void stop() {
    try {
      clientSocket.close();
      System.out.println("CLIENT is stopped");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
