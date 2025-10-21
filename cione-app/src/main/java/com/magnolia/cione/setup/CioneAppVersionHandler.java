package com.magnolia.cione.setup;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleModuleResource;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.FilterOrderingTask;
import info.magnolia.module.delta.Task;

/**
 * This class is optional and lets you manage the versions of your module, by
 * registering "deltas" to maintain the module's configuration, or other type of
 * content. If you don't need this, simply remove the reference to this class in
 * the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class CioneAppVersionHandler extends DefaultModuleVersionHandler {

	public CioneAppVersionHandler() {
//		register(DeltaBuilder.update("1.16-SNAPSHOT", "")
//			.addTask(new BootstrapSingleModuleResource("Configuracion Endpoints", "",
//					"config.modules.cione-module.rest-endpoints.xml",
//					ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))					
//		);
	}

//	@Override
//	protected List<Task> getExtraInstallTasks(InstallContext installContext) {
//		List<Task> extraInstallTasks = new ArrayList<Task>(super.getExtraInstallTasks(installContext));
//		extraInstallTasks.add(new FilterOrderingTask("i18nCioneRest", new String[] { "i18n" }));
//		return extraInstallTasks;
//	}

}
