package org.eclipsercp.integration.wizard;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.swt.widgets.Label;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipsercp.integration.action.NewProjectAction;
import org.eclipsercp.integration.common.CreateProjectTreeView;
import org.eclipsercp.integration.model.TreeParent;
import org.eclipsercp.integration.view.ProjectTreeView;

public class CreateProjectWizardPage extends WizardPage {
	//ҳ�沼��
    private Composite container;
    private Label projectNameLabel;
    private Label convrDiagNameLabel;
    private Label collabDiagNameLabel;
    private Label projectDescriptionLabel;
    private Text projectName;
    private Text convrDiagName;
    private Text collabDiagName;
    private Text projectDescription;    

	protected CreateProjectWizardPage() {
		// TODO Auto-generated constructor stub
		super("new project page one ");
		setTitle("�½�һ��������Ŀ");
		setDescription("�½�һ��������Ŀ");		
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
       container = new Composite(parent, SWT.NULL);
       GridLayout layout = new GridLayout();
       container.setLayout(layout);
       layout.numColumns = 2; //ҳ�沼��������Ԫ��
       
      //ҳ�����һ��Ϊlabel����ʾ������Ϣ
      projectNameLabel = new Label(container, SWT.NONE);
//      projectNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,1, 1));
      projectNameLabel.setText("��Ŀ����:");
      
       projectName = new Text(container, SWT.BORDER | SWT.SINGLE);       
       projectName.setText("");
       projectName.addKeyListener(new KeyListener() {
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (projectName.getText().isEmpty()) {
				setPageComplete(true);				
			}
		}
	});
	   projectName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
       // Required to avoid an error in the system
	   setControl(container); 
	   
       convrDiagNameLabel = new Label(container, SWT.NONE);
       convrDiagNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,1, 1));
       convrDiagNameLabel.setText("����������:");
       convrDiagName = new Text(container, SWT.BORDER);
       convrDiagName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
       
       collabDiagNameLabel = new Label(container, SWT.NONE);
       collabDiagNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,1, 1));
       collabDiagNameLabel.setText("ҵ�����̰�����:");
       collabDiagName = new Text(container, SWT.BORDER);
       collabDiagName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public String getProjectText() {
		return projectName.getText();
	}
	public boolean finish() {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			createProject(this.getProjectText());
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ProjectTreeView.ID);
			CreateProjectTreeView createProjectTreeView=new CreateProjectTreeView();
			ProjectTreeView callTreeView = (ProjectTreeView) view;
			TreeParent treeparent=null;
			treeparent=createProjectTreeView.initialize();
			callTreeView.getViewer().setInput(treeparent);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * �½���Ŀģ��
	 * @param projectName
	 * @throws CoreException
	 */
		private void createProject(String projectName) throws CoreException {
//			Document document = DocumentHelper.createDocument();
			try {
				// �õ�workspace��·��
				IPath workPath = new Path(ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toString());
				// �õ���������ռ��µ����еĹ��̼���
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject(projectName);
				project.create(null);
				project.open(null);
				// ��������
				IProjectDescription description = project.getDescription();

				description.setNatureIds(new String[] { JavaCore.NATURE_ID });
				project.setDescription(description, null);
				// ��������
				IJavaProject javaProject = JavaCore.create(project);

				List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
				IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
				LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
				for (LibraryLocation element : locations) {
					entries.add(JavaCore.newLibraryEntry(
							element.getSystemLibraryPath(), null, null));
				}
				// ���javapath
				javaProject.setRawClasspath(
						entries.toArray(new IClasspathEntry[entries.size()]), null);

				IFolder convDiagramFolder = project.getFolder(projectName + "_ConversationDiagram");
				convDiagramFolder.create(false, true, null);
				
				IFolder collabDiagramFolder = project.getFolder(projectName + "_CollabrationDiagram");
				collabDiagramFolder.create(false, true, null);

//				// ��ȡ�ļ�
//				FileWriter fileWriter = new FileWriter(project.getLocation()	.toString() + "/vico-project.xml");
//				// �����ļ�����
//				OutputFormat xmlFormat = new OutputFormat();
//				xmlFormat.setEncoding("UTF-8");
//				// ����д�ļ�����
//				XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
//				// д���ļ�
//				xmlWriter.write(document);
//				// �ر�
//				xmlWriter.close();
	
				IFile newConvDiagramFile = project.getFile(projectName
						+ "_ConversationDiagram/" + convrDiagName.getText() + ".bpmn");
				String contents = "";
				InputStream convDiagramSource = new ByteArrayInputStream(contents.getBytes());
				newConvDiagramFile.create(convDiagramSource, false, null);

				IFile newCollabDiagramFile = project.getFile(projectName
						+ "_CollabrationDiagram/" + collabDiagName.getText() + ".bpmn");
				InputStream CollabDiagramSource = new ByteArrayInputStream(
						contents.getBytes());
				newCollabDiagramFile.create(CollabDiagramSource, false, null);

			} catch (Exception ex) {
				System.out.print(ex);
			}
		}

}
