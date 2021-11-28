import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class zapRun {

    public static void main(String args[]) throws Exception{
        ZapAuto ZapNew = new ZapAuto();
        ZapNew.runningZap();

    }


    /*
    @Test(groups = {"wip" }, testName = "Go to calendar zone",
            description = "Clicking on calendar from community page")
    public void RunningZap() throws ClientApiException, IOException {
        ZapAuto ZapNew = new ZapAuto();
        ZapNew.runningZap();
    }*/
}
