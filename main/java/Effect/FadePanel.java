package Effect;

import javax.swing.*;
import java.awt.*;

class FadePanel extends JPanel {
    private Image image;
    private float alpha = 1f; // 初始透明度为1，表示完全不透明

    public FadePanel() {}

    public void setImage(Image image) {
        this.image = image;
        this.alpha = 0f; // 每次设置新图片时重置透明度
        repaint();
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(image, 0, 0,this.getWidth(),this.getHeight(),this);
        }
    }
}
