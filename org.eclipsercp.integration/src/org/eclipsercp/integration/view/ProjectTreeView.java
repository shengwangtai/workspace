package org.eclipsercp.integration.view;

import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipsercp.integration.common.CreateProjectTreeView;
import org.eclipsercp.integration.model.TreeObject;
import org.eclipsercp.integration.model.TreeParent;
import org.eclipsercp.integration.wizard.AddFlowModelWizard;
import org.eclipsercp.integration.wizard.AddSystemModelWizard;

public class ProjectTreeView extends ViewPart {
	public static final String ID = "org.eclipsercp.integration.view.ProjectTreeView";
	private TreeViewer treeViewer;
	private String projectName;
	private Object selectedObject;	// selected object
	
	private Action addConvDiagramAction;
	private Action addCollabDiagramAction;
	
	private Action doubleClickAction;
	
	public ProjectTreeView() {
		// TODO Auto-generated constructor stub
	}
	
	public TreeViewer getViewer()
	{
		return treeViewer;
	}
	/**
	 * TreeViewContentProvider
	 * 
	 * @author Administrator
	 * 
	 */
	class TreeViewContentProvider implements IStructuredContentProvider, ITreeContentProvider	{
		private TreeParent invisibleRoot;
		CreateProjectTreeView createProjectTreeView = new CreateProjectTreeView();

		public void dispose(){
		}

		public Object[] getElements(Object parent){
			if (parent.equals(getViewSite()))
			{
				if (invisibleRoot == null)
					invisibleRoot = createProjectTreeView.initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child){
			if (child instanceof TreeObject)
			{
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent){
			if (parent instanceof TreeParent)
			{
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent){
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub			
		}
	}
	
	/**
	 * TreeViewLabelProvider
	 * 
	 * @author Administrator
	 * 
	 */
	class TreeViewLabelProvider extends LabelProvider	{

		public String getText(Object obj)	{
			return obj.toString();
		}

		public Image getImage(Object obj)	{
			String imageKey = ISharedImages.IMG_OBJ_FILE;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	
	class NameSorter extends ViewerSorter	{
	}
	
	private void createContextMenu(Composite parent)
	{

		MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(new IMenuListener()
		{
			public void menuAboutToShow(IMenuManager manager)
			{
				fillContextMenu(manager);
			}
		});
		Menu menu = mgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(mgr, treeViewer);
	}

	protected void fillContextMenu(IMenuManager manager)
	{
		manager.add(addConvDiagramAction);
		manager.add(addCollabDiagramAction);
	}
	
	private void hookDoubleClickAction()
	{
		treeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				doubleClickAction.run();
			}
		});
	}
	
	private void makeActions()
	{
		doubleClickAction = new Action()
		{
			public void run()
			{
				ISelection selection = treeViewer.getSelection();
				selectedObject = ((IStructuredSelection) selection).getFirstElement();

				String fileType = "";
				if (selectedObject.toString().lastIndexOf(".") != -1)
				{
					fileType = selectedObject.toString().substring(selectedObject.toString().lastIndexOf(".") + 1);
				}

				String selectProjectName = "";
				TreeObject selectionTreeObject = null;
				TreeObject secParent = null;
				if (selectedObject instanceof TreeObject)
				{
					selectionTreeObject = (TreeObject) selectedObject;
					try
					{
						if (fileType.endsWith("bpmn"))
						{
							secParent = (TreeObject) selectedObject;
							selectProjectName = secParent.getParent().getParent().getName();
							IFile pathFile = ResourcesPlugin.getWorkspace().getRoot().getProject(selectProjectName).getFolder(selectionTreeObject.getParent().getName()).getFile(selectionTreeObject.getName());
							getViewSite().getWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(pathFile), BPMN2Editor.EDITOR_ID);
						} 
					} catch (Exception ex)
					{
						// showMessage(ex.toString());
					}
				}
			}
		};
		
		this.addCollabDiagramAction = new Action("Create New collaboration diagram")
		{
			public void run()
			{
				ISelection selection = treeViewer.getSelection();
				selectedObject = ((IStructuredSelection) selection).getFirstElement();
				if (selectedObject instanceof TreeObject)
				{
					if (selectedObject.toString().lastIndexOf(".") == -1 && selectedObject.toString().endsWith("flowModel"))
					{
						TreeParent selectTreeNode = (TreeParent) selectedObject;

						projectName = selectTreeNode.getParent().getName();
						if (selectTreeNode.getParent() instanceof TreeParent)
						{
							AddFlowModelWizard addSystemModelWizard = new AddFlowModelWizard(projectName);
							WizardDialog dialog = new WizardDialog(null, addSystemModelWizard);
							dialog.open();
						}
					}
				}
			}
		};
	
		this.addConvDiagramAction = new Action("Create New conversation diagram")
		{
			public void run()
			{
				ISelection selection = treeViewer.getSelection();
				selectedObject = ((IStructuredSelection) selection).getFirstElement();
				if (selectedObject instanceof TreeObject)
				{
					if (selectedObject.toString().lastIndexOf(".") == -1 && selectedObject.toString().endsWith("systemModel"))
					{
						TreeParent selectTreeNode = (TreeParent) selectedObject;

						projectName = selectTreeNode.getParent().getName();
						if (selectTreeNode.getParent() instanceof TreeParent)
						{
							AddSystemModelWizard addSystemModelWizard = new AddSystemModelWizard(projectName);
							WizardDialog dialog = new WizardDialog(null, addSystemModelWizard);
							dialog.open();
						}
					}
				}
			}
		};
	}

	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new TreeViewContentProvider());
		treeViewer.setLabelProvider(new TreeViewLabelProvider());
		treeViewer.setSorter(new NameSorter());
		treeViewer.setInput(getViewSite());//?
		createContextMenu(parent);
		makeActions();
		hookDoubleClickAction();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
