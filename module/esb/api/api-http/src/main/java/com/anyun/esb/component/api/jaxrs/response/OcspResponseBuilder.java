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

package com.anyun.esb.component.api.jaxrs.response;

import com.anyun.cloud.api.Response;
import com.anyun.cloud.tools.json.JsonUtil;
import com.anyun.common.jbi.JbiCommon;
import com.anyun.esb.component.api.jaxrs.RestMethodDefine;
import com.anyun.exception.JbiComponentException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 4/20/16
 */
public class OcspResponseBuilder implements ResponseBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(OcspResponseBuilder.class);

    @Override
    public Object build(RestMethodDefine restMethodDefine, long startTime, Response response) throws JbiComponentException {
        String ocspResponse = (String) response.getContent();
        LOGGER.debug("OCSP response base64 string [\n{}\n]",ocspResponse);
        return Base64.getDecoder().decode(ocspResponse);
    }

    @Override
    public Class<?> getResponseType() {
        return byte[].class;
    }

    @Override
    public boolean match(Produces produces) {
        for (String produce : produces.value()) {
            if (produce.equals("application/ocsp-response") || produce.equals("ocsp-response")) {
                return true;
            }
        }
        return false;
    }
}
