package implementation.controller.application;

import design.controller.application.WorldDescriptor;

public class WorldDescriptorImpl implements WorldDescriptor, Comparable<WorldDescriptor> {

    private final String name;
    private final String description;
    private final String folderName;

    public WorldDescriptorImpl(String name, String description, String folderName) {
        this.name = name;
        this.description = description;
        this.folderName = folderName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFolderName() {
        return folderName;
    }

    @Override
    public int compareTo(WorldDescriptor arg0) {
        return this.folderName.compareTo(arg0.getFolderName());
    }

}
