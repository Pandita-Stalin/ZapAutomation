package loginPageApp.gicarPage;

import tools.Waiting;

public class ScanningZapLoginGicar {

    GicarLoginPage loginGicar = new GicarLoginPage();


    // Access on the login gicar.

    private void setLoginGicar(String loginName, String gicarPass){
        loginGicar.elementDisplayed(new GicarLoginPage().getUserInput());
        loginGicar.elementDisplayed(new GicarLoginPage().getPasswordInput());
        loginGicar.elementDisplayed(new GicarLoginPage().getAccessButton());
        loginGicar.setCompleteLoginPart(loginName,gicarPass);
        Waiting.time(5000);
    }
}
