/**
 * Copyright 2013 GOT5
 *
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
 */
package org.apache.tapestry5.cdi.test.beans;

import javax.ejb.Stateful;

@Stateful
public class StatefulEJBBeanImpl implements StatefulEJBBean{
	
	private int num = 0;

	@Override
	public int num() {
		return num;
	}
	@Override
	public int inc(){
		return ++num;
	}
	
	@Override
	public int reset(){
		return (num = 0);
	}
	
	
	
}
