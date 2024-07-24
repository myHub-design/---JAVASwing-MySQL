package Project_PushBox;

import MusicPlayer.Musics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectFrame extends JFrame {
    private ArrayList<SelectPanel> selectPanels = new ArrayList<>();
    GameJFrame gameJFrame;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;
    protected int pageIndex = 0;

    public SelectFrame(GameJFrame gameJFrame) {

        this.gameJFrame = gameJFrame;

        mainPanel = new JPanel(cardLayout);
        creatPages();
        add(mainPanel);

        addPerPageButtons();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public SelectFrame() {}

    private void creatPages() {
        for (int i = 0; i < (int)(DataHouse.getLevels().size() / 20) + 1; i++) {
            SelectPanel selectPanel = new SelectPanel();
            selectPanels.add(selectPanel);
            mainPanel.add(selectPanel, "" + i);
        }
    }

    public void addPerPageButtons() {
        for (int i = 0; i < selectPanels.size(); i++) {
            SelectPanel selectPanel = selectPanels.get(i);
            selectPanel.setButton_lastEvent(this);
            selectPanel.setButton_nextEvent(this);
            if (i < selectPanels.size() - 1) {
                selectPanel.addButtons(20, i);
                for (int j = 0; j < selectPanel.getMyButtons().size(); j++) {
                    selectPanel.setButtonEvent(selectPanel.getMyButtons().get(j), gameJFrame, this ,j + i * 20);
                }
            }
            else if (i == selectPanels.size() - 1) {
                selectPanel.addButtons(DataHouse.getLevels().size() - (selectPanels.size() - 1) * 20, i);
                for (int j = 0; j < selectPanel.getMyButtons().size(); j++) {
                    selectPanel.setButtonEvent(selectPanel.getMyButtons().get(j), gameJFrame, this ,j + i * 20);
                }
            }
        }
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public ArrayList<SelectPanel> getSelectPanels() {
        return selectPanels;
    }
}
