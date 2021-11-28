import Utils.Waiting;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZapAuto {

    private static final String ZAP_PROXYHOST = "localhost";
    private static final int ZAP_PROXYPORT = 8050;
    private static final String URl = "https://defendtheweb.net";

    public static final Logger Log = LogManager.getLogger(ZapAuto.class);


    private static WebDriver driver;

    ClientApi zapApp = new ClientApi(ZAP_PROXYHOST, ZAP_PROXYPORT);

    protected Proxy apiZapSetup() {

        try {
            Proxy seleniumProxy = new Proxy();
            seleniumProxy.setProxyAutoconfigUrl("http://" + ZAP_PROXYHOST + ":" + ZAP_PROXYPORT);
            zapApp.ascan.removeAllScans();
            zapApp.core.newSession("","");
            zapApp.spider.scan(URl,null, null,null,null);
            return seleniumProxy;
        }catch (Exception e){
            Log.info(e);
            return null;
        }
    }

    public FirefoxOptions firefoxSetupConf() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions fireOptions = new FirefoxOptions();
        fireOptions.setCapability(CapabilityType.PROXY, apiZapSetup());
        fireOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        fireOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return fireOptions;
    }


    protected void scanningRealTime() throws ClientApiException {
        System.out.println("-- Waiting for passive scan to complete --");
        try {
            zapApp.pscan.enableAllScanners(); // enable passive scanner.

            ApiResponse response = zapApp.pscan.recordsToScan(); // getting a response

            //iterating till we get response as "0".
            while(!response.toString().equals("0")) {
                response =	zapApp.pscan.recordsToScan();
            }
        } catch (ClientApiException e1) {
            e1.printStackTrace();
        }
        System.out.println("--- Passive scan completed! ---");

        zapApp.ascan.scan(URl, "true","false",null,null, null);

        System.out.println("-- Waiting for scan progress to complete --");

        ApiResponse res = zapApp.ascan.scanProgress("asdad");
        System.out.println(res);

        /*TODO

            hay que encontrar la forma para que podamos ver el progreso del escaneo, e intentar
            hacer que el el scrip termine cuando se

         */
        //zapApp.ascan.scanProgress();

        //System.out.println(scanpro);
        System.out.println("--- Scan Progress completed! ---");

    }



    protected void scanningURL() throws ClientApiException {
        driver = new FirefoxDriver(firefoxSetupConf());
        driver.get(URl);
        scanningRealTime();
        Waiting.time(5000);
    }

    protected void RemoveAndCleanTheSession() throws ClientApiException {
        zapApp.ascan.removeAllScans();
        zapApp.core.newSession("","");
    }

    protected void ZapReporting() throws ClientApiException, IOException {
        String report = new String(zapApp.core.htmlreport());
        Path fileReportPath = Paths.get(System.getProperty("user.dir") + "/scanZAPAuto/nombre-del-reporte.html");
        Files.deleteIfExists(fileReportPath);
        Files.write(fileReportPath, report.getBytes());
        RemoveAndCleanTheSession();
    }

    protected void runningZap() throws ClientApiException, IOException {
        scanningURL();
        ZapReporting();
        driver.quit();
    }

}
