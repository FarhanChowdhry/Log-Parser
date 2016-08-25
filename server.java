import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Farhan on 16/08/2016.
 */
public class server implements Runnable{

    protected int port = 9000;
    protected Socket clientSocket = null;
    protected ServerSocket server = null;
    protected Thread runningThread = null;


    public server(int port){
        this.port = port;
    }

    public void run(){
        synchronized (this){
            this.runningThread = Thread.currentThread();
        }

        try {
            runServer();

            for(;;){
                clientSocket = server.accept();
                System.out.println("new Connection");
                new Thread(new workerRunnable(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void runServer() throws IOException{
        server = new ServerSocket(port, 10);
    }
}
