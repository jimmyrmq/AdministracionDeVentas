package view.frame.component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonGroup {
    private List<Button> buttons = new ArrayList<>();

    public ButtonGroup(){}

    public void add(Button b){
        if(b == null) {
            return;
        }

        buttons.add(b);
        b.setGroup(this);
    }

    public void clearSelection() {
        for(Button b:buttons){
            b.setSelected(false);
        }
    }
}
