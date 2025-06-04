## Site API Generic Edit Handler Extension usage

First, follow the usage steps outlined in the [Site API Generic Edit usage][generic-edit-usage].


### Enabling Generic Edit as primary edit view

If you completed the steps for Generic Edit, you are ready to go.


### Enabling Generic Edit as secondary edit view

In addition to the steps for Generic Edit, you should customize you link handler configuration (in your project's `LinkHandlerConfigImpl`) and add the following Link Handler Preprocessor:

* `io.wcm.siteapi.genericedit.handler.link.SiteApiInternalLinkInheritGenericEditSelectorLinkPreProcessor`

With this activated, it is ensured that the selector `.generic-edit` is not lost when editors navigate to other pages in the preview mode.


[generic-edit-usage]: https://wcm.io/site-api/generic-edit/usage.html