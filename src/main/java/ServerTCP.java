import java.io.*;
import java.time.*;
import java.net.*;
import java.time.temporal.TemporalUnit;

//Single thread server
public class ServerTCP {

    protected static BufferedReader out_stream;
    protected static PrintWriter in_stream;
    public static void main(String[] args) throws IOException {
        //param check
        paramCheck(args);
        final int PORT = Integer.parseInt(args[0]);
        ServerSocket socket= new ServerSocket(PORT);
        System.out.println(printLog(LocalDateTime.now())+"Opening port "+PORT);
        System.out.println(printLog(LocalDateTime.now())+"Server up");
        while(true){
            Socket client_socket = socket.accept();
            System.out.println(printLog(LocalDateTime.now())+"connection accepted from "+client_socket.getInetAddress()+":"+client_socket.getPort());
            //till connection up
            echoing(client_socket);
        }

    }

    public static void paramCheck(String [] args){
        int len = args.length;
        if(len == 0) {
            System.out.println(printLog(LocalDateTime.now())+"missing param : port");
            System.exit(1);
        }
    }

    public static String printLog(LocalDateTime datetime){
    return "["+datetime.toString().substring(0,19)+"]\t";
    }

    public static void echoing(Socket cli) throws IOException {
        //initialize stream
        out_stream = new BufferedReader(new InputStreamReader(cli.getInputStream()));
        in_stream = new PrintWriter(cli.getOutputStream());

        while (true){
            String msg = out_stream.readLine();
            System.out.println(printLog(LocalDateTime.now())+"client"+cli.getInetAddress()+">"+msg);
            in_stream.print(msg+"\n");
            if(msg == null){
                System.out.println(printLog(LocalDateTime.now())+"bye "+cli.getInetAddress());
                break;
            }

            in_stream.flush();
        }
        closing();
    }

    public static void closing() throws IOException {
        in_stream.close();
        in_stream = null;
        out_stream.close();
        out_stream = null;
    }

}
