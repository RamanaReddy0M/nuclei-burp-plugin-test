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

package io.projectdiscovery.nuclei.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

class NucleiUtilsTest {

    @SuppressWarnings("DuplicateStringLiteralInspection")
    @ParameterizedTest
    @ValueSource(strings = {"nuclei -t \"c:/directory name with space/another one/something.yaml\" -u http://localhost",
                            "nuclei -t 'c:/directory name with space/another one/something.yaml' -u http://localhost",
                            "nuclei -t 'c:\\directory\\test name with space\\another one\\something.yaml' -u http://localhost",
                            "nuclei -t \"c:\\directory\\test name with space\\another one\\something.yaml\" -u http://localhost",
                            "nuclei -t 'c:/temp/something.yaml' -u http://localhost"
    })
    void testNucleiTemplateParameterPattern(String testCase) {
        Map.of("c:\\Users\\someone\\test.yaml", "c:/Users/someone/test.yaml",
               "c:/Users/someone/test.yaml", "c:/Users/someone/test.yaml",
               "c:\\directory with spaces\\level2\\test.yaml", "\"c:/directory with spaces/level2/test.yaml\"",
               "c:/directory with spaces/level2/test.yaml", "\"c:/directory with spaces/level2/test.yaml\"",
               "/tmp/directory with spaces/test.yaml", "\"/tmp/directory with spaces/test.yaml\"",
               "/tmp/directory/test.yaml", "/tmp/directory/test.yaml")
           .forEach((input, expected) -> Assertions.assertEquals(String.format("nuclei -t %s -u http://localhost", expected), NucleiUtils.replaceTemplatePathInCommand(testCase, input)));
    }

    @Test
    void testNucleiHelpParsing() {
        final String nucleiHelpSnippet = "Nuclei is a fast, template based vulnerability scanner focusing\n" +
                                         "on extensive configurability, massive extensibility and ease of use.\n" +
                                         "\n" +
                                         "Usage:\n" +
                                         "  nuclei [flags]\n" +
                                         "\n" +
                                         "Flags:\n" +
                                         "TARGET:\n" +
                                         "   -u, -target string[]  target URLs/hosts to scan\n" +
                                         "   -l, -list string      path to file containing a list of target URLs/hosts to scan (one per line)\n" +
                                         "   -resume               Resume scan using resume.cfg (clustering will be disabled)\n" +
                                         "\n" +
                                         "TEMPLATES:\n" +
                                         "   -tu, -template-url string[]  URL containing list of templates to run\n" +
                                         "   -nt, -new-templates          run only new templates added in latest nuclei-templates release\n" +
                                         "   -validate                    validate the passed templates to nuclei\n" +
                                         "\n";

        final Map<String, String> computedCliArgumentMap = NucleiUtils.getCliArguments(Arrays.stream(nucleiHelpSnippet.split("\n")));

        final Map<String, String> expected = Stream.of(Map.entry("-u, -target target URLs/hosts to scan", "-u"),
                                                       Map.entry("-l, -list path to file containing a list of target URLs/hosts to scan (one per line)", "-l"),
                                                       Map.entry("-resume Resume scan using resume.cfg (clustering will be disabled)", "-resume"),
                                                       Map.entry("-tu, -template-url URL containing list of templates to run", "-tu"),
                                                       Map.entry("-nt, -new-templates run only new templates added in latest nuclei-templates release", "-nt"),
                                                       Map.entry("-validate validate the passed templates to nuclei", "-validate"))
                                                   .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);

        Assertions.assertEquals(expected, computedCliArgumentMap);
    }
}