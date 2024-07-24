package Project_PushBox;

import MusicPlayer.Musics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;

public class GamePane extends JPanel {

    MyObservable myObservable = new MyObservable();

    public MyObservable getMyObservable() {
        return myObservable;
    }

    public void setMyObservable(MyObservable myObservable) {
        this.myObservable = myObservable;
    }

    // 游戏地图类型实例
    private GameMapKind gameMapKind;
    // 玩家实例
    private Player player = new Player();

    JLabel stepsLabel = new JLabel("Steps");//显示步数

    JLabel first_steps_Label = new JLabel("最佳步数:");

    // 默认构造方法，调用 runPane 方法初始化面板
    public GamePane() {
        runPane();
    }

    // 带有地图类型参数的构造方法，使用工厂方法创建地图类型实例
    public GamePane(String MapKind) {
        gameMapKind = GameMapFactory.createGameMapKind(MapKind);
        runPane();
    }

    // 带有地图类型和具体地图布局的构造方法，使用工厂方法创建地图类型实例并设置地图布局
    public GamePane(String MapKind, int[][] map, int map_id) {
        gameMapKind = GameMapFactory.createGameMapKind(MapKind);
        gameMapKind.setMap(map);
        runPane();
    }

    // 初始化面板
    public void runPane() {
        // 查找并设置玩家位置
        player.findPlayer(getMapData());
        // 设置面板的首选大小
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(getMapData()[0].length * GameJFrame.gameSize, (getMapData().length + 1) * GameJFrame.gameSize));
        setFocusable(true);
        // 添加键盘事件监听器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int key = e.getKeyCode();
                        // 根据按键方向移动玩家
                        if (key == KeyEvent.VK_UP) {
                            if (player.movePlayer(-1, 0, getMapData())) gameMapKind.addBoxAndPlayerPoint();
                            gameMapKind.resetEndPoint();
                        } else if (key == KeyEvent.VK_DOWN) {
                            if (player.movePlayer(1, 0, getMapData())) gameMapKind.addBoxAndPlayerPoint();
                            gameMapKind.resetEndPoint();
                        } else if (key == KeyEvent.VK_LEFT) {
                            if (player.movePlayer(0, -1, getMapData())) gameMapKind.addBoxAndPlayerPoint();
                            gameMapKind.resetEndPoint();
                        } else if (key == KeyEvent.VK_RIGHT) {
                            if (player.movePlayer(0, 1, getMapData())) gameMapKind.addBoxAndPlayerPoint();
                            gameMapKind.resetEndPoint();
                        }
                        new Thread(Musics.clickMusic.get(1)).start();
                        stepsLabel.setText("Steps: " + gameMapKind.getHh());

                        revalidate(); // 确保面板重新布局
                        repaint(); // 重新绘制面板
                        // 检查玩家是否胜利
                        if (gameMapKind.judgVictory()) {
                            myObservable.setGameMapKind(gameMapKind);
                        }
                    }
                });
            }
        });

        // 确保标签被添加到面板中
        setLayout(null);
        stepsLabel.setBounds( 0,GameJFrame.gameSize*10, GameJFrame.gameSize*2, GameJFrame.gameSize);
        first_steps_Label.setBounds(350,GameJFrame.gameSize*10, GameJFrame.gameSize*4, GameJFrame.gameSize);
        add(first_steps_Label);
        add(stepsLabel);
    }

    // 选择地图类型
    public void choiceMap(String MapKind) {
        gameMapKind = GameMapFactory.createGameMapKind(MapKind);
    }

    // 获取当前地图数据
    public int[][] getMapData() {
        int[][] map = gameMapKind.getMap();
        return map;
    }

    // 获取玩家实例
    public Player getPlayer() {
        return player;
    }

    // 设置玩家实例
    public void setPlayer(Player player) {
        this.player = player;
    }

    // 获取游戏地图类型实例
    public GameMapKind getGameMapKind() {
        return gameMapKind;
    }

    // 设置游戏地图类型实例
    public void setGameMapKind(GameMapKind gameMapKind) {
        this.gameMapKind = gameMapKind;
    }

    // 重写 paintComponent 方法，自定义绘制面板内容
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] map = getMapData();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        int x = 0, y = 0;
        for (x = 0; x < map.length; x++) {
            for (y = 0; y < map[0].length; y++) {
                int tile = map[x][y];
                g.drawImage(gameMapKind.getBackground(), y * GameJFrame.gameSize, x * GameJFrame.gameSize, this);
                if (tile == 1) {
                    g.drawImage(gameMapKind.getWallImage(), y * GameJFrame.gameSize, x * GameJFrame.gameSize, this);
                } else if (tile == 3) {
                    g.drawImage(gameMapKind.getBoxImage(), y * GameJFrame.gameSize, x * GameJFrame.gameSize, this);
                } else if (tile == 4) {
                    g.drawImage(gameMapKind.getGoalImage(), y * GameJFrame.gameSize, x * GameJFrame.gameSize, this);
                } else if (tile == 2) {
                    g.drawImage(gameMapKind.getPlayerImage(), y * GameJFrame.gameSize, x * GameJFrame.gameSize, this);
                }
            }
        }
    }

    public JLabel getFirst_steps_Label() {
        return first_steps_Label;
    }

    public void setFirst_steps_Label(JLabel first_steps_Label) {
        this.first_steps_Label = first_steps_Label;
    }

    // 重写 addNotify 方法，确保面板获得焦点
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
