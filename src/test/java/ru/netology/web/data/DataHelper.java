package ru.netology.web.data;

import lombok.Value;
import ru.netology.web.repository.DbManager;

import java.util.Map;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AccountInfo {
        private String login;
        private String password;

    }



    public static AccountInfo getAccountInfo() {
        return new AccountInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(String user) {
        return new VerificationCode(DbManager.getCodeByUser(user));
    }


}


