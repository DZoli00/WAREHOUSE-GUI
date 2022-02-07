package Presentation;

import BusinessLayer.ClientBLL;
import BusinessLayer.OrderBLL;
import BusinessLayer.ProductBLL;
import DataAccessLayer.ClientDAO;
import DataAccessLayer.OrdersDAO;
import DataAccessLayer.ProductDAO;
import model.Client;
import model.ComboItem;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * In aceasta clasa este realizata interfata GUI pentru orders
 * @author Zoli
  * @version 1.0
 */

public class OrdersGUI extends JFrame {

    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();
    private OrderBLL orderBLL = new OrderBLL();

    private  JLabel jl1 = new JLabel("Orders Menu");

    private JLabel id = new JLabel("idOrder:");
    private JTextField idt = new JTextField(10);
    private JPanel jp1 = new JPanel();

    private JLabel cl = new JLabel("client:");
    private JComboBox clcb = new JComboBox();
    private JPanel jp2 = new JPanel();

    private JLabel pr = new JLabel("product:");
    private JComboBox prcb = new JComboBox();
    private JPanel jp3 = new JPanel();

    private JLabel qu = new JLabel("quantity:");
    private JTextField qut = new JTextField(10);
    private JPanel jp4 = new JPanel();
    private JButton add = new JButton("Order!");
    private JButton delete = new JButton("Delete");
    private JButton printall = new JButton("View");
    private JButton refresh = new JButton("Refresh");
    private JPanel jp5 = new JPanel();
    private JPanel jpfinal = new JPanel();

    private File file = new File("orders.txt");
    private FileWriter myWriter;
    private BufferedWriter out;
    private JTable tabel = new JTable();
    JScrollPane sp = new JScrollPane();

    public void setJt1(JTable jt1) {
        this.tabel = jt1;
    }


    public OrdersGUI(){
        super("Orders");
        super.setSize(300,300);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        createBox();
        this.jpfinal.setLayout(new BoxLayout(jpfinal,BoxLayout.Y_AXIS));
        jpfinal.add(jl1);
        jp1.add(id);
        jp1.add(idt);
        jp2.add(cl);
        jp2.add(clcb);
        jp3.add(pr);
        jp3.add(prcb);
        jp4.add(qu);
        jp4.add(qut);
        add.addActionListener(new ButtonAdd());
        delete.addActionListener(new ButtonDelete());
        printall.addActionListener(new printButton());
        refresh.addActionListener(new refreshBox());
        jp5.add(add);
        jp5.add(delete);
        jp5.add(printall);
        //jp5.add(refresh);
        jpfinal.add(jp1);
        jpfinal.add(jp2);
        jpfinal.add(jp3);
        jpfinal.add(jp4);
        jpfinal.add(jp5);

        try {
            if(file.createNewFile()){
                System.out.println("hurra");
            }
        } catch (IOException ioException) {
            System.out.println("File creating failed");
            ioException.printStackTrace();
        }
        try {
            myWriter = new FileWriter("orders.txt");
            out = new BufferedWriter(myWriter);

        } catch (IOException ioException) {
            System.out.println("FileWriter creating failed");
            ioException.printStackTrace();
        }

        super.setContentPane(jpfinal);
        super.setVisible(true);
    }

    class ButtonDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int idO = Integer.parseInt(idt.getText());
            orderBLL.delete(new Orders(idO,0,0,null,null,0,0));
        }
    }

    class refreshBox implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e)
        {
            createBox();


            JPanel jpref = new JPanel();
            jpref.add(cl);
            jpref.add(clcb);
            jpref.add(pr);
            jpref.add(prcb);
            jp2 = jpref;
            orderBLL.findAll();
        }
    }
    class ButtonAdd implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int idO = Integer.parseInt(idt.getText());
            ComboItem comboItem = (ComboItem) clcb.getSelectedItem();
            int idC = comboItem.getId();
            String nameC =  comboItem.getName();
            comboItem = (ComboItem) prcb.getSelectedItem();
            int idP = comboItem.getId();
            String nameP = comboItem.getName();
            int quantity = Integer.parseInt(qut.getText());

            Product product = productBLL.findById(idP);
            int priceall = quantity*product.getPrice();
            Orders o1 = new Orders(idO,idC,idP,nameC,nameP,quantity,priceall);
            try {
                orderBLL.insert(o1);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

            product.setQuantity(product.getQuantity()-quantity);
            try {
                productBLL.update(product);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }

            afisareOrders();

        }
    }

    public void printBill(Orders o){
        try {
            myWriter.write("\nORDER ID: " + o.getIdOrder()+
                    "\nIDClient: " + o.getIdClient() + "" +
                    " name: " + o.getNameClient() + "\nIDProduct: "+
                    o.getIdProduct() + " name: "+ o.getNameProduct() + "" +
                    "\nQuantity: " + o.isQuantity() + " price: "+ o.getPriceAll() +"\n");
            myWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class printButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            afisareOrders();
        }
    }

    /**
     * afiseaza tabelul
     */
    public void afisareOrders(){
        List<Orders> lista = orderBLL.findAll();
        List<String[]> values = new ArrayList<String[]>();
        for (Orders c : lista) {
            values.add(new String[]{String.valueOf(c.getIdOrder()), String.valueOf(c.getIdClient()), String.valueOf(c.getIdProduct()),c.getNameClient(),c.getNameProduct(), String.valueOf(c.isQuantity()), String.valueOf(c.getPriceAll())});
        }
        String[] columns = new String[]{"idOrder", "idClient", "idProduct", "nameClient", "nameProduct","quantity","priceAll"};
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


    /**
     *  creeaza comboboxurile
     */
    public void createBox(){

        List<Client> list1 = clientBLL.findAll();
        JComboBox jbDemo1 = new JComboBox();
        for(Client c: list1){
            jbDemo1.addItem(new ComboItem(c.isIdClient(),c.getName()));
        }

        setClcb(jbDemo1);
        JComboBox jbDemo2 = new JComboBox();
        List<Product> list2 = productBLL.findAll();
        for(Product c: list2){
            jbDemo2.addItem(new ComboItem(c.getIdProduct(),c.getName()));
        }

        setPrcb(jbDemo2);

        setVisible(true);
    }

    public void setClcb(JComboBox clcb) {
        this.clcb = clcb;
    }

    public void setPrcb(JComboBox prcb) {
        this.prcb = prcb;
    }

    public void setSp(JScrollPane sp) {
        this.sp = sp;
    }
}
