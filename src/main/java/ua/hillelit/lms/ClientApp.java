package ua.hillelit.lms;

/**
 * @author Dmytro Trotsenko on 13.12.2022
 */
public class ClientApp {

  public static void main(String[] args) {
    MyClient client = new MyClient();
    client.startConnection("localhost",8080);
    client.sendMessage("Hi");
    client.sendMessage("file file1.txt");
    client.sendMessage("exit");
    //client.stopConnection();
  }

}
