package towersim.aircraft;

/** Stores information about particular models of aircraft
 */
public enum AircraftCharacteristics {
    /** Narrow-body twin-jet airliner. */
    AIRBUS_A320(AircraftType.AIRPLANE, 42600, 27200, 150, 0),

    /** Wide-body quad-jet freighter. */
    BOEING_747_8F(AircraftType.AIRPLANE, 197131, 226117, 0, 137756),

    /** Four-seater light helicopter. */
    ROBINSON_R44(AircraftType.HELICOPTER, 658, 190, 4, 0),

    /** Long range, wide-body twin-jet airliner. */
    BOEING_787(AircraftType.AIRPLANE, 119950, 126206, 242, 0),

    /** Twin-jet regional airliner. */
    FOKKER_100(AircraftType.AIRPLANE, 24375, 13365, 97, 0),

    /** Twin-engine heavy-lift helicopter. */
    SIKORSKY_SKYCRANE(AircraftType.HELICOPTER, 8724, 3328, 0, 9100);

    /** Type of aircraft */
    public final AircraftType type;

    /** Weight of aircraft with no load or fuel in kilograms */
    public final int emptyWeight;

    /** Maximum amount of freight able to be carried in kilograms */
    public final int freightCapacity;

    /** Maximum amount of fuel able to be carried in kilograms */
    public final double fuelCapacity;

    /** Maximum number of passengers to be carried */
    public final int passengerCapacity;

    private AircraftCharacteristics(AircraftType type, int emptyWeight, double fuelCapacity,
                                    int passengerCapacity, int freightCapacity) {
        this.type = type;
        this.emptyWeight = emptyWeight;
        this.fuelCapacity = fuelCapacity;
        this.passengerCapacity = passengerCapacity;
        this.freightCapacity = freightCapacity;

    }
}



