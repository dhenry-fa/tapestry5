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

package org.apache.tapestry.internal.services;

import static java.lang.String.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.apache.tapestry.ioc.services.ClassFab;
import org.apache.tapestry.ioc.services.ClassFactory;
import org.apache.tapestry.ioc.services.MethodSignature;
import org.apache.tapestry.services.Environment;
import org.apache.tapestry.services.EnvironmentalShadowBuilder;

public class EnvironmentalShadowBuilderImpl implements EnvironmentalShadowBuilder
{
    private final ClassFactory _classFactory;

    private final Environment _environment;

    public EnvironmentalShadowBuilderImpl(final ClassFactory classFactory,
            final Environment environment)
    {
        _classFactory = classFactory;
        _environment = environment;
    }

    public <T> T build(Class<T> serviceType)
    {
        // TODO: Check that serviceType is an interface?

        Class proxyClass = buildProxyClass(serviceType);

        try
        {
            Constructor cons = proxyClass.getConstructors()[0];

            Object raw = cons.newInstance(_environment, serviceType);

            return serviceType.cast(raw);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private Class buildProxyClass(Class serviceType)
    {
        ClassFab classFab = _classFactory.newClass(serviceType);

        classFab.addField("_environment", Environment.class);
        classFab.addField("_serviceType", Class.class);

        classFab.addConstructor(new Class[]
        { Environment.class, Class.class }, null, "{ _environment = $1; _serviceType = $2; }");

        classFab.addMethod(Modifier.PRIVATE, new MethodSignature(serviceType, "_delegate", null,
                null), "return ($r) _environment.peekRequired(_serviceType); ");

        classFab.proxyMethodsToDelegate(serviceType, "_delegate()", format(
                "<EnvironmentalProxy for %s>",
                serviceType.getName()));

        return classFab.createClass();
    }

}
