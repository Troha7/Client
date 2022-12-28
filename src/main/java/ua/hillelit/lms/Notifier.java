package ua.hillelit.lms;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * {@link Notifier} is a class which send the message and file to the server.
 *
 * @author Dmytro Trotsenko on 26.12.2022
 */
public class Notifier {
  private final Socket clientSocket;
  private PrintWriter textWriter;
  private FileTransmitter fileTransmitter;

  public Notifier(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  /**
   * Sends message or file to the client
   *
   * @param message text message
   */
  public void write(String message) {
    try {
      textWriter = new PrintWriter(clientSocket.getOutputStream(), true);

      System.out.println("[Client] <<< " + message);
      textWriter.println(message);
      sendFile(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Parse the text message with path to file
   * and transmit file at the class {@link FileTransmitter}.
   *
   * @param message text message mast contain command {@code "-file"},
   *                path to file and file name
   */
  private void sendFile(String message) {
    String path;
    if (message.startsWith("-file")) {
      path = message.replaceFirst("-file", "").trim();
      fileTransmitter = new FileTransmitter(clientSocket);
      fileTransmitter.writeFile(path);
    }
  }

  /**
   * Close PrintWriter and DataOutputStream
   */
  public void close(){
    textWriter.close();
    fileTransmitter.close();
  }

}
