package org.tframework.example.repository;

import lombok.Data;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.properties.group.Property;
import org.tframework.core.properties.group.PropertyGroup;

@Data
@Element
@PropertyGroup(name = "repository")
public class RepositoryProperties {

    @Property("file-path")
    private String filePath;

    private String separator;
}
