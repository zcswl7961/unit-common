package com.zcswl.airlift;

import io.airlift.bootstrap.Bootstrap;
import io.airlift.event.client.EventModule;
import io.airlift.http.server.HttpServerModule;
import io.airlift.jaxrs.JaxrsModule;
import io.airlift.json.JsonModule;
import io.airlift.node.NodeModule;

/**
 * https://github.com/airlift/airlift
 *
 * Airlift is a framework for building REST services in Java.
 * trino use airlift for rest
 * @author xingyi
 * @date 2021/9/11
 */
public class Service {

    public static void main(String[] args)
    {
        Bootstrap app = new Bootstrap(new ServiceModule(),
                new NodeModule(),
                new HttpServerModule(),
                new EventModule(),
                new JsonModule(),
                new JaxrsModule());
        app.initialize();
    }

    private Service()
    {
    }
}
