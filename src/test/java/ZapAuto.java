import Utils.Waiting;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dani "Bolombo" Bonilla
 */

public class ZapAuto {

    private static final String ZAP_PROXYHOST = "localhost";
    private static final int ZAP_PROXYPORT = 8050;
    //private static final String URl = "https://preproduccio.govern.cat/president";
    private static final String URl = "https://defendtheweb.net/";

    public static final Logger Log = LogManager.getLogger(ZapAuto.class);
    private boolean debug = false;

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



    protected void scanningRealTime() throws Exception {
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
        System.out.println("-- Waiting for scan progress to complete --");
        zapApp.ascan.scan(URl, "true","false",null,null, null);
        zapApp.activeScanSiteInScope(URl);
        System.out.println("--- Scan Progress completed! ---");
    }


    protected void scanningURL() throws Exception {
        BrowserFactory browserConfig = new BrowserFactory();
        driver = (WebDriver) browserConfig.BrowserSetupOptionsDriver(apiZapSetup(),true,true);
        Waiting.time(5000);
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

    protected void runningZap() throws Exception {
        scanningURL();
        ZapReporting();
        driver.quit();
    }

}
