package CarParkGUIApp;
/**
 * This class describes cars to be parked in a car park
 * @author Emily Cheng 103329805
 * @version 1.0 21.3.2021
 */

public class Car {
    private String registrationNum;
    private String ownerFullName;
    private String ownerType;

    /**
     * Creates a new instance of car
     */
    public Car(String registrationNum, String ownerFirstName, String ownerLastName, String ownerType){
        this.registrationNum = registrationNum;
        String ownerFirstName1 = ownerFirstName.substring(0, 1).toUpperCase() + ownerFirstName.substring(1).toLowerCase();
        String ownerLastName1 = ownerLastName.substring(0, 1).toUpperCase() + ownerLastName.substring(1).toLowerCase();
        this.ownerFullName = ownerFirstName1 + " " + ownerLastName1;
        this.ownerType = ownerType;
    }

    public String getRegistrationNum(){
        return this.registrationNum;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

}



