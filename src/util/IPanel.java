package util;

import javax.swing.*;

public interface IPanel {
    void init();
    String getTitle();
    JPanel getPanel();
    void close();
    boolean isOpen();
    boolean isClosed();
    void runBeforeClose();
}