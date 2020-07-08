//package com.zcswl.user;
//
//import com.github.ory.hydra.ApiClient;
//import com.github.ory.hydra.api.AdminApi;
//import com.github.ory.hydra.api.PublicApi;
//import com.github.ory.hydra.model.AcceptConsentRequest;
//import com.github.ory.hydra.model.AcceptLoginRequest;
//import com.github.ory.hydra.model.CompletedRequest;
//import com.github.ory.hydra.model.LoginRequest;
//import com.zcswl.gateway.openapi.api.ApiException;
//
///**
// * @author zhoucg
// * @date 2020-03-11 16:47
// */
//public class AdminApiExample {
//
//    public static void main(String[] args) {
//
//        ApiClient apiClient = new ApiClient();
//        apiClient.setBasePath("http://192.168.239.179:4445");
//
//        AdminApi apiInstance = new AdminApi(apiClient);
//       /* String consentChallenge = "2f7f7d9ec66e459795d467417bf020b2"; // String |
//        AcceptConsentRequest body = new AcceptConsentRequest(); // AcceptConsentRequest |
//        try {
//            CompletedRequest result = apiInstance.acceptConsentRequest(consentChallenge, body);
//            System.out.println(result);
//        } catch (Exception e) {
//            System.err.println("Exception when calling AdminApi#acceptConsentRequest");
//            e.printStackTrace();
//        }*/
//
//
//        // ==由对应的回调信息返回给对应的输出结果
//        // == LoginRequest
//        String challenge = "1d41ef071c754ae08aac6d46f4cecf87";
//        LoginRequest loginRequest = apiInstance.getLoginRequest(challenge);
//        String subject = loginRequest.getSubject();
//
//        Boolean skip = loginRequest.getSkip();
//        // ==acceptLoginRequest
//
//        AcceptLoginRequest acceptLoginRequest = new AcceptLoginRequest();
//        acceptLoginRequest.setSubject(subject);
//
//
//        CompletedRequest completedRequest = apiInstance.acceptLoginRequest(challenge, acceptLoginRequest);
//
//
//    }
//}
