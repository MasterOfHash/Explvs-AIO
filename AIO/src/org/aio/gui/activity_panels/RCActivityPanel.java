package org.aio.gui.activity_panels;

import org.aio.activities.activity.Activity;
import org.aio.activities.skills.runecrafting.*;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class RCActivityPanel implements ActivityPanel {

    private JPanel mainPanel;
    private JComboBox<Altar> altarSelector;
    private JComboBox<RunecraftingType> rcTypeSelector;
    private JComboBox<EssenceType> essenceTypeSelector;
    private JCheckBox useDuelRingBox;

    public RCActivityPanel() {
        mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        mainPanel.add(new JLabel("Altar:"));
        altarSelector = new JComboBox<>(Altar.values());
        mainPanel.add(altarSelector);

        mainPanel.add(new JLabel("Type:"));
        rcTypeSelector = new JComboBox<>(RunecraftingType.values());
        mainPanel.add(rcTypeSelector);

        JPanel essencePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        essencePanel.add(new JLabel("Essence type: "));
        essenceTypeSelector = new JComboBox<>(EssenceType.values());
        essencePanel.add(essenceTypeSelector);
        mainPanel.add(essencePanel);

        useDuelRingBox = new JCheckBox("Use ring of dueling");
        mainPanel.add(useDuelRingBox);
        useDuelRingBox.setVisible(false);

        altarSelector.addActionListener(e -> {
            Altar newValue = (Altar) altarSelector.getSelectedItem();
            if (newValue == Altar.FIRE) {
                useDuelRingBox.setVisible(true);
            } else {
                useDuelRingBox.setVisible(false);
            }
        });

        rcTypeSelector.addActionListener(e -> {
            if (rcTypeSelector.getSelectedItem() == RunecraftingType.RUNES) {
                essencePanel.setVisible(true);
            } else {
                essencePanel.setVisible(false);
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public Activity toActivity() {
        RunecraftingType runecraftingType = (RunecraftingType) rcTypeSelector.getSelectedItem();

        if (runecraftingType == RunecraftingType.RUNES) {
            Altar selectedAltar = (Altar) altarSelector.getSelectedItem();
            EssenceType essenceType = (EssenceType) essenceTypeSelector.getSelectedItem();

            if (selectedAltar == Altar.FIRE) {
                return new FireAltarRunecraftingActivity(selectedAltar, essenceType, useDuelRingBox.isSelected());
            }
            return new RunecraftingActivity(selectedAltar, essenceType);
        } else {
            return new TiaraMakingActivity((Altar) altarSelector.getSelectedItem());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("altar", ((Altar) altarSelector.getSelectedItem()).name());
        jsonObject.put("type", ((RunecraftingType) rcTypeSelector.getSelectedItem()).name());
        jsonObject.put("essence_type", ((EssenceType) essenceTypeSelector.getSelectedItem()).name());
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) {
        altarSelector.setSelectedItem(Altar.valueOf((String) jsonObject.get("altar")));
        if (jsonObject.containsKey("type")) {
            rcTypeSelector.setSelectedItem(RunecraftingType.valueOf((String) jsonObject.get("type")));
            essenceTypeSelector.setSelectedItem(EssenceType.valueOf((String) jsonObject.get("essence_type")));

        }
    }
}
