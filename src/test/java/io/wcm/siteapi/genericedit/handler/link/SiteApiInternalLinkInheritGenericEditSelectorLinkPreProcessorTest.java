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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.siteapi.genericedit.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SiteApiInternalLinkInheritGenericEditSelectorLinkPreProcessorTest {

  private static final String GENERIC_EDIT_SELECTOR = "generic-edit";

  private AemContext context = AppAemContext.newAemContext();

  private Page page;

  @BeforeEach
  void setUp() {
    page = context.currentPage(context.create().page("/content/site1/page1"));
  }

  @Test
  @SuppressWarnings("null")
  void testInternalLink_NoSelector() {
    LinkHandler linkHandler = context.request().adaptTo(LinkHandler.class);
    Link link = linkHandler.get(page).build();
    assertEquals("/content/site1/page1.html", link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testInternalLink_GenericEditSelector() {
    context.requestPathInfo().setSelectorString(GENERIC_EDIT_SELECTOR);

    LinkHandler linkHandler = context.request().adaptTo(LinkHandler.class);
    Link link = linkHandler.get(page).build();
    assertEquals("/content/site1/page1." + GENERIC_EDIT_SELECTOR + ".html", link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testInternalLink_GenericEditSelector_ExistingSelector() {
    context.requestPathInfo().setSelectorString(GENERIC_EDIT_SELECTOR);

    LinkHandler linkHandler = context.request().adaptTo(LinkHandler.class);
    Link link = linkHandler.get(page).selectors("sel1.sel2").build();
    assertEquals("/content/site1/page1.sel1.sel2." + GENERIC_EDIT_SELECTOR + ".html", link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testExternalLink_GenericEditSelector() {
    context.requestPathInfo().setSelectorString(GENERIC_EDIT_SELECTOR);

    LinkHandler linkHandler = context.request().adaptTo(LinkHandler.class);
    Link link = linkHandler.get("https://myhost/page1.html").build();
    assertEquals("https://myhost/page1.html", link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testInternalLink_GenericEditSelector_NoRequest() {
    context.requestPathInfo().setSelectorString(GENERIC_EDIT_SELECTOR);

    LinkHandler linkHandler = context.currentResource().adaptTo(LinkHandler.class);
    Link link = linkHandler.get(page).build();
    assertEquals("/content/site1/page1.html", link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testInvalidLink() {
    LinkHandler linkHandler = context.currentResource().adaptTo(LinkHandler.class);
    Link link = linkHandler.invalid();
    assertNull(link.getUrl());
  }

  @Test
  @SuppressWarnings("null")
  void testNullReferenceLink() {
    LinkHandler linkHandler = context.currentResource().adaptTo(LinkHandler.class);
    Link link = linkHandler.get((String)null).build();
    assertNull(link.getUrl());
  }

}
