package CarParkGUIApp;
import java.util.ArrayList;

/**
 * This class describes car parks
 * @author Emily Cheng 103329805
 * @version 1.0 21.3.2021
 */

public class CarPark {
    private ArrayList<ParkingSlot> parkingSlots;

    /**
     * Creates a new instance of car park
     */
    public CarPark(){
        ParkingSlot.staffSlotCount = 0;
        ParkingSlot.visitorSlotCount = 0;
        parkingSlots = new ArrayList<>();
    }

    /**
     * check whether the number of slots of a given type in the car park has reached the maximum number.
     * @param slotType type of slot to be checked
     * @return true or false
     */
    public boolean hasCapacity(String slotType){
        boolean hasCapacity = false;

        if(slotType.equals(CarParkGUI.STAFF) && ParkingSlot.staffSlotCount < 999){
            hasCapacity = true;
        }

        if(slotType.equals(CarParkGUI.VISITOR) && ParkingSlot.visitorSlotCount < 999){
            hasCapacity = true;
        }
        return hasCapacity;
    }

    /**
     * create and add a slot to the car park
     * @param slotType the type pf the slot
     * @return error message
     */

    public String addSlot(String slotType, String slotId){
        String message;
        ParkingSlot slot = findSlotById(slotId);
        if(slot != null){
            message = "This slot has already existed.";
        }else if(hasCapacity(slotType)){
            if(slotId.matches("[V]\\d{3}") && slotType.equals(CarParkGUI.VISITOR)){
                ParkingSlot newSlot = new ParkingSlot(slotType, slotId);
                this.parkingSlots.add(newSlot);
                message = "Slot is added successfully.";
            }else if(slotId.matches("[S]\\d{3}") && slotType.equals(CarParkGUI.STAFF)){
                ParkingSlot newSlot = new ParkingSlot(slotType, slotId);
                this.parkingSlots.add(newSlot);
                message = "Slot is added successfully.";
            }else{
                message = "Slot ID dose not match slot type.";
            }
        }else{
            message = "Adding slot unsuccessful. Car park has reached maximum capacity of 1000 slots for each type.";
        }
        return message;
    }

    /**
     * remove a slot by a given slot ID
     * @param slotId slot ID of the slot to be deleted
     */
    public String deleteSlotById(String slotId){
        String message;
        ParkingSlot slot = findSlotById(slotId);
        if(slot == null) {
            message = "Slot ID not found.";
        }
        else if(slot.isOccupied()){
            message = "This parking slot is currently occupied, you cannot delete it.";
        }
        else{
            slot.getSlotButton().setVisible(false);
            this.parkingSlots.remove(slot);
            message = "Slot " + slot.getSlotId() + " has been removed successfully.";
        }
        return message;
    }

    /**
     * find a slot with a given slot ID
     * @param slotId the slot ID of slo to be found
     * @return the slot object found
     */
    public ParkingSlot findSlotById(String slotId){
        ParkingSlot slotFound = null;
        for(ParkingSlot parkingSlot: this.parkingSlots){
            if(parkingSlot.getSlotId().equals(slotId)){
                slotFound = parkingSlot;
                break;
            }
        }
        return slotFound;
    }

    /**
     * find a slot giving the car registration number of the card parked in that slot
     * @param registrationNum car registration number
     * @return the slot object found
     */
    public ParkingSlot findSlotByRegistration(String registrationNum){
        ParkingSlot slotFound = null;
        for(ParkingSlot parkingSlot: this.parkingSlots){
            if(parkingSlot.getCar()!=null && parkingSlot.getCar().getRegistrationNum().equals(registrationNum)){
                slotFound = parkingSlot;
                break;
            }
        }
        return slotFound;
    }

    /**
     * prints all parking slots
     */
    public void listAllSlots(){
        for(ParkingSlot parkingSlot: this.parkingSlots){
            System.out.println(parkingSlot.toString());
        }
    }

    public ArrayList<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }
}
