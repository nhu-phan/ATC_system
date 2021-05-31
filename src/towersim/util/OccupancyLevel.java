package towersim.util;

/**
 * A class that has an inherent capacity and a current level of loading relative to that capacity.
 * Loading level is called occupancy level and ranges from 0 (completely empty) to 100 (completely
 * at full capacity)
 */
public interface OccupancyLevel {

    /**
     * Get the current occupancy level of an entity as a percentage.
     * @return occupancy level, from 0 to 100.
     */
    int calculateOccupancyLevel();


}
