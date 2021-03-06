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

package com.anyun.cloud.agent.common;

import com.anyun.cloud.tools.scheduling.NodeListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 4/25/16
 */
public class HostWatch implements Watcher {
    private NodeListener nodeListener;
    private String path;

    public HostWatch(String path, NodeListener nodeListener) {
        this.nodeListener = nodeListener;
        this.path = path;
    }

    @Override
    public void process(final WatchedEvent event) {
        try {
            Env.env(ZKConnector.class).addNodeStatusListener(nodeListener);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    nodeListener.process(event);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public NodeListener getNodeListener() {
        return nodeListener;
    }

    public String getPath() {
        return path;
    }
}
