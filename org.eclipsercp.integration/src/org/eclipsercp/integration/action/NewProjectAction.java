package org.eclipsercp.integration.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipsercp.integration.wizard.CreateProjectWizard;
import org.eclipsercp.integration.wizard.CreateProjectWizardPage;

public class NewProjectAction extends Action {
	public final static String ID = "org.eclipsercp.integration.action.NewProjectAction";

	public NewProjectAction()
	{
		super("Create New Model Project");
		setId(ID);
	}

	public void run()
	{
		// �򿪴�����Ŀ�ĶԻ���
		CreateProjectWizard wizard = new CreateProjectWizard();
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.open();
	}
}
