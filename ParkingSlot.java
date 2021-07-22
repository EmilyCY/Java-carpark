package CarParkGUIApp;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class describes parking slots in a car park
 * @author Emily Cheng 103329805
 * @version 1.0 21.3.2021
 */

public class ParkingSlot {
    public static int staffSlotCount = 0;
    public static int visitorSlotCount = 0;
    private String slotId;
    private String slotType;
    private boolean isOccupied = false;
    private Car car;
    private JButton slotButton;

    /**
     * Creates a new instance of parking slot
     */
    public ParkingSlot(String slotType, String slotId) {
        this.isOccupied = false;
        this.slotType = slotType;
        this.slotId = slotId;
        if(slotType.equals(CarParkGUI.STAFF)){
            ParkingSlot.staffSlotCount++;
        }else{
            ParkingSlot.visitorSlotCount++;
        }
        slotButton = new JButton(slotId);
        if(isOccupied){
            slotButton.setBackground(Color.RED);
        }else{
            slotButton.setBackground(Color.GREEN);
        }
    }


    public String getSlotType() {
        return slotType;
    }

    public JButton getSlotButton() {
        return slotButton;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Car getCar() {
        return car;
    }

    /**
     * check whether the slot type matches a given owner type
     * @param ownerType owner's type (staff or visitor)
     * @return true or false
     */
    public boolean isOwnerTypeMatch(String ownerType){
        return slotType.equals(ownerType);
    }

    /**
     * park a car to slot
     * @param car the car to be parked into the slot
     */
    public void addCar(Car car) {
        this.car = car;
        this.isOccupied = true;
        slotButton.setBackground(Color.RED);
    }

    /**
     * removes a car from the slot
     */
    public String removeCar(){
        String message;
        if(this.isOccupied){
            this.car = null;
            this.isOccupied = false;
            slotButton.setBackground(Color.GREEN);
            message = "This car has been removed successfully";
        }
        else{
            message = "This slot is empty. There is no car to remove.";
        }
        return message;
    }


    @Override
    public String toString() {
        if(this.isOccupied)
            return "Slot ID is: " + slotId + ", it is for " + slotType + ", and is occupied. The car registration is: "
                    + this.car.getRegistrationNum() + ", and the owner is: " + this.car.getOwnerFullName();
        else
            return "Slot ID is: " + slotId + ", it is for " + slotType + ", and is not occupied.";
    }
}

