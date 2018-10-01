/**
 * (C) Copyright 2016-2017 teecube
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
package t3.toe.domains;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import t3.AdvancedMavenLifecycleParticipant;
import t3.CommonMojo;
import t3.CommonTIBCOMojo;
import t3.plugin.PropertiesEnforcer;
import t3.plugin.annotations.Mojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
public abstract class CommonDomains extends CommonTIBCOMojo {

	protected String currentGoalName;
	private static boolean firstGoal = true;

	public CommonDomains() {}

	public CommonDomains(CommonMojo mojo) {
		super(mojo);
	}

	@Override
	protected AdvancedMavenLifecycleParticipant getLifecycleParticipant() {
		return new DomainsLifecycleParticipant();
	}

	protected void initDefaultParameters() throws MojoExecutionException {
		// manually init default parameters with well known values or guessed values
	}

	@Override
	public <T> void initStandalonePOM() throws MojoExecutionException {
		super.initStandalonePOM();

		initDefaultParameters();

		if (firstGoal) {
			firstGoal = false;

			List<String> _goals = new ArrayList<String>();
			_goals.addAll(session.getRequest().getGoals());
//			List<String> dependenciesGoals = getDependenciesGoals();
			List<String> dependenciesGoals = new ArrayList<String>();

			try {
				if (_goals.size() > 1) {
					for (String goal : _goals.subList(1, _goals.size())) {
						if (!dependenciesGoals.contains(goal)) {
							dependenciesGoals.add(goal);
						}
					}
				}

				if (dependenciesGoals.size() > 0) {
					for (Iterator<String> it = dependenciesGoals.iterator(); it.hasNext();) {
						String goal = (String) it.next();
						CommonDomains mojo = DomainsMojosFactory.getDomainsMojo(goal);
						mojo.setSession(session);
						mojo.initStandalonePOM();
					}
				}

				session.getRequest().getGoals().addAll(dependenciesGoals);

				try {
					if (pluginManager != null) {
						PropertiesEnforcer.enforceProperties(session, pluginManager, logger, new ArrayList<String>(), DomainsLifecycleParticipant.class, DomainsLifecycleParticipant.pluginKey); // check that all mandatory properties are correct
					}
				} catch (MavenExecutionException e) {
					throw new MojoExecutionException(e.getLocalizedMessage(), e);
				}

				session.getRequest().setGoals(_goals);
			} finally {
				session.getRequest().setGoals(_goals);
			}
		}
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Mojo mojoAnnotation = this.getClass().getAnnotation(Mojo.class);
		currentGoalName = mojoAnnotation.name();
	}

}
