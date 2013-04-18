package org.eclipsercp.integration.common;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipsercp.integration.model.TreeObject;
import org.eclipsercp.integration.model.TreeParent;
public class CreateProjectTreeView {
	private TreeParent invisibleRoot=null;
	/*
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	public TreeParent initialize() {
		// �õ������ռ��µ�������Ŀ
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		invisibleRoot = new TreeParent("");
		for (IProject project : projects) {

			// �����Ŀ���˵�
			TreeParent root = new TreeParent(project.getName());

			// �õ��������ͼ
			IFolder convDiagramFolder = project.getFolder(project
					.getName().toString() + "_conversationDiagram");
			// ���̽�ģĿ¼
			IFolder collabDiagramFolder = project.getFolder(project.getName()
					.toString() + "_collaborationDiagram");

			// ��ӽ�ģ�ļ��м�Ŀ¼
			try {
				IResource projectRootResource[] = project.members();
				for (IResource resource : projectRootResource) {
					if (resource instanceof IFolder) {
						TreeParent rootTree = new TreeParent(
								resource.getName());
						root.addChild(rootTree);
						IFolder rootFolder = (IFolder) resource;
						IResource rootFolderSource[] = rootFolder.members();
						for (IResource rootResource : rootFolderSource) {
							if (rootResource instanceof IFolder) {
								TreeParent newTreeObject = new TreeParent(
										rootResource.getName());
								rootTree.addChild(newTreeObject);
								for (IResource childResource : ((IFolder) rootResource)
										.members()) {
									TreeObject childTreeObject = new TreeObject(
											childResource.getName());
									newTreeObject.addChild(childTreeObject);
								}
							} else {
								TreeObject newTreeObject = new TreeObject(
										rootResource.getName());

								rootTree.addChild(newTreeObject);

							}
						}
					}

				}
				invisibleRoot.addChild(root);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return invisibleRoot;
	}
	
}
