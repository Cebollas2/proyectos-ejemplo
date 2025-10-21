[#if cmsfn.editMode]
	Esta página hará una redirección a la página de Home
[#else]
	${ctx.getResponse().sendRedirect(cmsfn.link(cmsfn.contentByPath("/cione/private/home")))}
[/#if]