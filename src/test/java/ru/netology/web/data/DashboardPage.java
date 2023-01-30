package ru.netology.web.data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement account = $("[data-test-id=dashboard]");

    public String getDashBoardData() {
        return account.getText().replaceFirst("^\\s*", "");
    }

}
