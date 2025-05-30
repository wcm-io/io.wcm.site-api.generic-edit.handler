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

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

import io.wcm.handler.media.Media;
import io.wcm.siteapi.genericedit.builder.ValueInspectorService;
import io.wcm.siteapi.genericedit.component.value.MediaValue;
import io.wcm.wcm.core.components.models.mixin.MediaMixin;

/**
 * Detects wcm.io Media Handler media references.
 */
@Component(service = ValueInspectorService.class)
@ServiceRanking(1000)
public class MediaValueInspectorService extends ValueInspectorService<MediaValue> {

  private static final String PROPERTY_MEDIAMIXIN_MEDIAURL = "wcmio:mediaURL";

  @Override
  protected @Nullable MediaValue inspectValue(@NotNull String key, @Nullable Object rawValue,
      @NotNull Object instance) {
    Media link = getMediaObject(key, rawValue, instance);
    if (link != null) {
      return new MediaValueImpl(link);
    }
    return null;
  }

  private @Nullable Media getMediaObject(@NotNull String key, @Nullable Object rawValue,
      @NotNull Object instance) {
    if (rawValue instanceof Media) {
      return (Media)rawValue;
    }
    if (StringUtils.equals(key, PROPERTY_MEDIAMIXIN_MEDIAURL) && instance instanceof MediaMixin) {
      return ((MediaMixin)instance).getMediaObject();
    }
    return null;
  }

}
