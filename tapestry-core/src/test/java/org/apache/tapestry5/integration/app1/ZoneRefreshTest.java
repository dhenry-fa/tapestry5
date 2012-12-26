// Copyright 2011, 2012 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.integration.app1;

import org.apache.tapestry5.integration.TapestryCoreTestCase;
import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class ZoneRefreshTest extends TapestryCoreTestCase
{

    @Test
    public void test_if_zone_with_event_handler_returning_void_works() throws Exception
    {
        openBaseURL();

        waitForPageInitialized();

        clickAndWait("link=Zone Refresh With Event Handler Returning Void");

        sleep(AJAX_WAIT_TIME);

        checkZoneValues("zone", 3);
    }

    @Test
    public void test_if_zone_with_event_handler_returning_zone_works() throws Exception
    {
        openBaseURL();

        waitForPageInitialized();

        clickAndWait("link=Zone Refresh With Event Handler Returning Zone");

        sleep(AJAX_WAIT_TIME);

        checkZoneValues("zone", 3);
    }

    private void checkZoneValues(String zone, int times) throws Exception
    {
        // Wait until Prototype is loaded ...
        waitForCondition("selenium.browserbot.getCurrentWindow().Ajax", "20000");

        for (int i = 1; i <= times; ++i)
        {
            // Wait for ajax call to begin
            waitForCondition("selenium.browserbot.getCurrentWindow().Ajax.activeRequestCount != 0", "20000");

            // Wait for ajax call from end
            waitForCondition("selenium.browserbot.getCurrentWindow().Ajax.activeRequestCount == 0", "20000");

            // Check the value changed
            assertText(zone, String.valueOf(i));
        }
    }

}
