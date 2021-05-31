package towersim.util;

/**
 * Denotes an entity whose behaviour and operations can be affected by emergencies.
 * Examples of emergencies include: mechanical or software failures, etc.
 */
public interface EmergencyState {
    /** Declares a state of emergency. */
    void declareEmergency();

    /** Clears any active state of emergency */
    void clearEmergency();

    /** Returns whether or not a state of emergency is currently active
     * @return true if there is an active state of emergency, or false otherwise.
     */
    boolean hasEmergency();

}
