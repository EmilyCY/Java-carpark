package CarParkGUIApp;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a GUL for establishing a car park
 * @author Emily Cheng103329805
 * @version 1.0
 */

public class CarParkCreation extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel prompt = new JLabel("Please enter the number of parking slots to " +
            "be created for staff and visitors (less than 1000)");
    JLabel staff = new JLabel("Staff");
    JLabel visitor = new JLabel("Visitor");
    JTextField staffField = new JTextField();
    JTextField visitorField = new JTextField();
    JButton createButton = new JButton("Create");
    JButton resetButton = new JButton("Reset");
    static int staffSlotNumber;
    static int visitorSlotNumber;
    public static final String STAFF = "s";
    public static final String VISITOR = "v";

    public static void main(String[] args) {
        new CarParkCreation();
    }


    CarParkCreation() {
        setFrame();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        pack();
    }

    public int getStaffSlotNumber() {
        return staffSlotNumber;
    }

    public int getVisitorSlotNumber() {
        return visitorSlotNumber;
    }

    public void setFrame(){
        setPreferredSize(new Dimension(800, 600));
        setLocation(400,200);
        setTitle("Establish car park");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        prompt.setBounds(100, 50,600, 60);
        staff.setBounds(100, 150, 100, 30);
        visitor.setBounds(100, 220, 100, 30);
        staffField.setBounds(150, 150, 150, 30);
        visitorField.setBounds(150, 220, 150, 30);
        createButton.setBounds(100, 300, 100, 30);
        resetButton.setBounds(400, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(prompt);
        container.add(staff);
        container.add(visitor);
        container.add(staffField);
        container.add(visitorField);
        container.add(createButton);
        container.add(resetButton);
    }

    public static CarPark establishCarPark(){
        //create new instance of car park
        CarPark carPark = new CarPark();
        String slotId;
        // for loops to add slots to car park based on entered numbers.
        for (int i = 0; i < staffSlotNumber; i++) {
            slotId = "S" + String.format("%03d",i);
            carPark.addSlot(STAFF, slotId);
        }
        for (int i = 0; i < visitorSlotNumber; i++) {
            slotId = "V" + String.format("%03d",i);
            carPark.addSlot(VISITOR, slotId);
        }
        return carPark;
    }

    public void addActionEvent() {
        createButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String staffInput;
            String visitorInput;
            staffInput = staffField.getText();
            visitorInput = visitorField.getText();
            if (staffInput.matches("(1000|[0-9]{1,3})") && visitorInput.matches("(1000|[0-9]{1,3})")) {
                JOptionPane.showMessageDialog(this, "Slots are created successfully");
                staffSlotNumber = Integer.parseInt(staffInput);
                visitorSlotNumber = Integer.parseInt(visitorInput);
                CarPark carPark = establishCarPark();
                CarParkGUI home = new CarParkGUI(carPark);
                setVisible(false);
                home.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }

        }

        if (e.getSource() == resetButton) {
            staffField.setText("");
            visitorField.setText("");
        }

    }

}


