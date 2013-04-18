package org.eclipsercp.integration.wizard;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class CreateProjectWizard extends Wizard implements INewWizard {
	private ISelection selection;
	private CreateProjectWizardPage createProjectPage;
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		  this.selection = selection;
		  setNeedsProgressMonitor(true);
		  setWindowTitle("新建集成项目");
	}

	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		createProjectPage = new CreateProjectWizardPage();
		addPage(createProjectPage);
	}
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return createProjectPage.finish();
	}

}
