// Copyright 2007 The Apache Software Foundation
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

package org.apache.tapestry.internal.util;

import org.apache.tapestry.internal.test.InternalBaseTestCase;
import org.apache.tapestry.runtime.Component;
import org.testng.annotations.Test;

public class NotificationEventHandlerTest extends InternalBaseTestCase
{
    private static final String EVENT_TYPE = "myEventType";

    private static final String COMPLETE_ID = "foo.bar.baz";

    private static final String METHOD = "foo.components.Baz.bar()";

    @Test
    public void true_is_allowed()
    {
        Component component = newComponent();

        replay();

        NotificationEventHandler handler = new NotificationEventHandler(EVENT_TYPE, COMPLETE_ID);

        assertTrue(handler.handleResult(Boolean.TRUE, component, METHOD));

        verify();
    }

    @Test
    public void false_is_allowed()
    {
        Component component = newComponent();

        replay();

        NotificationEventHandler handler = new NotificationEventHandler(EVENT_TYPE, COMPLETE_ID);

        assertFalse(handler.handleResult(Boolean.FALSE, component, METHOD));

        verify();
    }

    @Test
    public void other_values_force_exception()
    {
        Component component = newComponent();
        String result = "*RESULT*";

        replay();

        NotificationEventHandler handler = new NotificationEventHandler(EVENT_TYPE, COMPLETE_ID);

        try
        {
            handler.handleResult(result, component, METHOD);
            unreachable();
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals(
                    ex.getMessage(),
                    "Event 'myEventType' from foo.bar.baz received an event handler method return value of *RESULT* from foo.components.Baz.bar(). "
                            + "This type of event does not support return values from event handler methods.");
        }

        verify();
    }

}
