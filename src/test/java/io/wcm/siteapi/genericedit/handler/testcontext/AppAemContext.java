/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2023 wcm.io
 * %%
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
 * #L%
 */
package io.wcm.siteapi.genericedit.handler.testcontext;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static io.wcm.testing.mock.wcmio.caconfig.ContextPlugins.WCMIO_CACONFIG;
import static io.wcm.testing.mock.wcmio.handler.ContextPlugins.WCMIO_HANDLER;
import static io.wcm.testing.mock.wcmio.siteapi.genericedit.ContextPlugins.WCMIO_SITEAPI_GENERICEDIT;
import static io.wcm.testing.mock.wcmio.sling.ContextPlugins.WCMIO_SLING;
import static io.wcm.testing.mock.wcmio.wcm.ContextPlugins.WCMIO_WCM;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import io.wcm.handler.link.spi.LinkHandlerConfig;
import io.wcm.siteapi.genericedit.handler.impl.inspector.LinkValueInspectorService;
import io.wcm.siteapi.genericedit.handler.impl.inspector.MediaValueInspectorService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextCallback;
import io.wcm.testing.mock.wcmio.caconfig.MockCAConfig;

/**
 * Sets up {@link AemContext} for unit tests.
 */
public final class AppAemContext {

  private AppAemContext() {
    // static methods only
  }

  /**
   * @return {@link AemContext}
   */
  public static AemContext newAemContext() {
    return new AemContextBuilder()
      .plugin(CACONFIG, CORE_COMPONENTS, WCMIO_SLING, WCMIO_WCM, WCMIO_CACONFIG, WCMIO_HANDLER, WCMIO_SITEAPI_GENERICEDIT)
      .afterSetUp(SETUP_CALLBACK)
      .build();
  }

  /**
   * Custom set up rules required in all unit tests.
   */
  private static final AemContextCallback SETUP_CALLBACK = new AemContextCallback() {

    @Override
    public void execute(@NotNull AemContext context) throws IOException {

      // context path strategy
      MockCAConfig.contextPathStrategyAbsoluteParent(context, 1, 2, 3);

      // link handler config
      context.registerService(LinkHandlerConfig.class, new DummyLinkHandlerConfig());

      // register services
      context.registerInjectActivateService(LinkValueInspectorService.class);
      context.registerInjectActivateService(MediaValueInspectorService.class);
    }
  };

}
