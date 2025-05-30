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

import static io.wcm.handler.link.LinkNameConstants.PN_LINK_EXTERNAL_REF;
import static io.wcm.handler.link.LinkNameConstants.PN_LINK_TYPE;
import static io.wcm.handler.link.LinkNameConstants.PN_LINK_WINDOW_TARGET;
import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;

import io.wcm.handler.link.type.ExternalLinkType;
import io.wcm.handler.media.MediaNameConstants;
import io.wcm.siteapi.genericedit.builder.GenericComponentBuilderService;
import io.wcm.siteapi.genericedit.component.GenericComponent;
import io.wcm.siteapi.genericedit.component.value.LinkValue;
import io.wcm.siteapi.genericedit.component.value.MediaValue;
import io.wcm.siteapi.genericedit.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.wcm.core.components.models.ResponsiveImage;

@ExtendWith(AemContextExtension.class)
class LinkMediaValueInspectorServiceTest {

  private AemContext context = AppAemContext.newAemContext();

  private GenericComponentBuilderService componentBuilder;
  private Page page;

  @BeforeEach
  void setUp() throws Exception {
    componentBuilder = context.getService(GenericComponentBuilderService.class);
    page = context.create().page("/content/site1/page1");
  }

  @Test
  @SuppressWarnings("null")
  void testWithMixins() {
    Asset asset = context.create().asset("/content/dam/test.jpg", 10, 10, "image/jpeg");
    Resource resource = context.currentResource(context.create().resource(page, "image",
        MediaNameConstants.PN_MEDIA_REF, asset.getPath(),
        PN_LINK_TYPE, ExternalLinkType.ID,
        PN_LINK_EXTERNAL_REF, "https://myhost",
        PN_LINK_WINDOW_TARGET, "_blank",
        PROPERTY_RESOURCE_TYPE, "wcm-io/wcm/core/components/wcmio/responsiveimage/v1/responsiveimage"));
    ResponsiveImage image = context.request().adaptTo(ResponsiveImage.class);

    assertNotNull(image);

    GenericComponent component = componentBuilder.build(image, resource);

    assertEquals(1, component.getLinkProperties().size());
    assertLink(component.getLinkProperties().get(0).getValue(),
        "https://myhost", "_blank");

    assertEquals(1, component.getMediaProperties().size());
    assertMedia(component.getMediaProperties().get(0).getValue(),
        "/content/dam/test.jpg/_jcr_content/renditions/original./test.jpg");
  }

  @Test
  @SuppressWarnings("null")
  void testWithProperties() {
    Asset asset = context.create().asset("/content/dam/test.jpg", 10, 10, "image/jpeg");
    Resource resource = context.currentResource(context.create().resource(page, "model",
        MediaNameConstants.PN_MEDIA_REF, asset.getPath(),
        PN_LINK_TYPE, ExternalLinkType.ID,
        PN_LINK_EXTERNAL_REF, "https://myhost",
        PN_LINK_WINDOW_TARGET, "_blank"));

    context.addModelsForClasses(SimpleModel.class);
    SimpleModel model = context.request().adaptTo(SimpleModel.class);

    assertNotNull(model);

    GenericComponent component = componentBuilder.build(model, resource);

    assertEquals(1, component.getLinkProperties().size());
    assertLink(component.getLinkProperties().get(0).getValue(),
        "https://myhost", "_blank");

    assertEquals(1, component.getMediaProperties().size());
    assertMedia(component.getMediaProperties().get(0).getValue(),
        "/content/dam/test.jpg/_jcr_content/renditions/original./test.jpg");
  }

  private void assertLink(LinkValue linkValue, String linkUrl, String target) {
    assertNotNull(linkValue);
    assertEquals(linkUrl, linkValue.getUrl());
    assertEquals(Map.of("href", linkUrl, "target", target), linkValue.getHtmlAttributes());
    assertTrue(linkValue.isValid());
  }

  private void assertMedia(MediaValue mediaValue, String mediaUrl) {
    assertNotNull(mediaValue);
    assertEquals(mediaUrl, mediaValue.getUrl());
    assertNotNull(mediaValue.getMarkup());
    assertTrue(mediaValue.isValid());
  }

}
