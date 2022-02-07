package Presentation;

import BusinessLayer.ClientBLL;
import DataAccessLayer.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * In aceasta clasa este realizata interfata GUI pentru clientii
 * @author Zoli
 * @version 1.
 */
public class ClientGUI extends JFrame {

    private ClientBLL clientBLL = new ClientBLL();
    private JLabel jl1 = new JLabel("Client Menu");

    private JLabel id = new JLabel("idClient:");
    private JTextField idt = new JTextField(2);
    private JPanel jp1 = new JPanel();
    private JLabel name = new JLabel("Name:");
    private JTextField namet= new JTextField(5);
    private JPanel jp2 = new JPanel();
    private JLabel address = new JLabel("Address:");
    private JTextField addresst = new JTextField(5);
    private JPanel jp3 = new JPanel();
    private JLabel email = new JLabel("Email:");
    private JTextField emailt = new JTextField(5);
    private JPanel jp4 = new JPanel();
    private JLabel age = new JLabel("Age:");
    private JTextField aget = new JTextField(2);
    private JPanel jp5 = new JPanel();

    private JButton jb1 = new JButton("Add");
    private JButton jb2 = new JButton("Update");
    private JButton jb3 = new JButton("Delete");
    private JButton jb4 = new JButton("View");
    private JPanel jpfinal = new JPanel();
    private JPanel jp6 = new JPanel();

    private JTable tabel = new JTable() ;
    JScrollPane sp = new JScrollPane();

    public void setJt1(JTable jt1) {
        this.tabel = jt1;
    }

    public ClientGUI(){
        super("Client");
        super.setSize(500,300);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.jpfinal.setLayout(new BoxLayout(jpfinal,BoxLayout.Y_AXIS));
        jp1.add(id);
        jp1.add(idt);
        jp2.add(name);
        jp2.add(namet);
        jp3.add(address);
        jp3.add(addresst);
        jp4.add(email);
        jp4.add(emailt);
        jp5.add(age);
        jp5.add(aget);
        this.jb1.addActionListener(new ButtonAdd());
        jp6.add(jb1);
        jp6.add(jb2);
        this.jb2.addActionListener(new UpdateDelete());
        jp6.add(jb3);
        this.jb3.addActionListener(new ButtonDelete());
        jp6.add(jb4);
        this.jb4.addActionListener(new ButtonView());
        jpfinal.add(jl1);
        jpfinal.add(jp1);
        jpfinal.add(jp2);
        jpfinal.add(jp3);
        jpfinal.add(jp4);
        jpfinal.add(jp5);
        jpfinal.add(jp6);
        setLayout(new BorderLayout());
        super.setContentPane(jpfinal);
        super.setVisible(true);
    }

    class ButtonAdd implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int idC = Integer.parseInt(idt.getText());
            String nameC = namet.getText();
            String addressC = addresst.getText();
            String emailC = emailt.getText();
            int ageC = Integer.parseInt(aget.getText());
            Client c = new Client(idC,nameC,addressC,emailC,ageC);
            try {
                clientBLL.insert(c);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

        }
    }

    public void setSp(JScrollPane sp) {
        this.sp = sp;
    }


    class ButtonView implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> lista = clientBLL.findAll();
            List<String[]> values = new ArrayList<String[]>();
            for (Client c : lista) {
                values.add(new String[]{String.valueOf(c.isIdClient()) + "", c.getName(), c.getAddress(), c.getEmail(), String.valueOf(c.getAge())});
            }
           String[] columns = new  String[]{"idClient","name","address","email","age"};
            JTable tabel1 = new JTable(values.toArray(new Object[][]{}),columns);
            tabel1.setBounds(10,10,300,300);
            setJt1(tabel1);
            JScrollPane sp1 = new JScrollPane(tabel);
            setSp(sp1);
            JPanel jpfinalfinal = new JPanel();
            jpfinalfinal.add(jpfinal);
            jpfinalfinal.add(sp);
            setContentPane(jpfinalfinal);
            setVisible(true);
        }
    }

    class ButtonDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int idC = Integer.parseInt(idt.getText());
            clientBLL.delete(new Client(idC,null,null,null,0));
        }
    }

    class UpdateDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int idC = Integer.parseInt(idt.getText());
            String nameC = namet.getText();
            String addressC = addresst.getText();
            String emailC = emailt.getText();
            int ageC = Integer.parseInt(aget.getText());
            Client c = new Client(idC,nameC,addressC,emailC,ageC);
            try {
                clientBLL.update(c);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
}
