package model;

import DataAccessLayer.ClientDAO;
import DataAccessLayer.ConnectionFactory;
import DataAccessLayer.ProductDAO;
import Presentation.ClientGUI;
import Presentation.OrdersGUI;
import Presentation.ProductGUI;

import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    private final static String findStatementString = "SELECT * FROM client";

    public static void main(String[] args) throws IllegalAccessException {

            ClientGUI viewClient = new ClientGUI();
            ProductGUI viewProduct = new ProductGUI();
            OrdersGUI view = new OrdersGUI();
    }


}
