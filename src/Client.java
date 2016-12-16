import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

    private Socket client;
    private GridFrame grid;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean send = true;

    public ObjectInputStream getIn() {
        return in;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    private Client(String adresse, int port) throws IOException {
        this.client = new Socket(adresse, port);
    }

    public void sendMsg(Object msg) throws IOException {
        out.writeObject(msg);
        out.flush();
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    private void run() throws UnsupportedLookAndFeelException, IOException, ClassNotFoundException, InterruptedException {
        System.out.println("RUN CLIENT");


        this.out = new ObjectOutputStream(this.client.getOutputStream());
        out.flush();

        this.in = new ObjectInputStream(this.client.getInputStream());

        grid = new GridFrame(this, (int) in.readObject());

        waitServer();

        waitPlay();
    }


    public void waitServer() throws IOException {
        /*ObjectInputStream in = new ObjectInputStream(this.client.getInputStream());*/
        while (true) {
            System.out.println("EN ATTENTE DU SERVER");
            Object o = null;
            try {
                o = this.in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            //System.out.println(o);

            if (this.send) {
                if (((boolean) o)) {
                    this.send = false;
                }
            } else {
                if (o instanceof Grid) {
                    this.grid.setGrid((Grid) o);
                    break;
                }
            }

        }
    }

    public void waitPlay() throws IOException, ClassNotFoundException {

        while (true) {
            Object o = this.in.readObject();
            //System.out.println(o);

            if (o.equals("lock")) {
                this.grid.lockGrid();
            } else if (o.equals("unlock")) {
                this.grid.unLockGrid();
            } else if (o.equals("win")) {
                this.grid.win();
            } else if (o.equals("loose")) {
                this.grid.loose();
            } else if (o.equals("end")) {
                this.grid.lockGrid();
            }

            if (o instanceof Object[]) {
                if (((Object[]) o)[0].equals("YES")) {
                    this.grid.setTextBoxYes((Box) ((Object[]) o)[1]);
                } else if (((Object[]) o)[0].equals("NO")) {
                    this.grid.setTextBoxNo((Box) ((Object[]) o)[1]);
                }

            }
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
        try {
            Client client = new Client("localhost", 3333);
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}
