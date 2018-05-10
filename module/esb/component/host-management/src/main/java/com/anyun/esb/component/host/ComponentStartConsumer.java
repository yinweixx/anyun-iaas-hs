/*
 *     Licensed to the Apache Software Foundation (ASF) under one or more
 *     contributor license agreements.  See the NOTICE file distributed with
 *     this work for additional information regarding copyright ownership.
 *     The ASF licenses this file to You under the Apache License, Version 2.0
 *     (the "License"); you may not use this file except in compliance with
 *     the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anyun.esb.component.host;

import com.anyun.esb.component.host.common.Env;
import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 4/13/16
 */
public class ComponentStartConsumer extends DefaultConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentStartConsumer.class);
    private Component component;
    private String option = "";
    private RelationalDatabaseTesterConsumer relationalDatabaseTesterConsumer;
    private ServicesRegistConsumer registConsumer;
    private HostConnectConsumer hostConnectConsumer;

    public ComponentStartConsumer(Component component, Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.component = component;
        relationalDatabaseTesterConsumer = new RelationalDatabaseTesterConsumer();
        registConsumer = new ServicesRegistConsumer(component, endpoint, processor);
        hostConnectConsumer = new HostConnectConsumer(component, endpoint, processor);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        Jedis jedis = new Jedis("redis.dev.anyuncloud.com", 6379);
        Env.export(Jedis.class, jedis);
        Env.export(CamelContext.class, getEndpoint().getCamelContext());
        relationalDatabaseTesterConsumer.start();
        registConsumer.start();
        hostConnectConsumer.start();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        if (registConsumer != null)
            registConsumer.stop();
        if (hostConnectConsumer != null)
            hostConnectConsumer.stop();
    }

    @Override
    protected void doShutdown() throws Exception {
        super.doShutdown();
        if (registConsumer != null)
            registConsumer.stop();
        if (hostConnectConsumer != null)
            hostConnectConsumer.stop();
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
