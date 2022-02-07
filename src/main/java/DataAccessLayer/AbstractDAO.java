package DataAccessLayer;

import model.Client;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * In aceasta clasa sunt implementate toate interogarile sql care sunt executate in timpul rularii programului
 *
 * @author Zoli
 * @version 1.0
 * @since 2020-04-22
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    public List<T> findAll() {
        String findAllString = new String("SELECT * FROM ");
        findAllString = findAllString + type.getSimpleName();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllString);
            resultSet = statement.executeQuery();
            List<T> list = createList(resultSet);
            return (List<T>) list;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("idProduct");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> list = createList(resultSet);
            return list.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public List<T> createList(ResultSet resultSet) throws SQLException, InvocationTargetException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchMethodException {
        List<T> list = new ArrayList<>();
        Constructor<?>[] cons1 = type.getConstructors();
        String fields = "" ;
        while(resultSet.next()){
            Object[] args = new Object[cons1[0].getGenericParameterTypes().length];
            int i = 0;
            fields = "";
            for(Field field : type.getDeclaredFields()){
                String fieldName = field.getName();
                fields = fields + "" + fieldName + " ";
                Object value = resultSet.getObject(fieldName);
                if(value instanceof Integer){
                    Integer x = (Integer)value;
                    args[i++] = new Integer(x);
                } else{
                    String x = value.toString();
                    args[i++] = x;
                }
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,type);
                Method method = propertyDescriptor.getWriteMethod();
            }
            T bird = (T) cons1[0].newInstance(args);
            list.add(bird);
        }
       // printListe(list,fields);
        return list;
    }

    public String createInsertQuery(int l, Object[] args, String[] fields){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName() +"(");
        for( int i = 0 ; i < fields.length; ++i){
            sb.append(fields[i]);
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(") VALUES(");
        for( int i = 0 ; i < args.length; ++i){
            sb.append("?");
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public T insert(T t) throws IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int l = 0;
        for(Field field : t.getClass().getDeclaredFields()) {
            l++;
        }
        Object[] args = new Object[l];
        String[] fields = new String[l];
        int cnt = 0;
        for(Field field : t.getClass().getDeclaredFields()){
            fields[cnt] = field.getName();
            field.setAccessible(true);
            Object value = field.get(t);
            if(value instanceof Integer){
                Integer x = (Integer)value;
                args[cnt++] = new Integer(x);
            } else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }

        String query = createInsertQuery(l,args,fields);
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 0 ;i  < cnt ; ++i){
                if(args[i] instanceof Integer){
                    statement.setInt(i+1, (Integer) args[i]);
                }
                else{
                    statement.setString(i+1, (String) args[i]);
                }
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public String createUpdateQuery(int l, String[] fields){
        StringBuilder sb1 = new StringBuilder();
        sb1.append("UPDATE ");
        sb1.append(type.getSimpleName() + " SET ");
        for(int i = 1 ; i < fields.length; ++i){
            sb1.append(fields[i] + "=?" );
            if(i != fields.length -1){
                sb1.append(" , ");
            }
        }
        sb1.append(" WHERE " + fields[0] + "=?");
        return sb1.toString();
    }

    public T update(T t) throws IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int l = 0;
        for(Field field : t.getClass().getDeclaredFields()) {
            l++;
        }
        Object[] args = new Object[l];
        String[] fields = new String[l];
        int cnt = 0;
        for(Field field : t.getClass().getDeclaredFields()){
            fields[cnt] = field.getName();
            field.setAccessible(true);
            Object value = null;
            value = field.get(t);
            if(value instanceof Integer){
                Integer x = (Integer)value;
                args[cnt++] = new Integer(x);
            } else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }
        String query = createUpdateQuery(l,fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 1 ;i  < cnt ; ++i){
                if(args[i] instanceof Integer){
                    statement.setInt(i, (Integer) args[i]);
                }
                else{
                    statement.setString(i, (String) args[i]);
                }
            }
            statement.setInt(cnt, (Integer) args[0]);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public String createDeleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName() + " WHERE " + field + "=?");
        return sb.toString();
    }
    public T delete(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] field = t.getClass().getDeclaredFields();
        String query = createDeleteQuery(field[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            field[0].setAccessible(true);
            Object value = new Integer((Integer) field[0].get(t));
            statement.setInt(1, (Integer) value);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public void printListe(List<T> list, String fields){
        System.out.println(fields);
        for(T d: list){
            System.out.println(d.toString());
        }
    }
}
