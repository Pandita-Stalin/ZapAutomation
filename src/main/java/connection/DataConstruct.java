package connection;

public class DataConstruct {

    private Integer ApplicacionID;
    private String ApplicationName, Scanned, UrlPRE, UrlPro, LoginType, Username, Password, DataLastScanned, PMName, SPMName;


    public Integer getApplicacionID() {
        return ApplicacionID;
    }

    public void setApplicacionID(Integer applicacionID) {
        ApplicacionID = applicacionID;
    }

    public String getApplicationName() {
        return ApplicationName;
    }

    public void setApplicationName(String applicationName) {
        ApplicationName = applicationName;
    }

    public String getScanned() {
        return Scanned;
    }

    public void setScanned(String scanned) {
        Scanned = scanned;
    }

    public String getUrlPRE() {
        return UrlPRE;
    }

    public void setUrlPRE(String urlPRE) {
        UrlPRE = urlPRE;
    }

    public String getUrlPro() {
        return UrlPro;
    }

    public void setUrlPro(String urlPro) {
        UrlPro = urlPro;
    }

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDataLastScanned() {
        return DataLastScanned;
    }

    public void setDataLastScanned(String dataLastScanned) {
        DataLastScanned = dataLastScanned;
    }

    public String getPMName() {
        return PMName;
    }

    public void setPMName(String PMName) {
        this.PMName = PMName;
    }

    public String getSPMName() {
        return SPMName;
    }

    public void setSPMName(String SPMName) {
        this.SPMName = SPMName;
    }

    @Override
    public String toString() {
        return "DataConstruct{" +
                "ApplicacionID=" + ApplicacionID +
                ", ApplicationName='" + ApplicationName + '\'' +
                ", Scanned='" + Scanned + '\'' +
                ", UrlPRE='" + UrlPRE + '\'' +
                ", UrlPro='" + UrlPro + '\'' +
                ", LoginType='" + LoginType + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", DataLastScanned='" + DataLastScanned + '\'' +
                ", PMName='" + PMName + '\'' +
                ", SPMName='" + SPMName + '\'' +
                '}';
    }
}
