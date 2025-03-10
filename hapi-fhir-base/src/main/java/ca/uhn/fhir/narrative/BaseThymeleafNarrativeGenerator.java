package ca.uhn.fhir.narrative;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 - 2022 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative2.NarrativeTemplateManifest;
import ca.uhn.fhir.narrative2.ThymeleafNarrativeGenerator;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.io.IOException;
import java.util.List;

public abstract class BaseThymeleafNarrativeGenerator extends ThymeleafNarrativeGenerator {

	private boolean myInitialized;

	/**
	 * Constructor
	 */
	protected BaseThymeleafNarrativeGenerator() {
		super();
	}

	@Override
	public boolean populateResourceNarrative(FhirContext theFhirContext, IBaseResource theResource) {
		if (!myInitialized) {
			initialize();
		}
		super.populateResourceNarrative(theFhirContext, theResource);
		return false;
	}

	protected abstract List<String> getPropertyFile();

	private synchronized void initialize() {
		if (myInitialized) {
			return;
		}

		List<String> propFileName = getPropertyFile();
		try {
			NarrativeTemplateManifest manifest = NarrativeTemplateManifest.forManifestFileLocation(propFileName);
			setManifest(manifest);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}

		myInitialized = true;
	}


}
