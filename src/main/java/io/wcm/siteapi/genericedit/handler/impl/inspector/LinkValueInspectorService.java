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

import org.apache.commons.lang3.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

import io.wcm.handler.link.Link;
import io.wcm.siteapi.genericedit.builder.ValueInspectorService;
import io.wcm.siteapi.genericedit.component.value.LinkValue;
import io.wcm.wcm.core.components.models.mixin.LinkMixin;

/**
 * Detects wcm.io Link Handler link references.
 */
@Component(service = ValueInspectorService.class)
@ServiceRanking(1000)
public class LinkValueInspectorService extends ValueInspectorService<LinkValue> {

  private static final String PROPERTY_LINKMIXIN_LINKURL = "wcmio:linkURL";

  @Override
  protected @Nullable LinkValue inspectValue(@NotNull String key, @Nullable Object rawValue,
      @NotNull Object instance) {
    Link link = getLinkObject(key, rawValue, instance);
    if (link != null) {
      return new LinkValueImpl(link);
    }
    return null;
  }

  private @Nullable Link getLinkObject(@NotNull String key, @Nullable Object rawValue,
      @NotNull Object instance) {
    if (rawValue instanceof Link) {
      return (Link)rawValue;
    }
    if (Strings.CS.equals(key, PROPERTY_LINKMIXIN_LINKURL) && instance instanceof LinkMixin) {
      return ((LinkMixin)instance).getLinkObject();
    }
    return null;
  }

}
