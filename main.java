/**
 * Created by Farhan on 16/08/2016.
 */
public class main {
    public static void main(String args[]){
        System.out.println("Starting server");
        server s = new server(8080);
        new Thread(s).start();
    }
}
