package sarahag;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class panel extends JPanel implements Runnable, MouseListener {

    ImageIcon img;
    JLabel lab, lab2,lab3;
    int ro, rotimes, times, degree, ms, rospeed;
    int labx, laby, lab2x,lab3x;
    boolean down, down2,down3;
    Thread my;
    String star;
    int sx, sy;
    int sin,cos;
    int d ;
    int C_x ,size,C_y,d_st;//for the color angles
    players dizi;
    JPopupMenu mm;
    JMenuItem op1,op2;
    panel() {
        this.setFocusable(true);
        my = new Thread(this);
        dizi = new players(10);
        labx = 675;
        laby = 700;
        lab2x = 1300;
        lab3x = 20;
        img = new ImageIcon("imgs//bottle.png");
        lab = new JLabel();
        lab2 = new JLabel();
        lab3 = new JLabel();
        lab.addMouseListener(this);
        lab2.addMouseListener(this);
        lab3.addMouseListener(this);
        this.addMouseListener(this);
        this.setBackground(Color.LIGHT_GRAY);

        ro = 0;
        rospeed = 0;
        rotimes = (int) (Math.random() * 10);
        if (rotimes < 5) {
            rotimes = rotimes + 4;
        }
        degree = (int) (Math.random() * 360);
        times = 0;
        ms = 0;
        star = "";
        sx = 1400;
        sy = 700;
        
        size = 1000;
        C_x =750-(size/2);
        C_y = 400-(size/2);
        down = false;
        mm = new JPopupMenu("welcome");
        p_menu(mm);
   
        
    }
    public  void p_menu(JPopupMenu n){
         op1 = new JMenuItem("change background");
         op2 = new JMenuItem("clear all");
        //JMenuItem op3 = new JMenuItem("hellllo");
        n.add(op1);
        n.add(op2);
        op1.addMouseListener(this);
        op2.addMouseListener(this);
        this.add(n);
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
         d = 0;
        Graphics2D g2 = (Graphics2D) g;
        
        for (int i = 0; i < dizi.top+1; i++) {
            d = (360 / (dizi.top+1 ));
            d_st =d*i+(d/2);
            g.setColor(dizi.dizi[i].getColor());
            if (i==6) {
                g2.fillArc(C_x , C_y , size, size, d*i, d+3);
            }else
            {g2.fillArc(C_x , C_y , size, size, d*i, d);}
            
            sin = -(int)(350*Math.sin(Math.toRadians(d_st)));
            cos = (int)(350*Math.cos(Math.toRadians(d_st)));
            
            g2.setFont(new Font(null, Font.TRUETYPE_FONT, 50));
            g.setColor(Color.BLACK);
            g2.drawString(dizi.dizi[i].getName(), 700+cos,400+sin);
        }
        

        if (down) {
            g.setColor(new Color(55,77,99));
            repaint();
        } else {
            g.setColor(new Color(55,66,77));
            repaint();
        }
        //g2.fillRoundRect(labx, 400, 150, 50, 20, 20);
        g2.setColor(Color.BLACK);

        //g2.drawRoundRect(labx, laby, 150, 50, 20, 20);

        if (down2) {
            g.setColor(new Color(55,77,99));
            repaint();
        } else {
            g.setColor(new Color(55,66,77));
            repaint();
        }
        g2.fillRoundRect(lab2x, laby, 170, 50, 20, 20);
         if (down3) {
            g.setColor(new Color(55,77,99));
            repaint();
        } else {
            g.setColor(new Color(55,66,77));
            repaint();
        }
        g2.fillRoundRect(lab3x, laby, 170, 50, 20, 20);
         g2.setColor(Color.magenta);
        g2.setFont(new Font(null, Font.TRUETYPE_FONT, 30));
       
        g2.drawString("New player", lab2x + 10, laby + 35);
        g2.drawString("remove last", lab3x + 5, laby + 35);
        g2.drawString(star, sx, sy);
        g2.translate(750, 400);
        g2.rotate(Math.toRadians(ro));
        g2.drawImage(img.getImage(), -256, -256, null);
        //g2.translate(-750, -400);
         g2.drawString("Enter", (labx + 40)-750, 20);
        lab.setBounds(labx, 360, 150, 50);
        lab2.setBounds(lab2x, laby, 150, 50);
        lab3.setBounds(lab3x, laby, 150, 50);
        this.add(lab);
        this.add(lab2);
        this.add(lab3);
    }

    @Override
    public void run() {
        try {
            while (true) {

                Thread.sleep(ms, rospeed);
                rospeed = rospeed + rotimes *10;
                //if for slwoing more down
                if (rospeed > rotimes * 8000) {
                    ms++;
                    rospeed = 0;
                }

                ro++;
                System.out.println(rotimes+"  "+degree+"  "+times+" "+ro);
                if (times == rotimes && ro == degree) {

                    rospeed = 0;
                    rotimes = (int) (Math.random() * 10);
                    if (rotimes < 5) {
                        rotimes = rotimes + 3;
                    }
                    System.out.println(360-(270+degree)%360);
                    times = 0;
                    ms = 0;
                    int to = (360-(270+degree)%360)/d;
                    degree = (int) (Math.random() * 360);
                    
//                    System.out.println(to+"   "+d);
                    this.setBackground(dizi.dizi[to].getColor());
                    my.stop();
                    
                }

                if (ro > 360) {
                    ro = 0;
                    times++;
                }

                //       System.out.println(rotimes);
                repaint();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lab && !my.isAlive()) {
            my = new Thread(this);
            if (!(dizi.top<2)) {
                        
                       int pl1 = (int) (Math.random()*dizi.top),pl2 = (int) (Math.random()*dizi.top);
                       player tmp = dizi.dizi[pl1];
                       dizi.dizi[pl1]=dizi.dizi[pl2];
                       dizi.dizi[pl2]=tmp;
              }
            my.start();
        }
        if (e.getSource() == lab2 && !my.isAlive()) {
            String na = JOptionPane.showInputDialog("Type your name :");
            if (na.length()>2) {
                player yeni = new player(na, dizi.top+1);
            System.out.println( dizi.top+1);
            dizi.push(yeni);
            }else System.out.println("you should enter a name");
            
        }
        if (e.getSource() == lab3 && !my.isAlive()) {
            dizi.pop();
        }
          if (e.getButton() == MouseEvent.BUTTON3) {
            System.out.println("ggg");
            mm.enable();
            mm.setLocation(e.getX(), e.getY());
            mm.setVisible(true);
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            mm.setVisible(false);
            mm.disable();
        }
        if (e.getSource() == op1) {
            System.out.println("h");
        }else if (e.getSource() == op2) {
            System.out.println("h2");
        }
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == lab2) {
            down2 = true;
        } else if(e.getSource() == lab3){
            down3 = true;
        }else if (e.getSource() == lab) {
            down = true;
        }
      
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         if (e.getSource() == lab2) {
            down2 = false;
        } else if(e.getSource() == lab3){
            down3 = false;
        }else down = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

  

}
/////////////////////////////////////////////////////////
class player {

    String name;
    int order;
    int points;
    Color color;
    private int[] rgb;

    player(String name, int order) {
        this.name = name;
        this.order = order;
        points = 0;
        rgb = new int[3];
        for (int i = 0; i < rgb.length; i++) {
            double a1 = 255 * Math.random();
            rgb[i] = (int) Math.floor(a1);
        }
        color = new Color(rgb[0], rgb[1], rgb[2]);

    }

    public Color getColor() {
        return color;
    }

    public void setPoints(int point) {
        this.points = point;
    }

    public String getName() {
        return name;
    }

}
///////////////////////////////////////////////////////
class players {

    player[] dizi;
    int top, n;

    players(int n) {
        dizi = new player[n];
        this.n = n;
        top = -1;

    }

    public void push(player yeni) {
        if (!isfull()) {
            top++;
            dizi[top] = yeni;
        }
    }

    public player pop() {
        if (!isEmpty()) {
            top--;
            return dizi[top + 1];
        } else {
            return null;
        }
    }
    public player peek() {
        if (!isEmpty()) {
            return dizi[top];
        }else return null;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isfull() {
        return top == n - 1;
    }

}
