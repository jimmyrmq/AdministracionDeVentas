package view.frame.ui.component;

import java.util.ArrayList;
import java.util.List;

public class SelectedColorGroup {

    private List<SelectedColor> buttonTabbeds = new ArrayList<>();

    public SelectedColorGroup(){}

    public void add(SelectedColor b){
        if(b == null) {
            return;
        }

        buttonTabbeds.add(b);
        b.setGroup(this);
    }

    public void clearSelection() {
        for(SelectedColor b: buttonTabbeds){
            b.setSelected(false);
        }
    }

    public SelectedColor getButtonSelected(){
        SelectedColor sc = null;

        cont:for(SelectedColor b: buttonTabbeds){
            if(b.isSelected()){
                sc = b;
                break cont;
            }
        }
        return sc;
    }
}
