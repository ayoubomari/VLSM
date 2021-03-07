package VLSM;
import java.util.Scanner;


public class Network {
    private static Scanner scanner = new Scanner(System.in);
    private static String networkInfo;
    private static String cls;
    private static int mask;

    public static void Network() {
        remplir();
    }

    public static void remplir() {//Network information
        System.out.println("\n\n\n");
        System.out.println("Press enter to return.");
        System.out.print("Enter your network adress like (192,168,10,0): ");
        networkInfo = scanner.nextLine();
        if (networkInfo.isEmpty()) {
            System.out.println("==================================================\n\n\n\n");
            Main.globalControler();
        } else {
            String[] bytes = networkInfo.split(",", 4);
            if (bytes.length == 4) {
                int byte0 = Integer.parseInt(bytes[0]);
                int byte1 = Integer.parseInt(bytes[1]);
                int byte2 = Integer.parseInt(bytes[2]);
                int byte3 = Integer.parseInt(bytes[3]);


                if (byte0 >= 128 && byte0 < 224 && byte1 >= 0 && byte1 <= 255 && byte2 >= 0 && byte2 <= 255 && byte3 >= 0 && byte3 <= 255) {
                    if (byte0 >= 128 && byte0 < 192) {
                        cls = "b";
                    } else {
                        cls = "c";
                    }
                    System.out.print("Enter the subnet mask: ");
                    mask = scanner.nextInt();
                    if (cls == "b" && mask >= 16 && mask <= 32) {
                        subnetB(byte0, byte1, byte2, byte3, mask);
                    } else if (cls == "c" && mask >= 24 && mask <= 32) {
                        subnetC(byte0, byte1, byte2, byte3, mask);
                    } else {
                        System.out.println("Incorect input!");
                    }
                }else{
                    System.out.println("Incorect input!");
                    Network.remplir();
                }
            } else {
                System.out.println("Incorect input!");
                Network.remplir();
            }
        }
    }


    private static void subnetB(int byte0, int byte1, int byte2, int byte3, int mask) {
        int[] defaultHost = {65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};

        int subnet;
        int allHost = 0;
        System.out.print("Enter the number of subnets: ");
        subnet = scanner.nextInt();
        if (subnet >= 1 && subnet <= 65536) {
            int[] arrMask = new int[subnet];
            int[] arrHost = new int[subnet];
            for (int i = 0; i < subnet; i++) { //enter hosts list
                System.out.print("Subnet n°" + i + " => Enter the number of hosts: ");
                arrHost[i] = scanner.nextInt();

                allHost += arrHost[i] + 2;
                if (allHost > defaultHost[mask - 16] || arrHost[i] < 0) {
                    System.out.println("impossible!");
                    return;
                }
            }
            for (int i = 0; i < arrHost.length; i++) {
                arrMask[i] = getMaskB(arrHost[i] );
            }

            String[] IPAdress = new String[subnet];
            String[] firsIP = new String[subnet];;
            String[] lastIP = new String[subnet];;
            String[] IPBrodcast = new String[subnet];;

            for (int i = 0; i < subnet; i++) {
                if(arrMask[i] >= 24){
                    IPAdress[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(byte3);if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    if(byte3 + 1 > 255){
                        byte3 = byte3 + 1;
                        byte3 -= 256;
                        byte2 += 1;
                        firsIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)(byte3 + 1));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                        byte3 = byte3 - 1;
                    }else{
                        firsIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)(byte3 + 1));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    }
                    if(((int)byte3 + Math.pow(2, 32 - arrMask[i]) - 1) > 255){
                        byte3 += Math.pow(2, 32 - (int)arrMask[i]) - 1;
                        byte3 -= 256;
                        byte2 += 1;
                        IPBrodcast[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(byte3);if(byte2 > 255){ System.out.println("Impossible!"); return;}
                        byte3 -= Math.pow(2, 32 - (int)arrMask[i]) - 1;
                        byte3 += 256;
                        byte2 -= 1;
                    }else{
                        IPBrodcast[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(((int)byte3 + (int)Math.pow(2, 32 - arrMask[i]) - 1));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    }
                    if(((int)byte3 + Math.pow(2, 32 - arrMask[i]) - 2) > 255){
                        byte3 += Math.pow(2, 32 - (int)arrMask[i]) - 2;
                        byte3 -= 256;
                        byte2 += 1;
                        lastIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(byte3);if(byte2 > 255){ System.out.println("Impossible!"); return;}
                        byte3 -= Math.pow(2, 32 - (int)arrMask[i]) - 2;
                        byte3 += 256;
                        byte2 -= 1;
                    }else{
                        lastIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(((int)byte3 + (int)Math.pow(2, 32 - arrMask[i]) - 2));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    }
                    byte3 += Math.pow(2, 32 - arrMask[i]);
                    if(byte3 > 255){
                        byte3 -= 256;
                        byte2 += 1;
                    }
                }else{// less then /24.
                    IPAdress[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(byte3);if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    if(byte3 + 1 > 255){
                        firsIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)((byte3 - 256) + 1));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    }else{
                        firsIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)(byte3 + 1));if(byte2 > 255){ System.out.println("Impossible!"); return;}
                    }
                    if(byte3 + 255 > 255){
                        byte3 += 255;
                        byte3 -= 256;
                        byte2 += 1;
                        IPBrodcast[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1) + "." + threeIt(byte3);if((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1 > 255){ System.out.println("Impossible!"); return;}
                        byte3 -= 255;
                        byte3 += 256;
                        byte2 -= 1;
                    }else{
                        IPBrodcast[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1) + "." + threeIt(byte3+ 255);if((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1 > 255){ System.out.println("Impossible!"); return;}
                    }
                    if(byte3 + 254 > 255){
                        byte3 += 254;
                        byte3 -= 256;
                        byte2 += 1;
                        lastIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1) + "." + threeIt(byte3);if((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1 > 255){ System.out.println("Impossible!"); return;}
                        byte3 -= 254;
                        byte3 += 256;
                        byte2 -= 1;
                    }else{
                        lastIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1) + "." + threeIt(byte3+ 254);if((int)((int)byte2 + (int)Math.pow(2, 32 - (int)arrMask[i] - 8)) - 1 > 255){ System.out.println("Impossible!"); return;}
                    }
                    byte2 += Math.pow(2, 32 - arrMask[i] - 8);
                }
            }

            //print final result================================================
            displayTable(subnet, arrMask, IPAdress, firsIP, lastIP, IPBrodcast);

        } else {
            System.out.println("Incorect input!");
        }
    }

    private static void subnetC(int byte0, int byte1, int byte2, int byte3, int mask) {
        int[] defaultHost = {256, 128, 64, 32, 16, 8, 4, 2, 1};

        int subnet;
        int allHost = 0;
        System.out.print("Enter the number of subnets: ");
        subnet = scanner.nextInt();
        if (subnet >= 1 && subnet <= 256) {
            int[] arrMask = new int[subnet];
            int[] arrHost = new int[subnet];
            for (int i = 0; i < subnet; i++) { //enter hosts list
                System.out.print("Subnet n°" + i + " => Enter the number of hosts: ");
                arrHost[i] = scanner.nextInt();

                allHost += arrHost[i] + 2;
                if (allHost > defaultHost[mask - 24] || arrHost[i] < 0) {
                    System.out.println("Impossible!");
                    return;
                }
            }
            for (int i = 0; i < arrHost.length; i++) {
                arrMask[i] = getMaskC(arrHost[i]);
            }

            String[] IPAdress = new String[subnet];
            String[] firsIP = new String[subnet];;
            String[] lastIP = new String[subnet];;
            String[] IPBrodcast = new String[subnet];;

            for (int i = 0; i < subnet; i++) {
                IPAdress[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt(byte3);if(byte3 > 255){ System.out.println("Impossible!"); return;}
                firsIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)(byte3 + 1));if(byte3 + 1 > 255){ System.out.println("Impossible!"); return;}
                IPBrodcast[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)((int)byte3 + (int)Math.pow(2, 32 - (int)arrMask[i]) - 1));if((int)((int)byte3 + (int)Math.pow(2, 32 - (int)arrMask[i]) - 1) > 255){ System.out.println("Impossible!"); return;}
                lastIP[i] = threeIt(byte0) + "." + threeIt(byte1) + "." + threeIt(byte2) + "." + threeIt((int)((int)byte3 + (int)Math.pow(2, 32 - (int)arrMask[i]) - 2));if((int)((int)byte3 + (int)Math.pow(2, 32 - (int)arrMask[i]) - 2) > 255){ System.out.println("Impossible!"); return;}
                byte3 += Math.pow(2, 32 - arrMask[i]);
            }

            //print final result================================================
            displayTable(subnet, arrMask, IPAdress, firsIP, lastIP, IPBrodcast);
        } else {
            System.out.println("Incorect input!");
        }
    }





    private static int getMaskB(int hostNumber) {
        int[] defaultHost = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        int[] defaultMask = {32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16};

        int n = hostNumber + 2;
        int i = 0;
        for (; i < defaultHost.length; i++) {
            if (n <= defaultHost[i]) {
                return defaultMask[i];
            }
        }
        return defaultMask[i - 1];
    }


    private static int getMaskC(int hostNumber) {
        int[] defaultHost = {1, 2, 4, 8, 16, 32, 64, 128, 256};
        int[] defaultMask = {32,31,30,29,28,27,26,25,24};

        int n = hostNumber + 2;
        int i = 0;
        for (; i < defaultHost.length; i++) {
            if (n <= defaultHost[i]) {
                return defaultMask[i];
            }
        }
        return defaultMask[i - 1];
    }

    private static void displayTable(int subnet, int[] arrMask, String[] IPAdress, String[] firsIP, String[] lastIP, String[] IPBrodcast){
        System.out.println("*--------------------------------------------------------------------------------------*");
        System.out.println("| Name  | Mask |       @IP       |     First @     |     Last @      |    @Brodcast    |");
        for (int i = 0; i < subnet; i++) {
            System.out.println("|_______|______|_________________|_________________|_________________|_________________|");
            System.out.println("|       |      |                 |                 |                 |                 |");
            System.out.println("| " + fiveIT(i+1) + " | /" + arrMask[i] + "  | " + IPAdress[i] + " | " + firsIP[i] + " | " + lastIP[i] + " | " + IPBrodcast[i] + " |");
            System.out.println("|       |      |                 |                 |                 |                 |");
        }
        System.out.println("|_______|______|_________________|_________________|_________________|_________________|");
    }

    private static String threeIt(int n){
        String s = String.valueOf(n);
        for(;;){
            if(s.length() >= 3){
                break;
            }
            s = "0" + s;
        }
        return s;
    }
    private static String fiveIT(int n){
        String s = String.valueOf(n);
        for(;;){
            if(s.length() == 5){
                break;
            }
            s = "0" + s;
        }
        return s;
    }

}


