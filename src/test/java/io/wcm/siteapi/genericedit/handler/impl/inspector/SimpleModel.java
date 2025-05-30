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
package io.wcm.siteapi.genericedit.handler.impl.inspector;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.handler.media.Media;
import io.wcm.handler.media.MediaHandler;

@Model(adaptables = SlingHttpServletRequest.class)
public class SimpleModel implements ComponentExporter {

  @SlingObject
  private Resource resource;
  @Self
  private LinkHandler linkHandler;
  @Self
  private MediaHandler mediaHandler;

  private Link link;
  private Media media;

  @PostConstruct
  private void activate() {
    this.link = linkHandler.get(resource).build();
    this.media = mediaHandler.get(resource).build();
  }

  public Link getLink() {
    return this.link;
  }

  public Media getMedia() {
    return this.media;
  }

  @Override
  public @NotNull String getExportedType() {
    return resource.getResourceType();
  }

}
