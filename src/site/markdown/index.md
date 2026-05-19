## About Site API Generic Edit Handler Extension

Support wcm.io Handler infrastructure in Site API Generic Edit Mode.

[![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.site-api.generic-edit.handler)](https://repo1.maven.org/maven2/io/wcm/io.wcm.site-api.generic-edit.handler/)


### Documentation

* [Usage][usage]
* [API documentation][apidocs]
* [Changelog][changelog]


### Overview

The Site API Generic Edit Handler Extension:

* Extends [Site API Generic Edit][generic-edit] with capabilities for [wcm.io Handler][handler].
* Add ValueInspectorService implementations to detect wcm.io Handler Link and Media objects, or the related mixins from [wcm.io WCM Core Components][wcm-core-components].
* Provides a Link Handler preprocessor that inherits the configured selector for the generic edit view to the rendered links.


### AEM Version Support Matrix

| Site API Generic Edit Handler Extension version | AEM Sites Core Component version |AEM version supported
|-------------------------------------------------|----------------------------------|-----------------------
|1.2.0 or higher                                  |2.25.4 and up                     |AEM 6.5.24+, AEM 6.6.2+, AEMaaCS
|1.1.x                                            |2.25.4 and up                     |AEM 6.5.17+, AEM 6.6.0+, AEMaaCS


### Dependencies

To use this module you have to deploy also:

|---|---|---|
| [wcm.io Sling Commons](https://repo1.maven.org/maven2/io/wcm/io.wcm.sling.commons/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.sling.commons)](https://repo1.maven.org/maven2/io/wcm/io.wcm.sling.commons/) |
| [wcm.io Handler Commons](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.commons/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.handler.commons)](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.commons/) |
| [wcm.io URL Handler](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.url/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.handler.url)](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.url/) |
| [wcm.io Media Handler](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.media/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.handler.media)](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.media/) |
| [wcm.io Link Handler](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.link/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.handler.link)](https://repo1.maven.org/maven2/io/wcm/io.wcm.handler.link/) |
| [wcm.io WCM Core Components](https://repo1.maven.org/maven2/io/wcm/io.wcm.wcm.core.components/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.wcm.core.components)](https://repo1.maven.org/maven2/io/wcm/io.wcm.wcm.core.components/) |
| [Site API Generic Edit](https://repo1.maven.org/maven2/io/wcm/io.wcm.site-api.generic-edit/) | [![Maven Central](https://img.shields.io/maven-central/v/io.wcm/io.wcm.site-api.generic-edit)](https://repo1.maven.org/maven2/io/wcm/io.wcm.site-api.generic-edit/) |


### GitHub Repository

Sources: https://github.com/wcm-io/io.wcm.site-api.generic-edit.handler


[usage]: usage.html
[apidocs]: apidocs/
[changelog]: changes.html
[generic-edit]: https://wcm.io/site-api/generic-edit/
[handler]: https://wcm.io/handler/
[wcm-core-components]: https://wcm.io/wcm/core-components/