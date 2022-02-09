package ru.griva.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.griva.spring.DAO.PersonDAO;
import ru.griva.spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) { // Лучше внедрять зависимость через конструктор.
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) { // Получим всех из DAO и передадим через модель на отображение в представление.

        model.addAttribute("people", personDAO.index());
        return "people/index"; // Возвращаем html со всеми
    }

    @GetMapping("/{id}") // Во время запуска можно будет задать любое id и оно придет в аргумент этого метода, извлекается @PathVariable.
    public String show(@PathVariable("id") int id,
                       Model model) {

        model.addAttribute("person", personDAO.show(id));  // Получим одного человека по id из DAO и передадим на отображение в представление.
        return "people/show";
    }

    @GetMapping("/new") // По GET запросу нам возвращается форма для создания нового человека.
    public String newPerson(@ModelAttribute("person") Person person) { // При использовании Thymeleaf форм передаем им объект, для которого эти формы нужны

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, // Создаем нового человека и кладем в модель его данные этой @
                         BindingResult bindingResult) { // Объект, содержащий в себе все ошибки.
        if (bindingResult.hasErrors())
            return "/people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
       model.addAttribute("person", personDAO.show(id)); // В этой модели значения текущего человека.
       return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, // Меняем значения человека id на значения полученные аннотацией @ModelAttribute из формы.
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
