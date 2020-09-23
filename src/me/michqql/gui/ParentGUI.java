package me.michqql.gui;

import me.michqql.gui.interfaces.IUpdate;
import me.michqql.gui.listener.InputListener;
import me.michqql.gui.widgets.Container;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class ParentGUI extends Container implements Runnable, IUpdate {

    //Thread
    private Thread thread;
    private boolean running;
    private final double UPDATE_TICKS = 60.0D; //no. of ticks per second
    private final double UPDATE_FRAMES = 60.0D; //no. of frames per second

    //Display/Window
    private String title;
    private int width, height;
    private float scale;

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private InputListener inputListener;

    private Dimension dimension;

    public ParentGUI(String title, int width, int height, float scale) {
        super(null, "master-parent-gui", 0, 0, (int) (width * scale), (int) (height * scale));
        this.title = title;
        this.width = width;
        this.height = height;
        this.scale = scale;
        start();
    }

    public synchronized void start() {
        initWindow();
        this.running = true;

        this.thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        this.running = false;
    }

    @Override
    public void run() {
        final double BILLION = 1000000000.0D;

        long now;
        long lastTime = System.nanoTime();

        final double nst = BILLION / UPDATE_TICKS; //nano seconds per tick
        final double nsf = BILLION / UPDATE_FRAMES; //nano seconds per frame

        //unprocessed nano seconds
        double unprocessedTicks = 0D;
        double unprocessedFrames = 0D;

        long timer = System.currentTimeMillis();
        int frames = 0; //frames counter
        int ticks = 0; //ticks counter

        while(running) {
            now = System.nanoTime();
            unprocessedTicks += (now - lastTime) / nst;
            unprocessedFrames += (now - lastTime) / nsf;
            lastTime = now;

            while(unprocessedTicks >= 1) {
                tick();
                ticks++;
                unprocessedTicks--;
            }

            while(unprocessedFrames >= 1) {
                render(graphics);
                frames++;
                unprocessedFrames--;
            }

            /*
            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames + ", TICKS: " + ticks);
                timer += 1000;
                frames = 0;
                ticks = 0;
            }
            */
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(Child c : getChildren()) {
            c.render(graphics);
        }

        bufferStrategy.show();
    }

    //Display/Window
    private void initWindow() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        canvas = new Canvas();
        frame = new JFrame();

        //Canvas
        canvas.setPreferredSize(getDimension());
        canvas.setMinimumSize(getDimension());
        canvas.setMaximumSize(getDimension());

        //JFrame
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        //Input Handler
        inputListener = new InputListener(this);
        canvas.addMouseListener(inputListener);
        canvas.addMouseMotionListener(inputListener);
        canvas.addMouseWheelListener(inputListener);
        canvas.addKeyListener(inputListener);

        //Graphics
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();


    }

    private Dimension getDimension() {
        return (dimension != null) ? dimension : (dimension = new Dimension((int) (width * scale), (int) (height * scale)));
    }

    /*

    private boolean running;
    private int targetFps;
    private String title;
    private int width, height;
    private float scale;

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private InputListener inputListener;

    public ParentGUI(int targetFps, String title, int width, int height, float scale) {
        super(null, "master-parent-gui", 0, 0, width, height);
        this.targetFps = targetFps;
        this.title = (title == null) ? "" : title;
        this.width = width;
        this.height = height;
        this.scale = scale;
        init();
    }

    private void init() {
        //Local Variables
        final int sW = LocationUtil.getScaledDimension(width, scale); //Scaled Width
        final int sH = LocationUtil.getScaledDimension(height, scale); //Scaled Height

        //Image
        this.image = new BufferedImage(sW, sH, BufferedImage.TYPE_INT_ARGB);

        //Canvas
        this.canvas = new Canvas();
        final Dimension dimension = new Dimension(sW, sH);
        canvas.setPreferredSize(dimension);

        //JFrame
        this.frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        //Buffer Strategy
        canvas.createBufferStrategy(2);
        this.bufferStrategy = canvas.getBufferStrategy();
        this.graphics = bufferStrategy.getDrawGraphics();

        //Input Listener
        inputListener = new InputListener(this);
        canvas.addMouseListener(inputListener);
        canvas.addMouseMotionListener(inputListener);
        canvas.addMouseWheelListener(inputListener);
        canvas.addKeyListener(inputListener);

        this.running = true;
        new Thread(this).start();
    }

    @Override
    public void render() {
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0, 100, 100);

        bufferStrategy.show();
    }

    @Override
    public void run() {
        final double BILLION = 1000000000.0D;

        long now;
        long lastTime = System.nanoTime();

        final double nsf = BILLION / targetFps; //nano seconds per frame

        //unprocessed nano seconds
        double unprocessedFrames = 0D;

        long timer = System.currentTimeMillis();
        short frames = 0;

        while(running) {
            now = System.nanoTime();
            unprocessedFrames += (now - lastTime) / nsf;
            lastTime = now;

            while(unprocessedFrames >= 1) {
                render();
                frames++;
                unprocessedFrames--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames);
                timer += 1000;
                frames = 0;
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public JFrame getFrame() {
        return frame;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public BufferStrategy getBufferStrategy() {
        return bufferStrategy;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public InputListener getInputListener() {
        return inputListener;
    }

     */
}
