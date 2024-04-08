/*
 * MIT License
 *
 * Copyright (c) 2021 ProjectDiscovery, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.projectdiscovery.cve;

import java.util.Set;

/**
 * Interface to access CVE information from a specific source
 */
public interface CveInfo {

    /**
     * @return the CVE id
     */
    String getId();

    /**
     * @return the CVE description
     */
    String getDescription();

    /**
     * @return the CVE severity
     */
    String getSeverity();

    /**
     * @return the CVSS score
     */
    Double getCvssScore();

    /**
     * @return the CVSS metrics
     */
    String getCvssMetrics();

    /**
     * @return the list of references
     */
    Set<String> getReferences();

    /**
     * @return the associated CWE IDs
     */
    Set<String> getCweIds();
}
