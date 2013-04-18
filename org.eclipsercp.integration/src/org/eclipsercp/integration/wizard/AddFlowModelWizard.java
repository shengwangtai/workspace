package org.eclipsercp.integration.wizard;

import org.eclipse.jface.wizard.Wizard;

public class AddFlowModelWizard extends Wizard {

	private AddFlowModelPage addFlowModelPage;
	private String projectName;

	public AddFlowModelWizard(String projectName) {
		setWindowTitle("New Wizard");
		this.projectName = projectName;
	}

	@Override
	public void addPages() {
		addFlowModelPage = new AddFlowModelPage(projectName);
		this.addPage(addFlowModelPage);
	}

	@Override
	public boolean performFinish() {
		return addFlowModelPage.finsh();
	}

}
