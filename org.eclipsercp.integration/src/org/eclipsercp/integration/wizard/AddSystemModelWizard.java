package org.eclipsercp.integration.wizard;

import org.eclipse.jface.wizard.Wizard;

public class AddSystemModelWizard extends Wizard {

	private AddSystemModelPage addSystemModelPage;
	private String projectName;

	public AddSystemModelWizard(String projectName) {
		setWindowTitle("New Wizard");
		this.projectName = projectName;
	}

	@Override
	public void addPages() {
		addSystemModelPage = new AddSystemModelPage(projectName);
		this.addPage(addSystemModelPage);
	}

	@Override
	public boolean performFinish() {
		return addSystemModelPage.finsh();
	}

}
