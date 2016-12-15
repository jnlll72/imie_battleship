import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    private ServerSocket server;

    private final int MAXCLIENT = 2;
    private Socket[] aryClient;

    private Grid[] listeGridClients = new Grid[2];

    private ObjectOutputStream[] obj = new ObjectOutputStream[MAXCLIENT];
    private ObjectInputStream[] objInt = new ObjectInputStream[MAXCLIENT];


    private Serveur(int port) {
        try {
            this.server = new ServerSocket(port);
            this.aryClient = new Socket[MAXCLIENT];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Object msg, ObjectOutputStream out) throws IOException {

        out.writeObject(msg);
        out.flush();
    }

    private void run() throws ClassNotFoundException {
        System.out.println("RUN SERVER");

        try {
            boolean add = true;
            int i = 0;

            //Attente des 2 clients
            while (true) {
                Socket sockClient = this.server.accept();
                this.aryClient[i] = sockClient;
                this.obj[i] = new ObjectOutputStream(sockClient.getOutputStream());
                this.objInt[i++] = new ObjectInputStream(sockClient.getInputStream());

                this.sendMsg(i, this.obj[i - 1]);

                if (i == MAXCLIENT) {
                    break;
                }
            }

            //Attente de la validation des clients apres verification de leur grille
            while (true) {
                int index = 0;
                for (Socket anAryClient : this.aryClient) {
                    Object reponse = this.objInt[index].readObject();
                    this.sendMsg(((Object[]) reponse)[0], obj[index]);
                    this.listeGridClients[index++] = ((Grid) ((Object[]) reponse)[1]);
                }
                break;
            }

            //Lancement du jeu avec echange de grille
            this.sendMsg(this.listeGridClients[1], this.obj[0]);
            this.sendMsg(this.listeGridClients[0], this.obj[1]);

            this.sendMsg("unlock", this.obj[0]);
            this.sendMsg("lock", this.obj[1]);

            Socket clientEnCours = this.aryClient[0];
            int index = 0;

            while (true) {

                Object reponse = this.objInt[index].readObject();
                //System.out.println(reponse);

                if (!((Object[]) reponse)[0].equals("END")) {
                    if (((Object[]) reponse)[0].equals("YES")) {
                        if (index == 0) {
                            this.sendMsg(reponse, this.obj[1]);
                        } else {
                            this.sendMsg(reponse, this.obj[0]);
                        }

                    } else {
                        if (((Object[]) reponse)[0].equals("NO")) {
                            if (index == 0) {
                                this.sendMsg("lock", this.obj[0]);
                                this.sendMsg("unlock", this.obj[1]);
                                index = 1;
                            } else {
                                this.sendMsg("lock", this.obj[1]);
                                this.sendMsg("unlock", this.obj[0]);
                                index = 0;
                            }
                        }
                        this.sendMsg(reponse, this.obj[index]);
                    }
                } else {
                    System.out.println(index);

                    this.sendMsg("win", this.obj[index]);

                    if (index == 0) {
                        this.sendMsg("loose", this.obj[1]);
                    } else {
                        this.sendMsg("loose", this.obj[0]);
                    }

                    /*this.sendMsg("win",);
                    this.sendMsg("loose",);*/
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Serveur serveur = new Serveur(3333);
        serveur.run();
    }
}
