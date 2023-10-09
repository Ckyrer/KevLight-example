import ru.kvdl.kevlight.DHOperator;
import ru.kvdl.kevlight.Responser;
import ru.kvdl.kevlight.Server;
import ru.kvdl.kevlight.annotations.KL404Handler;
import ru.kvdl.kevlight.annotations.KLRequestHandler;

import java.io.*;

public class Main {
    public static String PATH = new File("").getAbsolutePath()+"/resources/";

    public static void main(String[] args) {
        Server server = new Server(new Main(),5050);
        server.start();
    }

    @KLRequestHandler(request = "")
    public void homePage(Responser resp) {
        resp.sendString(DHOperator.buildPage(PATH+"/pages/home"));
    }

    @KLRequestHandler(request = "media", startsWith = true)
    public void onMedia(Responser resp) throws FileNotFoundException {
        File file = new File(PATH+"media/"+resp.getRequest().substring(6));
        if (file.exists() || file.isFile()) {
            resp.sendResponse(
                    new FileInputStream(file),
                    "200 OK",
                    new String[] {"Content-Type: image/png"},
                    2048
            );
        } else {
            resp.send404Response();
        }
    }

//    @KL404Handler
    @KLRequestHandler(request = "404")
    public void on404(Responser resp) {
        resp.sendString("Error 404 :(");
    }

}