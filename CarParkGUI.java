package CarParkGUIApp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a GUL for car park system
 * @author Emily Cheng103329805
 * @version 1.0
 */

public class CarParkGUI extends JFrame{
    Container container = getContentPane();
    JPanel slotsPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(slotsPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    final int WIDTH = 1000;
    final int HEIGHT = 800;
    CarPark carPark;
    public static final String STAFF = "s";
    public static final String VISITOR = "v";

    public CarParkGUI(CarPark carPark) {
        this.carPark = carPark;
        setFrame();
        makeMenuBar();
        displaySlots();
        pack();
    }

    private void setFrame(){
        setTitle("Car Park");
        setBounds(400, 200, WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenuItem parkCar = new JMenuItem("Park car");
        menuBar.add(parkCar);
        JMenuItem findCar = new JMenuItem("Find car");
        menuBar.add(findCar);
        JMenuItem removeCar = new JMenuItem("Remove car");
        menuBar.add(removeCar);
        JMenuItem addSlot = new JMenuItem("Add slot");
        menuBar.add(addSlot);
        JMenuItem deleteSlot = new JMenuItem("Delete slot");
        menuBar.add(deleteSlot);

        parkCar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                parkCar();
            }
        });
        findCar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                findCar();
            }
        });
        removeCar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCar();
            }
        });
        addSlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSlot();
            }
        });
        deleteSlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSlot();
            }
        });

    }

    private void displaySlots(){
        for(ParkingSlot parkingSlot: carPark.getParkingSlots()) {
            JButton button = parkingSlot.getSlotButton();
            slotsPanel.add(button);
        }
        slotsPanel.setLayout(new GridLayout(0,5));
        container.add(scrollPane);
        setVisible(true);
    }

    private void parkCar(){
        String ownerType = VISITOR;
        String errorMessage = "";
        JTextField regoField = new JTextField(10);
        JTextField slotIdField = new JTextField(10);
        JTextField fnameField = new JTextField(10);
        JTextField lnameField = new JTextField(10);
        JRadioButton visitorType = new JRadioButton("Visitor",true);
        JRadioButton staffType = new JRadioButton("Staff");
        ButtonGroup type = new ButtonGroup();
        type.add(visitorType);
        type.add(staffType);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Car registration:"));
        myPanel.add(regoField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Car owner's first name:"));
        myPanel.add(fnameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Car owner's last name:"));
        myPanel.add(lnameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Slot ID:"));
        myPanel.add(slotIdField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Parking slot for:"));
        myPanel.add(visitorType);
        myPanel.add(staffType);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
        int response = JOptionPane.showConfirmDialog(null, myPanel, "Park Car", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.OK_OPTION){
            String regoInput = regoField.getText();
            String slotIdInput = slotIdField.getText();
            String ownerFirstName = fnameField.getText();
            String ownerLastName = lnameField.getText();
            if(visitorType.isSelected()){
                ownerType = VISITOR;
            }else if(staffType.isSelected()){
                ownerType = STAFF;
            }
            errorMessage = checkRego(regoInput) + checkSlotId(slotIdInput,ownerType);

            if(!errorMessage.equals("")){
                JOptionPane.showMessageDialog(this, errorMessage);
            }else{
                Car car = new Car(regoInput, ownerFirstName, ownerLastName, ownerType);
                ParkingSlot slot = carPark.findSlotById(slotIdInput);
                slot.addCar(car);
                displaySlots();
                JOptionPane.showMessageDialog(this, "The car is parked successfully.");
            }
        }
    }


    private String checkRego(String regoInput){
        String errorMessage = "";
        if(regoInput.matches("[A-Z]\\d{5}")){
            String carRegistration = regoInput;
            if(carPark.findSlotByRegistration(carRegistration) != null){
               errorMessage = "This car has already been parked in slot. " + carPark.findSlotByRegistration(carRegistration).getSlotId();
            }
        }else {
            errorMessage = "Invalid registration number. ";
        }
        return errorMessage;
    }

    //private String getOwnerType(){

    //}

    private String checkSlotId(String slotIdInput, String ownerType){
        String errorMessage="";
        ParkingSlot slot = carPark.findSlotById(slotIdInput);

        if (slot == null) {
            errorMessage = "Slot ID not found in the system. ";
        } else if (slot.isOccupied()) {
            errorMessage = "Selected slot is already occupied. ";
        } else if(!slot.isOwnerTypeMatch(ownerType)){
            errorMessage = "Visitor cannot use staff car slots and staff cannot use visitor car slots. ";
        }
        return errorMessage;
    }


    private void findCar(){
        String message = "";
        JTextField regoField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please enter the car registration:"));
        myPanel.add(regoField);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
        int response = JOptionPane.showConfirmDialog(null, myPanel, "Find Car", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.OK_OPTION) {
            String regoInput = regoField.getText();
            if(regoInput.matches("[A-Z]\\d{5}")){
                ParkingSlot slot = carPark.findSlotByRegistration(regoInput);
                if(slot != null){
                    message = "This car has already been parked in slot " + slot.getSlotId() +
                            ". The the owner of the car is " + slot.getCar().getOwnerFullName();
                }else{
                    message = "This car is not parked in the car park. ";
                }
            }else{
                message = "Invalid registration number.";
            }
            JOptionPane.showMessageDialog(this, message);
        }

    }


    private void removeCar(){
        String message = "";
        JTextField regoField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please enter the car registration:"));
        myPanel.add(regoField);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
        int response = JOptionPane.showConfirmDialog(null, myPanel, "Remove Car", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.OK_OPTION) {
            String regoInput = regoField.getText();
            if(regoInput.matches("[A-Z]\\d{5}")){
                ParkingSlot slot = carPark.findSlotByRegistration(regoInput);
                if(slot != null){
                    message = slot.removeCar();
                }else{
                    message = "This car is not parked in the car park. ";
                }
            }else{
                message = "Invalid registration number.";
            }
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void addSlot(){
        String ownerType = VISITOR;
        String message = "";

        JTextField slotIdField = new JTextField(10);
        JRadioButton visitorType = new JRadioButton("Visitor",true);
        JRadioButton staffType = new JRadioButton("Staff");
        ButtonGroup type = new ButtonGroup();
        type.add(visitorType);
        type.add(staffType);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please enter slot ID:"));
        myPanel.add(slotIdField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Add parking slot for:"));
        myPanel.add(visitorType);
        myPanel.add(staffType);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        int response = JOptionPane.showConfirmDialog(null, myPanel, "Add Slot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.OK_OPTION){
            if(visitorType.isSelected()){
                ownerType = VISITOR;
            }else if(staffType.isSelected()){
                ownerType = STAFF;
            }

            String slotIdInput = slotIdField.getText();
            if(slotIdInput.matches("[VS]\\d{3}")){
                message = carPark.addSlot(ownerType, slotIdInput);
                JOptionPane.showMessageDialog(this, message);
                displaySlots();
            }else{
                JOptionPane.showMessageDialog(this,
                        "Invalid parking slot ID. Parking Slot ID should start with \"V\" or \"S\" followed by 3 digits.");
            }
        }
    }

    private void deleteSlot(){
        String message = "";
        JTextField slotIdField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please enter slot ID:"));
        myPanel.add(slotIdField);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        int response = JOptionPane.showConfirmDialog(null, myPanel, "Delete Slot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(response == JOptionPane.OK_OPTION){
            String slotIdInput = slotIdField.getText();
            message = carPark.deleteSlotById(slotIdInput);
            JOptionPane.showMessageDialog(this, message);
            slotsPanel.removeAll();
            displaySlots();
        }
    }

    private void cleanPanel(){

    }

}
