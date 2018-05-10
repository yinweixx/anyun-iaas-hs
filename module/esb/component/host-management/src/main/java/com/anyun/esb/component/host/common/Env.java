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

package com.anyun.esb.component.host.common;

import com.anyun.esb.component.host.client.HostSshClient;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 4/25/16
 */
public class Env {
    private final static Map<Object, Object> env = new HashMap();

    public static void init(Properties properties) throws Exception {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            export(entry.getKey(), entry.getValue());
        }
    }

    public static void export(Object key, Object value) {
        env.put(key, value);
    }

    public static void unset(String key) {
        env.remove(key);
    }

    public static <T> void unset(Class<T> type) {
        env.remove(type);
    }

    public static void set(Object key, Object value) {
        export(key, value);
    }

    public static String env(String key) {
        return env(String.class, key);
    }

    public static <T> T env(Class<T> type) {
        Object obj = env.get(type);
        if (obj == null)
            return null;
        return (T) obj;
    }

    public static <T> T env(Class<T> type, String key) {
        Object value = env.get(key);
        if (type.equals(String.class))
            return (T) String.valueOf(value);
        if (type.equals(Long.class))
            return (T) Long.valueOf(value.toString());
        if (type.equals(Integer.class))
            return (T) Integer.valueOf(value.toString());
        return (T) value;
    }

    public static DockerClient getDockerClient(String host) {
        Jedis jedis = Env.env(Jedis.class);
        if (jedis == null)
            return null;
        String email = jedis.get("com.anyun.docker.registry.email");
        String passwd = jedis.get("com.anyun.docker.registry.passwd");
        String url = jedis.get("com.anyun.docker.registry.url");
        String user = jedis.get("com.anyun.docker.registry.user");

        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        builder.withRegistryUrl(url + "/v2/");
        builder.withRegistryEmail(email);
        builder.withDockerHost("tcp://" + host.trim() + ":2375");
        builder.withRegistryUsername(user);
        builder.withRegistryPassword(passwd);
        builder.withDockerTlsVerify(false);
        DockerClientConfig dockerClientConfig = builder.build();
        DockerClient dockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerCmdExecFactory(DockerClientBuilder.getDefaultDockerCmdExecFactory())
                .build();
        return dockerClient;
    }

    public static HostSshClient getSshClient(String zkString) throws Exception{
        String serialNumber = zkString.split(":")[0].trim();
        String managementIp = zkString.split(":")[1].trim();
        HostSshClient hostSshClient = Env.env(HostSshClient.class, "host.ssh." + serialNumber);
        if(hostSshClient != null)
            return hostSshClient;
        Jedis jedis = Env.env(Jedis.class);
        String hostCert = jedis.get("com.anyun.certs.path") + "/host_key";
        hostSshClient = new HostSshClient(hostCert);
        hostSshClient.setHost(managementIp);
        hostSshClient.connect();
        Env.export("host.ssh." + serialNumber, hostSshClient);
        return hostSshClient;
    }
}
