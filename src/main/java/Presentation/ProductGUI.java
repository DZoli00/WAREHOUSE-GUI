package Presentation;

import BusinessLayer.ProductBLL;
import DataAccessLayer.AbstractDAO;
import DataAccessLayer.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * In aceasta clasa este realizata interfata GUI pentru produse
 * @author Zoli
 * @version 1.0

 */

public class ProductGUI extends JFrame {
    private ProductBLL productBLL = new ProductBLL();
    private JLabel jl1 = new JLabel("Product Menu");

    private JLabel id = new JLabel("idProduct:");
    private JTextField idt = new JTextField(2);
    private JPanel jp1 = new JPanel();

    private JLabel name = new JLabel("Name:");
    private JTextField namet = new JTextField(5);
    private JPanel jp2 = new JPanel();
    private JLabel price = new JLabel("Price:");
    private JTextField pricet = new JTextField(3);
    private JPanel jp3 = new JPanel();
    private JLabel quan = new JLabel("Quantity:");
    private JTextField quant = new JTextField(3);
    private JPanel jp4 = new JPanel();
    private JPanel jpfinal = new JPanel();

    private JButton jb1 = new JButton("Add");
    private JButton jb2 = new JButton("Update");
    private JButton jb3 = new JButton("Delete");
    private JButton jb4 = new JButton("View");
    private JPanel jp5 = new JPanel();

    private JTable tabel = new JTable();
    JScrollPane sp = new JScrollPane();


    public void setJt1(JTable jt1) {
        this.tabel = jt1;
    }

    public ProductGUI(){
        super("Product");
        super.setSize(500,300);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.jpfinal.setLayout(new BoxLayout(jpfinal,BoxLayout.Y_AXIS));
        jp1.add(id);
        jp1.add(idt);
        jp2.add(name);
        jp2.add(namet);
        jp3.add(price);
        jp3.add(pricet);
        jp4.add(quan);
        jp4.add(quant);
        jp5.add(jb1);
        jb1.addActionListener(new ButtonAdd());
        jp5.add(jb2);
        jb2.addActionListener(new UpdateDelete());
        jp5.add(jb3);
        jb3.addActionListener(new ButtonDelete());
        jp5.add(jb4);
        jb4.addActionListener(new ButtonView());
        jpfinal.add(jl1);
        jpfinal.add(jp1);
        jpfinal.add(jp2);
        jpfinal.add(jp3);
        jpfinal.add(jp4);
        jpfinal.add(jp5);
        super.setContentPane(jpfinal);
        super.setVisible(true);
    }

    class ButtonAdd implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int idP = Integer.parseInt(idt.getText());
            String nameC = namet.getText();
            int priceP = Integer.parseInt(pricet.getText());
            int quanP = Integer.parseInt(quant.getText());

            Product p = new Product(idP,nameC,priceP,quanP);

            try {
                productBLL.insert(p);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
    class ButtonView implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Product> lista = productBLL.findAll();
            List<String[]> values = new ArrayList<String[]>();
            for (Product c : lista) {
                values.add(new String[]{String.valueOf(c.getIdProduct()), c.getName(), String.valueOf(c.getPrice()), String.valueOf(c.getQuantity())});
            }
            String[] columns = new String[]{"idClient", "name", "price", "quantity"};
            JTable tabel1 = new JTable(values.toArray(new Object[][]{}), columns);
            tabel1.setBounds(10, 10, 300, 300);
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
            productBLL.delete(new Product(idC,null,0,0));
        }
    }

    class UpdateDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int idP = Integer.parseInt(idt.getText());
            String nameC = namet.getText();
            int priceP = Integer.parseInt(pricet.getText());
            int quanP = Integer.parseInt(quant.getText());

            Product p = new Product(idP,nameC,priceP,quanP);
            try {
                productBLL.update(p);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    public void setSp(JScrollPane sp) {
        this.sp = sp;
    }
}
