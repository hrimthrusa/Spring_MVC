package ru.griva.spring.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.griva.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO { // Обновляет, добавляет, изменяет и удаляет.
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   /* Чистый JDBC.
   private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver"); // С помощью рефлексии подгружаем класс Driver и убеждаемся что он в оперативной памяти для пользования.
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Используем драйвер и подключаемся к БД
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } */



    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));


        /* Чистый JDBC.
        List<Person> people = new ArrayList<>();

        try { // Здесь можно использовать обычный Statement т.к. не получаем данных от пользователя.
            Statement statement = connection.createStatement(); // Создаем объект, содержащий в себе SQL-запрос.
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL); // Выполняем запрос, метод возвращает ResultSet результат.

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person); // Заполняем поля Person и помещаем в список.
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
        */
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null); // PreparedStatement по умолчанию. Выражение возвращает по id и null в случае отсутствия.

        /* Чистый JDBC.
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

        preparedStatement.close();
        resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
        */
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());

        /* Чистый JDBC.
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ? ,?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
         */
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);

        /*
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);

       /*
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
    }
}
