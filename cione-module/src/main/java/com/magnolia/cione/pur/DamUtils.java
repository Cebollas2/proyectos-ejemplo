package com.magnolia.cione.pur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.magnolia.cms.core.FileSystemHelper;
import info.magnolia.context.MgnlContext;
import info.magnolia.dam.jcr.AssetNodeTypes;
import info.magnolia.dam.jcr.DamConstants;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.objectfactory.Components;

public class DamUtils {

	private static final Logger log = LoggerFactory.getLogger(DamUtils.class);
	
	public Node setFile(byte[] imageData, final String prefix, final String folderPath, final String fileExtension ) throws RepositoryException, FileNotFoundException {
		Node assetResourceNode = null;
		try {
			File file = File.createTempFile(prefix, fileExtension, Components.getComponent(FileSystemHelper.class).getTempDirectory());
			FileUtils.writeByteArrayToFile(file, imageData);
			String newFileName = prefix + "_" + Calendar.getInstance().getTimeInMillis() + "." + fileExtension;

			// "Navigate" to the assets folder node
			String nodeName  = folderPath.substring(folderPath.lastIndexOf('/') + 1);
			String folderName = folderPath.substring(0, folderPath.lastIndexOf('/'));
			Session session = MgnlContext.getJCRSession(DamConstants.WORKSPACE);
			Node folderNode = JcrUtils.getOrAddNode(session.getNode("/"+folderName), nodeName , NodeTypes.Folder.NAME);
			folderNode.getSession().save();

			InputStream input = new FileInputStream(file);
			try {

				// Create asset node
				Node assetNode = JcrUtils.getOrAddNode(folderNode, newFileName, AssetNodeTypes.Asset.NAME);
				assetNode.setProperty(AssetNodeTypes.Asset.ASSET_NAME, newFileName);

				// Create asset resource node
				assetResourceNode = JcrUtils.getOrAddNode(assetNode, AssetNodeTypes.AssetResource.RESOURCE_NAME, AssetNodeTypes.AssetResource.NAME);
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.DATA, session.getValueFactory().createBinary(input));
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.FILENAME, newFileName);
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.EXTENSION, fileExtension);
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.SIZE, Long.toString(file.length()));
					final ImageInfo imageInfo = new ImageInfo();
					imageInfo.setInput(input);
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.MIMETYPE, imageInfo.getMimeType());
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.WIDTH, Long.toString(imageInfo.getWidth()));
				assetResourceNode.setProperty(AssetNodeTypes.AssetResource.HEIGHT, Long.toString(imageInfo.getHeight()));
			} finally {
				IOUtils.closeQuietly(input);
			}
			session.save();
			assetResourceNode.getSession().save();
			if (file.exists() && !Files.deleteIfExists(file.toPath())) {
				log.warn("Cannot delete file: " + file.getAbsolutePath());
			}
			return assetResourceNode;

		} catch (Exception e) {

			log.debug("Could not save image as news asset", e);
			return assetResourceNode;
		}
	}

}
