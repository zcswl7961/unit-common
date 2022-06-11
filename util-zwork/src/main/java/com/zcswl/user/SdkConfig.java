package com.zcswl.user;

import com.dtstack.sdk.core.common.DtInsightApi;
import com.dtstack.streamapp.sdk.client.StreamTaskApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author xingyi
 * @date 2022/4/13
 */
public class SdkConfig {
    //模拟配置文件
    private  String[] serverUrls = new String[]{"http://x.x.x.x","http://x.x.x.x"};

    @Bean("streamSdk")
    public DtInsightApi getDtInsightApi (){
        DtInsightApi.ApiBuilder builder = new DtInsightApi.ApiBuilder()
                .setServerUrls(serverUrls)
                .setToken("xxx-token-xxx");
        return builder.buildApi();
    }
    /**
     * 注册服务
     **/
    @Bean
    public StreamTaskApiClient getStreamTaskApiClient(@Qualifier("streamSdk")DtInsightApi dtInsightApi){
        return dtInsightApi.getSlbApiClient(StreamTaskApiClient.class);
    }

}
