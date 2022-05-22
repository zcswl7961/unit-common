package com.zcswl.user;

import com.dtstack.sdk.core.common.DtInsightApi;
import com.dtstack.streamapp.sdk.client.StreamTaskApiClient;

/**
 * @author xingyi
 * @date 2022/4/13
 */
public class MainTest {

    public static void main(String[] args) {
        String[] serverUrls = new String[]{"http://x.x.x.x","http://x.x.x.x"};
        DtInsightApi.ApiBuilder builder = new DtInsightApi.ApiBuilder()
                .setServerUrls(serverUrls)
                .setToken("xxx-token-xxx");
        DtInsightApi api = builder.buildApi();
        StreamTaskApiClient slbApiClient = api.getSlbApiClient(StreamTaskApiClient.class);
    }
}
