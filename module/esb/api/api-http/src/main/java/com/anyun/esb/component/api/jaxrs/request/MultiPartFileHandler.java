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

package com.anyun.esb.component.api.jaxrs.request;

import org.apache.camel.Exchange;
import org.eclipse.jetty.util.MultiPartInputStreamParser;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author twitchgg <twitchgg@yahoo.com>
 * @version 1.0
 * @date 6/8/16
 */
public interface MultiPartFileHandler {

    /**
     *
     * @param files
     * @param servletRequest
     * @param exchange
     * @return
     * @throws Exception
     */
    Object process(List<MultiPartInputStreamParser.MultiPart> files, HttpServletRequest servletRequest, Exchange exchange) throws Exception;

    /**
     *
     * @return
     */
    boolean isChain();
}
