/**
 * (C) Copyright 2014-2016 teecube
 * (http://teecu.be) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package t3.toe.domains.bw6;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.tools.ant.taskdefs.optional.ReplaceRegExp;

import com.google.common.base.Function;

import t3.CommonMojo;
import t3.SortedProperties;
import t3.plugin.annotations.GlobalParameter;
import t3.tic.bw6.BW6MojoInformation;
import t3.tic.bw6.Messages;
import t3.toe.domains.CommonDomains;
import t3.toe.domains.DomainsMojosInformation;
import t3.toe.domains.bw6.Domain.DomainType;

public class CommonBW6Domains extends CommonDomains {

	@GlobalParameter (property = BW6MojoInformation.BW6.bwVersion, description = BW6MojoInformation.BW6.bwVersion_description, category = BW6MojoInformation.BW6.category, required = true, valueGuessedByDefault = false)
	protected String tibcoBW6Version;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwHome, defaultValue = BW6MojoInformation.BW6.bwHome_default, description = BW6MojoInformation.BW6.bwHome_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6Home;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwBin, defaultValue = BW6MojoInformation.BW6.bwBin_default, description = BW6MojoInformation.BW6.bwBin_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6Bin;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwConfig, defaultValue = BW6MojoInformation.BW6.bwConfig_default, description = BW6MojoInformation.BW6.bwConfig_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6Config;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwAdmin, defaultValue = BW6MojoInformation.BW6.bwAdmin_default, description = BW6MojoInformation.BW6.bwAdmin_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6Admin;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwAdminTRA, defaultValue = BW6MojoInformation.BW6.bwAdminTRA_default, description = BW6MojoInformation.BW6.bwAdminTRA_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6AdminTRA;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwAgent, defaultValue = BW6MojoInformation.BW6.bwAgent_default, description = BW6MojoInformation.BW6.bwAgent_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6Agent;
	
	@GlobalParameter (property = BW6MojoInformation.BW6.bwAgentTRA, defaultValue = BW6MojoInformation.BW6.bwAgentTRA_default, description = BW6MojoInformation.BW6.bwAgentTRA_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6AgentTRA;

	@GlobalParameter (property = BW6MojoInformation.BW6.bwAgentIni, defaultValue = BW6MojoInformation.BW6.bwAgentIni_default, description = BW6MojoInformation.BW6.bwAgentIni_description, category = BW6MojoInformation.BW6.category)
	protected File tibcoBW6AgentIni;

	@GlobalParameter (property = DomainsMojosInformation.BW6.domainHomes, defaultValue = DomainsMojosInformation.BW6.domainHomes_default, description = DomainsMojosInformation.BW6.domainHomes_description, category = BW6MojoInformation.BW6.category)
	protected File domainHomes;

	@GlobalParameter (property = DomainsMojosInformation.BW6.Admin.mode, defaultValue = DomainsMojosInformation.BW6.Admin.mode_default, description = DomainsMojosInformation.BW6.Admin.mode_description, category = BW6MojoInformation.BW6.category)
	protected String bwAdminMode;

	@GlobalParameter (property = DomainsMojosInformation.BW6.Admin.switchToRightMode, defaultValue = DomainsMojosInformation.BW6.Admin.switchToRightMode_default, description = DomainsMojosInformation.BW6.Admin.switchToRightMode_description, category = BW6MojoInformation.BW6.category)
	protected Boolean switchToRightMode;

	@GlobalParameter (property = DomainsMojosInformation.BW6.Admin.alwaysInitBwAdminAndDomains, defaultValue = DomainsMojosInformation.BW6.Admin.alwaysInitBwAdminAndDomains_default, description = DomainsMojosInformation.BW6.Admin.alwaysInitBwAdminAndDomains_description, category = BW6MojoInformation.BW6.category)
	protected Boolean alwaysInitBwAdminAndDomains;

	public static final String bwAdminModeProperty = "bw.admin.mode";

	public enum BwAdminMode {
		LOCAL,
		ENTERPRISE
	}

	private static boolean firstGoal = true;

	protected static BwAdminMode bwAdminCurrentMode;
	protected static List<Domain> domains;
	protected static List<Domain> allDomains;

	public CommonBW6Domains() {}

	public CommonBW6Domains(CommonMojo mojo) {
		super(mojo);
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();

		if (firstGoal || alwaysInitBwAdminAndDomains) {
			firstGoal = false;

			initBwAdminAndDomains();

			getLog().info(Messages.MESSAGE_SPACE);
		} else {
			getLog().info("->- Using current 'bwadmin' configuration and domains listing. -<-");
		}
	}

	protected void displayBwAdminMode() {
		getLog().info("bwadmin mode is set to '" + bwAdminMode + "'" + ("LOCAL".equals(bwAdminMode) && bwAdminCurrentMode.equals(BwAdminMode.ENTERPRISE) ? "     " : "") + " mode.");
	}

	protected void initBwAdminAndDomains() throws MojoExecutionException {
		try {
			bwAdminCurrentMode = getCurrentBwAdminMode();
			String bwAdminMode = getBwAdminMode(); // default is the mode from plugin configuration but can also be the one from topology

			getLog().info("->- Checking up 'bwadmin' configuration and domains listing ->-");
//			getLog().info(t3.Messages.MESSAGE_SPACE);

			getLog().info("bwadmin is running in  '" + bwAdminCurrentMode + "'" + ("ENTERPRISE".equals(bwAdminMode) && bwAdminCurrentMode.equals(BwAdminMode.LOCAL) ? "     " : "") + " mode.");
			displayBwAdminMode();

			if (!bwAdminCurrentMode.toString().equals(bwAdminMode)) {
				getLog().info(t3.Messages.MESSAGE_SPACE);
				getLog().warn("bwadmin is not running in the mode from configuration.");

				if (switchToRightMode) {
					getLog().warn("Switching to right mode...");
					switch (bwAdminMode) {
					case "ENTERPRISE":
						setBwAdminMode(BwAdminMode.ENTERPRISE);
						break;
					case "LOCAL":
						setBwAdminMode(BwAdminMode.LOCAL);
						break;
					}

					getLog().info(Messages.MESSAGE_SPACE);
					getLog().info("bwadmin is now running in '" + bwAdminCurrentMode + "'" + ("ENTERPRISE".equals(bwAdminMode) && bwAdminCurrentMode.equals(BwAdminMode.LOCAL) ? "     " : "") + " mode.");
				} else {
					getLog().error("Unable to switch to right mode because '" + DomainsMojosInformation.BW6.Admin.switchToRightMode + "' property is set to 'false'.");
					throw new MojoExecutionException("Unable to switch 'bwadmin' mode.");
				}
			}

			if (BwAdminMode.ENTERPRISE.equals(bwAdminCurrentMode)) {
				getLog().info(Messages.MESSAGE_SPACE);

				// check that at least one BW Agent is running
				List<AgentStatus> agentsStatuses = getBwAgentsStatuses();
				if (!anAgentIsRunning(agentsStatuses)) {
					getLog().error("No bwagent is running");
					return;
				}

				// load domains from bwadmin
				loadDomainsListFromBwAdmin();
			} else { // local
				loadDomainsListFromLocal();
			}

			displayDomains();

			getLog().info("-<- done -<-");
		} catch (IOException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}
	}

	private void displayDomains() {
		if (allDomains.isEmpty()) return;

		StringBuilder sb = new StringBuilder("Found following domains: ");
		for (Domain domain : allDomains) {
			if (bwAdminCurrentMode.equals(BwAdminMode.LOCAL) && domain.getType().equals(DomainType.ENTERPRISE)) {
				sb.append(domain.getName() + " (enterprise), ");
			} else {
				sb.append(domain.getName() + ", ");
			}
		}

		sb.setLength(sb.length() - 2);

		getLog().info(Messages.MESSAGE_SPACE);
		getLog().info(sb.toString());
	}

	public void executeBWAdmin(List<String> arguments, String messageInfo, String messageSuccess, String messageFailure) throws MojoExecutionException {
		if (messageInfo != null) {
			getLog().info(messageInfo);
		}

		List<File> tras = new ArrayList<File>();
		tras.add(tibcoBW6AdminTRA);

		try {
			executeTIBCOBinary(tibcoBW6Admin, tras, arguments, tibcoBW6Admin.getParentFile(), messageFailure);
		} catch (IOException | MojoExecutionException e) {
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		} finally {
		}

		if (messageSuccess != null) {
			getLog().info(Messages.MESSAGE_SPACE);
			getLog().info(messageSuccess);
		}
	}

	private <T> T executeAndParseResult(List<String> arguments, String messageInfo, String messageFailure, Function<List<String>, T> f) throws MojoExecutionException {
		try {
			CommonMojo.commandOutputStream = new ByteArrayOutputStream();
			CommonMojo.silentBinaryExecution = true;

			getLog().info(messageInfo);

			executeBWAdmin(arguments, null, null, messageFailure);

			String result = CommonMojo.commandOutputStream.toString();

			List<String> resultLines = Arrays.asList(result.split("[\\r\\n]+"));
			return f.apply(resultLines);
		} finally {
			CommonMojo.commandOutputStream = null;
			CommonMojo.silentBinaryExecution = false;
		}
	}

	private void loadDomainsListFromBwAdmin() throws MojoExecutionException {
		Function<List<String>, List<Domain>> f = new Function<List<String>, List<Domain> >() {
			@Override
			public List<Domain> apply(List<String> resultLines) {
				List<Domain> domains = new ArrayList<Domain>();

				for (int i = resultLines.size() - 1; i >= 0; i--) {
					String line = resultLines.get(i);
					if (line.startsWith("Domains:")) {
						break;
					}
					Domain d = new Domain(line.trim());
					d.setType(DomainType.ENTERPRISE); // the 'bwadmin show domains' in enterprise mode shows only enterprise domains
					domains.add(d);
				}

				return domains;
			}
		};

		List<String> arguments = new ArrayList<String>();
		arguments.add("show");
		arguments.add("domains");

		domains = executeAndParseResult(arguments, "Retrieving domains...", "Failed to retrieve domains", f);
		allDomains = domains;
	}

	private void loadDomainsListFromLocal() throws FileNotFoundException, IOException {
		domains = new ArrayList<Domain>();
		allDomains = new ArrayList<Domain>();

		Properties domainsList = SortedProperties.loadPropertiesFile(domainHomes, "UTF-8");
		Enumeration<Object> e = domainsList.keys();
		Domain domain = null;

   		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = domainsList.getProperty(key);

			String domainName = key.substring(0, key.indexOf("."));
			String property = key.substring(key.indexOf(".")+1);
			
			if ("domainHome".equals(property)) {
				domain = new Domain(domainName);
				domain.setHome(new File(value + File.separator + domainName));
				domains.add(domain);
				allDomains.add(domain);
			} else if ("type".equals(property)) {
				switch (value) {
				case "local":
					domain.setType(DomainType.LOCAL);
					break;
				case "enterprise":
					domain.setType(DomainType.ENTERPRISE);
					break;

				default:
					break;
				}
			}
		}

		if (BwAdminMode.LOCAL.equals(bwAdminCurrentMode)) { // keep only local domains in domains list (complete list is in allDomains)
			for (Iterator<Domain> it = domains.iterator(); it.hasNext();) {
				Domain d = (Domain) it.next();

				if (DomainType.ENTERPRISE.equals(d.getType())) {
					it.remove();
				}
			}
		}
	}

	private boolean anAgentIsRunning(List<AgentStatus> agentsStatuses) {
		for (AgentStatus agentStatus : agentsStatuses) {
			if ("Running".equals(agentStatus.getStatus())) {
				return true;
			}
		}
		return false;
	}

	protected List<AgentStatus> getBwAgentsStatuses() throws MojoExecutionException {
		Function<List<String>, List<AgentStatus>> f = new Function<List<String>, List<AgentStatus> >() {
			@Override
			public List<AgentStatus> apply(List<String> resultLines) {
				List<AgentStatus> agents = new ArrayList<AgentStatus>();

				Pattern p = Pattern.compile("([^ \t]*)[ \t]+([^ \t]*)[ \t]+([^ \t]*)[ \t]+([^ \t]*)[ \t]+([^ \t]*)[ \t]+([^ \t]*)[ \t]+([^ \t]*)[ \t]+(.*)");
				for (int i = resultLines.size() - 1; i >= 0; i--) {
					String line = resultLines.get(i);
					if (line.startsWith("Name")) {
						break;
					}
					Matcher m = p.matcher(line);
					if (m.matches()) {
						AgentStatus a = new AgentStatus();
						a.setName(m.group(1));
						a.setStatus(m.group(2));
						a.setMachine(m.group(3));
						a.setVersion(m.group(4));
						a.setConfigState(m.group(5));
						a.setMgmtPort(m.group(6));
						a.setPid(m.group(7));
						a.setUpTime(m.group(8));

						agents.add(a);
					}
				}

				return agents;
			}
		};

		List<String> arguments = new ArrayList<String>();
		arguments.add("show");
		arguments.add("agents");

		return executeAndParseResult(arguments, "Retrieving bwagents statuses...", "Failed to retrieve bwagents", f);
	}

	protected String getBwAdminMode() {
		return bwAdminMode;
	}

	private BwAdminMode getCurrentBwAdminMode() throws IOException {
		Properties iniProperties = SortedProperties.loadPropertiesFile(tibcoBW6AgentIni, "UTF-8");

		String mode = iniProperties.getProperty(bwAdminModeProperty);
		switch (mode) {
		case "local":
			return BwAdminMode.LOCAL;
		case "enterprise":
			return BwAdminMode.ENTERPRISE;
		default:
			// TODO: handle errors
			return null;
		}
	}

	protected void setBwAdminMode(BwAdminMode bwAdminMode) throws IOException {
		ReplaceRegExp replaceRegExp = new ReplaceRegExp();
		replaceRegExp.setByLine(true);
		replaceRegExp.setFile(tibcoBW6AgentIni);
		replaceRegExp.setMatch("bw.admin.mode=(.*)");
		switch (bwAdminMode) {
		case ENTERPRISE:
			replaceRegExp.setReplace("bw.admin.mode=enterprise");
			bwAdminCurrentMode = BwAdminMode.ENTERPRISE;
			break;
		case LOCAL:
			replaceRegExp.setReplace("bw.admin.mode=local");
			bwAdminCurrentMode = BwAdminMode.LOCAL;
			break;
		}
		replaceRegExp.execute();
	}

	protected boolean domainExists(String domainName) {
		if (domains == null) return false;

		for (Domain domain : domains) {
			if (domainName.equals(domain.getName())) {
				return true;
			}
		}

		return false;
	}

	protected boolean domainExistsInOneOfTwoModes(String domainName) {
		if (allDomains == null) return false;

		for (Domain domain : allDomains) {
			if (domainName.equals(domain.getName())) {
				return true;
			}
		}

		return false;
	}

}
