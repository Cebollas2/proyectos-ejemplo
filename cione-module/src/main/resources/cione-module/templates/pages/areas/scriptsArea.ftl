<!-- SCRIPT AREA -->

[#assign areaModel = model!]
[#list areaModel.tags as tagNode]
    [#assign tag = cmsfn.asContentMap(tagNode)]
    [#if (cmsfn.publicInstance || tag.activeAuthor!false) && tag.content?has_content]
        ${areaModel.preRender(tag)}
    [/#if]
[/#list]