package VLSM;

import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main{

    public static void main(String[] args) {
        Main.welcom();
        Main.globalControler();
    }


    public static void welcom() {
        System.out.print("\n" +
                "\t\t\t\t _       __     __                   \n" +
                "\t\t\t\t| |     / ___  / _________  ____ ___ \n" +
                "\t\t\t\t| | /| / / _ \\/ / ___/ __ \\/ __ `__ \\\n" +
                "\t\t\t\t| |/ |/ /  __/ / /__/ /_/ / / / / / /\n" +
                "\t\t\t\t|__/|__/\\___/_/\\___/\\____/_/ /_/ /_/ \n" +
                "\t\t\t\t                                     \n");

        System.out.println("" +
                "  ______         _    ____   _____ __  ___   _________    __________  ____    ___  __________  ____ \n" +
                " /_  ______     | |  / / /  / ___//  |/  /  / ____/   |  / / ____/ / / / /   /   |/_  __/ __ \\/ __ \\\n" +
                "  / / / __ \\    | | / / /   \\__ \\/ /|_/ /  / /   / /| | / / /   / / / / /   / /| | / / / / / / /_/ /\n" +
                " / / / /_/ /    | |/ / /______/ / /  / /  / /___/ ___ |/ / /___/ /_/ / /___/ ___ |/ / / /_/ / _, _/ \n" +
                "/_/  \\____/     |___/_____/____/_/  /_/   \\____/_/  |_/_/\\____/\\____/_____/_/  |_/_/  \\____/_/ |_|  \n" +
                "                                                                                                    \n");
    }



    //CONTROLERS
    public static void globalControler() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[1]: VLSM calculator\t\t[2]: What is VLSM");
        System.out.println("[3]: Go to our GitHub Page  \t[4]: exit");
        System.out.print("\n");

        System.out.print("Press a key To continue: ");
        int chois = scanner.nextInt();

        if(chois == 1){
            Network.Network();
        }
        else if(chois == 2){
            whatIsVLSM();

            System.out.print("Press any number key to return: ");
            chois = scanner.nextInt();
            System.out.println("==================================================\n\n\n\n");
            globalControler();
        }
        else if(chois == 3){
            goToGitHub();

            System.out.print("Press any number key to return: ");
            chois = scanner.nextInt();
            System.out.println("==================================================\n\n\n\n");
            globalControler();
        }
        else if(chois == 4){
            exit();
        }
        else{
            System.out.println("incorrect input!");
            System.out.println("==================================================\n\n\n\n");
            globalControler();
        }
        //after resolution

    }


    public static void whatIsVLSM() {
        Desktop d = Desktop.getDesktop();
        try{
            d.browse(new URI("https://www.orbit-computer-solutions.com/variable-length-subnet-mask-vlsm/#:~:text=Variable%20Length%20Subnet%20Masking%20%E2%80%93%20VLSM,way%2C%20means%20subnetting%20a%20subnet.&text=It%20can%20also%20be%20called%20a%20classless%20IP%20addressing."));
        }
        catch (URISyntaxException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }


    public static void goToGitHub() {
        Desktop d = Desktop.getDesktop();
        try{
            d.browse(new URI("https://github.com/ayoubomari"));
        }
        catch (URISyntaxException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }


    public static void exit() {
        System.exit(0);
    }
}
