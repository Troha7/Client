package ua.hillelit.lms;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * {@link FileTransmitter} is a class which send file to server.
 *
 * @author Dmytro Trotsenko on 26.12.2022
 */
public class FileTransmitter {
  private final Socket clientSocket;
  private DataOutputStream fileWriter;

  public FileTransmitter(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  /**
   * Transmit the file to server.
   * First we find file on the path {@code path},
   * then send file size to the server.
   * Create a new buffer of size 4 Kbs.
   * Send the file of 4 Kbs in a loop.
   *
   * @param path path to file
   */
  public void writeFile(String path){
    try {
      fileWriter = new DataOutputStream(clientSocket.getOutputStream());

      int bytes;
      File file = new File(path);
      FileInputStream fileIn = new FileInputStream(file);

      // send file size
      fileWriter.writeLong(file.length());
      System.out.println("[Client] <<< file size[" + file.length() +"]");

      byte[] buffer = new byte[4 * 1024];
      while ((bytes = fileIn.read(buffer)) != -1) {
        fileWriter.write(buffer, 0, bytes);
        fileWriter.flush();
      }
      fileIn.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Close DataOutputStream
   */
  public void close(){
    try {
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
