package com.postoGasolina.model.validacoes;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

/**
 * ComboBox with autofill feature.
 *
 * @param <T> the item type
 */
public class AutoFillComboBox<T> extends ComboBox<T> {

    private final List<T> unfilteredList = new ArrayList<>();
    private final double PREF_HEIGHT = 21.0d;
    private boolean onlyAllowPredefinedItems;

    /**
     * Creates a new {@link AutoFillComboBox}.
     */
    public AutoFillComboBox() {
        super();
        setPrefHeight(PREF_HEIGHT);
        this.setEditable(true);
        this.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    hide();
                    event.consume();

                }
                if (event.getCode() == KeyCode.RIGHT) {
                    hide();
                    event.consume();

                }
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    event.consume();
                }
            }
        });

        final KeyCombination keyCombination = KeyCombination.keyCombination("Ctrl+A");
        this.getEditor().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Forcefully selecting all text in the textfield on click on Ctrl+A when the list view is open
                if (keyCombination.match(event)) {
                    getEditor().selectAll();
                    return;
                }
                // [SBK] I removed the space as that can be a valid character sometimes
                if (!event.getCode().equals(KeyCode.LEFT) && !event.getCode().equals(KeyCode.RIGHT)
                        && !event.getCode().equals(KeyCode.ENTER)) {
                    show();
                }
                if (!(event.getCode().equals(KeyCode.TAB) || event.getCode().equals(KeyCode.CONTROL) || event.getCode().equals(KeyCode.UP) || event
                        .getCode().equals(KeyCode.DOWN))) {
                    // don't filter on tab,control, up and down
                    filterBasedOnString(getEditor().getText());
                }
            }
        });

        // scrolling the scrollbar to the selected item
        this.setOnShown(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // Work around; need to check for proper solution.
                final int selectedIndex = getSelectionModel().getSelectedIndex();
                final ComboBoxListViewSkin<?> skin = (ComboBoxListViewSkin<?>) getSkin();
                skin.getListView().scrollTo(selectedIndex < 0 ? 0 : selectedIndex);

            }
        });
        onlyAllowPredefinedItems = false;
    }

    /**
     * Set to add only the values from the list and not the free text values.
     *
     * @param onlyAllowPredefinedItems
     */
    public void setOnlyAllowPredefinedItems(boolean onlyAllowPredefinedItems) {
        this.onlyAllowPredefinedItems = onlyAllowPredefinedItems;
    }

    /**
     * Checks whether free text values are allowed or not.
     *
     * @return
     */
    public boolean isOnlyAllowPredefinedItems() {
        return onlyAllowPredefinedItems;
    }

    /**
     * Filters the list from "unfilteredList" based on the entered string and set the resultant list to the combobox.
     * @param newval 
     */
    protected void filterBasedOnString(String newval) {
        if (newval == null || newval.equals("")) {
            super.getItems().setAll(unfilteredList);
        } else {
            int caretPosition = super.getEditor().getCaretPosition();
            super.getItems().clear();
            for (T item : unfilteredList) {
                String str = "";
                if (getConverter() == null) {
                    str = item.toString().toLowerCase();
                } else {
                    str = getConverter().toString(item).toLowerCase();
                }

                boolean containsParts = true;
                final String[] splitted = newval.toLowerCase().split("\\s+");
                for (final String splitPart : splitted) {
                    if (!splitPart.isEmpty() && !str.contains(splitPart)) {
                        containsParts = false;
                    }
                }
                if (containsParts) {
                    super.getItems().add(item);
                }

            }
            // if a value was selected and then removed the string is cleared which shouldn't happen
            super.getEditor().setText(newval);
            super.getEditor().positionCaret(caretPosition);
        }
    }

    /**
     * Clears the selection of the combobox.
     */
    public void clearSelection() {
        super.getSelectionModel().clearSelection();
        super.getEditor().setText("");
        super.getEditor().clear();
    }

    /**
     * Clears all the items in the combobox.
     */
    public void clearItems() {
        unfilteredList.clear();
        super.getItems().clear();
    }

    /**
     * setting items to combobox the method is renamed from setItems() to
     * setRecords() as setItems() method is a final method in ComboBox class, so
     * can not override it.
     *
     * @param list
     */
    public void setRecords(List<T> list) {
        unfilteredList.clear();
        unfilteredList.addAll(list);
        super.getItems().setAll(list);
    }

    /**
     * Returns the unfiltered list of the combo box.
     *
     * @return
     */
    public List<T> getUnfilteredList() {
        return unfilteredList;
    }

}

