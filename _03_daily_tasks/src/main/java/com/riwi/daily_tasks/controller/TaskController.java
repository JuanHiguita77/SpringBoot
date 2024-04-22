package com.riwi.daily_tasks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.riwi.daily_tasks.entity.Task;
import com.riwi.daily_tasks.services.TaskService;

import org.springframework.web.bind.annotation.PostMapping;


@Controller//Controlador
@RequestMapping //se deja sola para obtener la ruta raiz
public class TaskController 
{
    @Autowired
    private TaskService taskService; //Inyectando una dependencia 

    @GetMapping("/") //dar nombre a la ruta de la vista, si se deja sola ira a la ruta raiz
    public String showViewAll(Model model, 
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "4") int size, 
        @RequestParam(required = false) String title)
    {               
        if (title != null) 
        {
            List<Task> listTaskFinded = this.taskService.findByTitle(title);
            model.addAttribute("taskList", listTaskFinded);
            return "taskPage";
        }

        Page<Task> list = this.taskService.findAllPaginate(page - 1, size);
        model.addAttribute("taskList", list);//La llave se usa para acceder desde html (llave , valor)
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", list.getTotalPages());

        return "taskPage";//Return coders view with file name
    }

    //Funcion para pasarle los datos al formulario y mostrarlo
    @GetMapping("taskMenu")
    public String showTaskMenu(Model model)
    {
        //El modelo se encarga de mandar info a las vista
        model.addAttribute("task", new Task());
        model.addAttribute("actionMessage", "Create");
        model.addAttribute("action", "/addNewTask");
        return "addTaskMenu";
    }

    //Recibir los coders del formulario
    @PostMapping("addNewTask")//Ruta del form
    public String postTask(@ModelAttribute Task task) 
    {
        this.taskService.addTask(task);
        return "redirect:/";
    }

    //UPDATE
    @GetMapping("taskMenuEdit/{id}")//Obtenemos la Info
    //El id que llega del boton de editar, debe tener la misma ruta
    public String showTaskMenuEdit(@PathVariable Long id, Model model)
    {
        //Llenamos el formulario con la info anterior
        Task task = this.taskService.findById(id);
        //El modelo se encarga de mandar info a las vista
        model.addAttribute("task", task);
        model.addAttribute("actionMessage", "Edit");
        model.addAttribute("action", "/updateTask/" + id);
        return "addTaskMenu";
    }

    @PostMapping("updateTask/{id}")//Metodo Post
    //El id que le llega desde el action que le pasamos de forma dinamica en el controlador de vista
    public String updatingTask(@PathVariable Long id, @ModelAttribute Task task)
    {                                               //EL modelo nos da ya actualizado el task desde el formulario
        Task taskUpdated = this.taskService.updateTask(id, task);

        if (taskUpdated != null) return "redirect:/";
  
        return "error";
    }

    //Controlador para eliminar que recibira el ID de la URL
    @GetMapping("delete/{id}")
    public String deleteTask(@PathVariable Long id)
    {
        this.taskService.delete(id);

        return "redirect:/";
    }


}

