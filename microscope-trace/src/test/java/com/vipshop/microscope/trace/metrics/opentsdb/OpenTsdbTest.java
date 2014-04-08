/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vipshop.microscope.trace.metrics.opentsdb;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Sean Scanlon <sean.scanlon@gmail.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenTsdbTest {

    private OpenTsdb openTsdb;

    @Mock
    private WebResource apiResource;

    @Mock
    WebResource.Builder mockBuilder;

    @Before
    public void setUp() {
        openTsdb = OpenTsdb.create(apiResource);
    }

    @Test
    public void testSend() {
        when(apiResource.path("/api/put")).thenReturn(apiResource);
        when(apiResource.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(anyObject())).thenReturn(mockBuilder);
        openTsdb.send(OpenTsdbMetric.named("foo").build());
        verify(mockBuilder).post();
    }

    @Test
    public void testBuilder() {
        assertNotNull(OpenTsdb.forService("foo")
                .withReadTimeout(1)
                .withConnectTimeout(1)
                .create());
    }
}
