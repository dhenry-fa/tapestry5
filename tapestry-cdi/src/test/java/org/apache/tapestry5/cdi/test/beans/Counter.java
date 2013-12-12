// Copyright 2013 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.cdi.test.beans;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Counter implements Serializable
{
	private static final long serialVersionUID = 1L;
	private AtomicInteger counter = new AtomicInteger();

	public int getCount()
	{
		return counter.get();
	}

	public void increment()
	{
		counter.incrementAndGet();
	}
}
