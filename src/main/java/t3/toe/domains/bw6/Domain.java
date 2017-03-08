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
package t3.toe.domains.bw6;

import java.io.File;

public class Domain {
	public enum DomainType {
		LOCAL,
		ENTERPRISE
	}

	private String name;
	private File home;
	private DomainType type;
	
	public Domain(String domainName) {
		this.name = domainName;
	}

	public File getHome() {
		return home;
	}
	
	public void setHome(File home) {
		this.home = home;
	}
	
	public String getName() {
		return name;
	}

	public DomainType getType() {
		return type;
	}

	public void setType(DomainType type) {
		this.type = type;
	}

}
