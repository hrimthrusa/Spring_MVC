package ru.griva.spring.DAO;

import org.springframework.jdbc.core.RowMapper;
import ru.griva.spring.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> { // Интерфейс по переводу строки из БД в объект. Но мы используем BeanPropertyRowMapper.
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
