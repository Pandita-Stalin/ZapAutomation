package com.capge.pack;

import browserFactory.BrowserFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import tools.Waiting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;

/**
 * @author Dani "Bolombo" Bonilla
 */

public class ZapAuto {

    private static final String ZAP_PROXYHOST = "localhost";
    private static final int ZAP_PROXYPORT = 8090;
    //private static final String URl = "https://www.arthemis.tech";
    private static final String URl = "https://defendtheweb.net/";
    public static final Logger Log = LogManager.getLogger(ZapAuto.class);
    private static WebDriver driver;

    ClientApi zapApp = new ClientApi(ZAP_PROXYHOST, ZAP_PROXYPORT);

    protected Proxy apiZapSetup() {

        try {
            Proxy seleniumProxy = new Proxy();
            seleniumProxy.setProxyAutoconfigUrl("http://" + ZAP_PROXYHOST + ":" + ZAP_PROXYPORT);
            zapApp.ascan.removeAllScans();
            zapApp.core.newSession("","");
            return seleniumProxy;
        }catch (Exception e){
            Log.info(e);
            return null;
        }
    }

    protected void spiderScan() throws ClientApiException {
        System.out.println("-- Starting the SPIDER Scan --");
        zapApp.spider.scan(URl,null, null,null,null);
        System.out.println("-- The SPIDER Scan was Complete!! --");
    }


    protected void scanningRealTime() throws Exception {
        System.out.println("-- Waiting for passive scan to complete --");
        try {
            zapApp.pscan.enableAllScanners(); // enable passive scanner.

            ApiResponse response = zapApp.pscan.recordsToScan(); // getting a response

            //iterating till we get response as "0".
            while(!response.toString().equals("100")) {
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
        Waiting.time(5000);
        driver = (WebDriver) browserConfig.BrowserSetupOptionsDriver(apiZapSetup(),true,true);
        driver.get(URl);
        spiderScan();
        Waiting.time(5000);
        scanningRealTime();
        Waiting.time(5000);
    }

    protected void RemoveAndCleanTheSession() throws ClientApiException {
        zapApp.ascan.removeAllScans();
        zapApp.core.newSession("","");
    }

    protected void ZapReporting() throws ClientApiException, IOException {
        String report = new String(zapApp.core.htmlreport());
        Path fileReportPath = Paths.get(System.getProperty("user.dir") + "/scanZAPAuto/"+ DateFormat.getDateInstance() + URl + ".html");
        Files.deleteIfExists(fileReportPath);
        Files.write(fileReportPath, report.getBytes());
        RemoveAndCleanTheSession();
    }

    protected void runningZap() throws Exception {
        scanningURL();
        ZapReporting();
        driver.quit();
    }
    public void runZap() throws Exception {
        runningZap();
    }

}
