package org.eclipsercp.integration.wizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class AddSystemModelPage extends WizardPage {
	private Text text;
	private Text text_1;
	private String projectName;
	private IFolder storeFolder;
	private IFolder srcFolder;

	/**
	 * Create the wizard.
	 */
	public AddSystemModelPage(String projectName) {
		super("add system model");
		setTitle("add system model");
		setDescription("add system model.");
		this.projectName = projectName;
		srcFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName).getFolder("src");
		storeFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName)
				.getFolder(projectName + "_systemModel");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label.setText("system Name:");

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_1 = new Label(container, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		label_1.setText("description:");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		text_1 = new Text(container, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1);
		gd_text_1.heightHint = 155;
		text_1.setLayoutData(gd_text_1);
	}

	public boolean finsh() {
		boolean result = false;
		if (storeFolder != null) {
			try {
				IFile SystemodelFile = storeFolder.getFile(text.getText()
						+ ".systemModel");
				String contents = "";
				InputStream systemModelsource = new ByteArrayInputStream(
						contents.getBytes());
				SystemodelFile.create(systemModelsource, false, null);

				IFile SystemmodelXml = srcFolder.getFile("App_systemModel/"
						+ text.getText() + ".xml");
				String xmlcontents = "";
				InputStream SystemmodelXmlStream = new ByteArrayInputStream(
						xmlcontents.getBytes());
				SystemmodelXml.create(SystemmodelXmlStream, false, null);
				result = true;
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			}
		}

		return result;
	}
}
