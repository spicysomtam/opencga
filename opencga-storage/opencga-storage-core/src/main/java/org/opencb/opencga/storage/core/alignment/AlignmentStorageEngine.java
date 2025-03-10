/*
 * Copyright 2015-2017 OpenCB
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

package org.opencb.opencga.storage.core.alignment;

import org.opencb.opencga.core.config.storage.StorageConfiguration;
import org.opencb.opencga.storage.core.StorageEngine;

/**
 * Created by pfurio on 07/11/16.
 */
public abstract class AlignmentStorageEngine extends StorageEngine<AlignmentDBAdaptor> {

    public AlignmentStorageEngine() {
    }

    public AlignmentStorageEngine(String storageEngineId, StorageConfiguration configuration) {
        super(storageEngineId, configuration);
    }

}
