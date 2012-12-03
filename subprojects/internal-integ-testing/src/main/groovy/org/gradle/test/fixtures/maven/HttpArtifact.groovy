/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.test.fixtures.maven

import org.gradle.test.fixtures.server.http.HttpServer
import org.gradle.util.TestFile

abstract class HttpArtifact extends HttpResource {

    HttpServer server
    String modulePath
    final Map options

    public HttpArtifact(HttpServer server, String modulePath, Map<String, ?> options = [:]) {
        this.server = server
        this.modulePath = modulePath
        this.options = options
    }

    void expectHead() {
        server.expectHead(getPath(), file)
    }

    void expectHeadMissing() {
        server.expectHeadMissing("$modulePath/${getMissingArtifactName()}")
    }

    void expectGet() {
        server.expectGet(getPath(), file)
    }

    void expectGetMissing() {
        server.expectGetMissing("$modulePath/${getMissingArtifactName()}")
    }

    void expectSha1GetMissing() {
        server.expectGetMissing("$modulePath/${missingArtifactName}.sha1")
    }

    void expectSha1Get() {
        server.expectGet(getArtifactSha1Path(), getSha1File())
    }

    String getArtifactSha1Path() {
        "${getPath()}.sha1"
    }

    protected String getPath() {
        "${modulePath}/${file.name}"
    }

    abstract File getSha1File();

    abstract TestFile getFile();

    protected abstract String getMissingArtifactName();
}