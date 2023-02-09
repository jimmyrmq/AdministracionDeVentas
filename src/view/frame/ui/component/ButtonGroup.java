package view.frame.ui.component;

import java.util.ArrayList;
import java.util.List;

public class ButtonGroup {
    private List<ButtonTabbed> buttonTabbeds = new ArrayList<>();

    public ButtonGroup(){}

    public void add(ButtonTabbed b){
        if(b == null) {
            return;
        }

        buttonTabbeds.add(b);
        b.setGroup(this);
    }

    public void clearSelection() {
        for(ButtonTabbed b: buttonTabbeds){
            b.setPaintBackEnteredMouse(false);
        }
    }
}
