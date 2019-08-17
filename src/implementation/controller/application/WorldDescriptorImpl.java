package implementation.controller.application;

import design.controller.application.WorldDescriptor;

public class WorldDescriptorImpl implements WorldDescriptor, Comparable<WorldDescriptor> {

    private final String name;
    private final String description;
    private final String folderName;

    public WorldDescriptorImpl(final String name, final String description, final String folderName) {
        this.name = name;
        this.description = description;
        this.folderName = folderName;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getFolderName() {
        return folderName;
    }

    @Override
    public final int compareTo(final WorldDescriptor arg0) {
        return this.folderName.compareTo(arg0.getFolderName());
    }

}
