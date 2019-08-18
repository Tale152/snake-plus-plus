package implementation.controller.application;

import design.controller.application.WorldDescriptor;

/**
 * @see WorldDescriptor
 * @author Nicola Orlando
 */
public class WorldDescriptorImpl implements WorldDescriptor, Comparable<WorldDescriptor> {

    private final String name;
    private final String description;
    private final String folderName;

    /**
     * Create a world descriptor.
     * @param name The name of the world.
     * @param description The description of the world.
     * @param folderName The folder containing the world levels.
     */
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

    @Override
    public final boolean equals(final Object arg0) {
        if (!(arg0 instanceof WorldDescriptor)) {
            return false;
        }
        return ((WorldDescriptor) arg0).getName().equals(this.name);
    }

    @Override
    public final int hashCode() {
        assert false : "hashCode not designed";
        return name.hashCode();
    }

}
