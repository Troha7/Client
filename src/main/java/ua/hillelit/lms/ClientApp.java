package ua.hillelit.lms;

/**
 * {@link ClientApp} is a main class.
 *
 * @author Dmytro Trotsenko on 26.12.2022
 */
public class ClientApp {

  public static void main(String[] args) {
    Client client = new Client();
    client.startConnection("localhost",8080);
    client.sendMessage("Hi");
    client.sendMessage("-file src/main/resources/file1.txt");
    client.sendMessage("-exit");
    //client.stop();
  }

}
