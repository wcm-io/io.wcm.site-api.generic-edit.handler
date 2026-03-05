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
package io.wcm.siteapi.genericedit.handler.link;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ProviderType;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkArgs;
import io.wcm.handler.link.spi.LinkProcessor;
import io.wcm.handler.link.spi.LinkType;
import io.wcm.handler.link.type.InternalCrossContextLinkType;
import io.wcm.handler.link.type.InternalLinkType;
import io.wcm.siteapi.genericedit.GenericEditConfig;
import io.wcm.sling.commons.request.RequestPath;

/**
 * Linkhandler preprocessor to inherit selector to enable generic edit mode to internal links.
 */
@Model(adaptables = {
    SlingHttpServletRequest.class, Resource.class
})
@ProviderType
public class SiteApiInternalLinkInheritGenericEditSelectorLinkPreProcessor implements LinkProcessor {

  // optional injection (only available if called inside a request)
  @SlingObject(injectionStrategy = InjectionStrategy.OPTIONAL)
  private SlingHttpServletRequest request;
  @OSGiService
  private GenericEditConfig genericEditConfig;

  private String genericEditSelector;

  @PostConstruct
  private void activate() {
    genericEditSelector = genericEditConfig.getSelector();
  }

  @Override
  public @NotNull Link process(@NotNull Link link) {
    if (isInternalLink(link) && isRequestHasGenericEditSelector()) {
      LinkArgs linkArgs = link.getLinkRequest().getLinkArgs();
      linkArgs.selectors(appendSelector(linkArgs.getSelectors()));
    }
    return link;
  }

  @SuppressWarnings({
      "null", "unused"
  })
  @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
  private boolean isInternalLink(@NotNull Link link) {
    LinkType linkType = link.getLinkType();
    if (linkType == null) { // should never be null, but actually can be null for null-reference link
      return false;
    }
    return StringUtils.equals(linkType.getId(), InternalLinkType.ID)
        || StringUtils.equals(linkType.getId(), InternalCrossContextLinkType.ID);
  }

  private boolean isRequestHasGenericEditSelector() {
    if (request == null) {
      return false;
    }
    return RequestPath.hasSelector(request, genericEditSelector);
  }

  private @NotNull String appendSelector(@Nullable String selectors) {
    if (selectors == null) {
      return genericEditSelector;
    }
    else {
      return selectors + "." + genericEditSelector;
    }
  }

}
