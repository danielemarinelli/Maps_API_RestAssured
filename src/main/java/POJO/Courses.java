package POJO;

import java.util.List;

public class Courses {

    // webAutomation, api, mobile are Array of multiple JSON objects, so we need to return a List of items
    public List<WebAutomation> webAutomation;
    public List<Api> api;
    public List<Mobile> mobile;

    public List<WebAutomation> getWebAutomation() {
        return webAutomation;
    }

    public List<Api> getApi() {
        return api;
    }

    public List<Mobile> getMobile() {
        return mobile;
    }

    public void setWebAutomation(List<WebAutomation> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public void setApi(List<Api> api) {
        this.api = api;
    }

    public void setMobile(List<Mobile> mobile) {
        this.mobile = mobile;
    }








}
