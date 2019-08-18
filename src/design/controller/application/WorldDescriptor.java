package design.controller.application;

/**
 * A class to hold some informations about a world without loading it in its entirety.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface WorldDescriptor {
    /**
     * Get the name of the world.
     * @return The name of the world.
     */
    String getName();
    /**
     * Get a small description of the world.
     * @return The world description.
     */
    String getDescription();
    /**
     * Get the name of the folder containing the world levels.
     * Equal to the name of the world file, minus the extension.
     * @return The name of the folder containing the world levels.
     */
    String getFolderName();
}
