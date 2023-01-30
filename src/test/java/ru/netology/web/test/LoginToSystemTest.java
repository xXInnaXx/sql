package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.LoginPage;
import ru.netology.web.repository.DbManager;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginToSystemTest {

    @AfterAll
    static void cleanDb() {
        DbManager.cleanDb();
    }

    @Test
    void shouldLoginIn() throws InterruptedException {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(DataHelper.getAccountInfo());
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo.getLogin());
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        String dashBoardData = dashboardPage.getDashBoardData();
        assertEquals("Личный кабинет", dashBoardData);
    }
}