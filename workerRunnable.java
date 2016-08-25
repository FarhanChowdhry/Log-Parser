import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Farhan on 16/08/2016.
 */
public class workerRunnable implements Runnable {

    protected Socket clientSocket;
    protected boolean heartbeat = false;
    protected boolean alive = true;
    protected String message = "";

    public workerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            System.out.println("Now listening");
            getParam();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getParam() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Scanner sc = new Scanner(in.readLine());
        sc.next();
        String[] array = (sc.next()).split("=");
        if(!array[0].equals("/?type")){
            System.out.println("Invalid Param");
            sendMessage("Invalid Parameters");
            return;
        }
        String var = array[1];
        if(!(var.equals("all") || var.equals("ERROR") || var.equals("WARN") || var.equals("both"))) {
            sendMessage("Invalid Parameters");
            return;
        }

        Parser parser = new Parser(var);
        sendMessage(parser.display);
    }


    private void sendMessage(String message) throws IOException{
        OutputStream out = clientSocket.getOutputStream();
        out.write(("HTTP/1.1 200 \r\n Content-Type: text/plain\r\n Connection:Keep-Alive \r\n\r\n" + message).getBytes());
        out.flush();
        clientSocket.shutdownOutput();
    }

}
