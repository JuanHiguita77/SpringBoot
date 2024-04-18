package com.riwi.MyFirstWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riwi.MyFirstWeb.entity.Coder;
import com.riwi.MyFirstWeb.services.CoderService;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;


@Controller//Controlador
@RequestMapping //se deja sola para obtener la ruta raiz
public class CoderController 
{
    @Autowired
    private CoderService coderService; //Inyectando una dependencia 

    @GetMapping("/coderPage") //dar nombre a la ruta de la vista, si se deja sola ira a la ruta raiz
    public String showViewAll(Model model)
    {
        List<Coder> list = this.coderService.findAll();
        model.addAttribute("coderList", list);//La llave se usa para acceder desde html (llave , valor)

        return "coderPage";//Return coders view with file name
    }

    //Funcion para pasarle los datos al formulario y mostrarlo
    @GetMapping("coderMenu")
    public String showCoderMenu(Model model)
    {
        //El modelo se encarga de mandar info a las vista
        model.addAttribute("coder", new Coder());
        model.addAttribute("actionMessage", "Create");
        model.addAttribute("action", "/addNewCoder");
        return "coderMenuAdd";
    }

    //Recibir los coders del formulario
    @PostMapping("addNewCoder")//Ruta del form
    public String postCoder(@ModelAttribute Coder coder) 
    {
        this.coderService.addCoder(coder);
        return "redirect:/coderPage";
    }

    //UPDATE
    @GetMapping("coderMenuEdit/{id}")//Obtenemos la Info
    //El id que llega del boton de editar, debe tener la misma ruta
    public String showCoderMenuEdit(@PathVariable Long id, Model model)
    {
        //Llenamos el formulario con la info anterior
        Coder coder = this.coderService.findById(id);
        //El modelo se encarga de mandar info a las vista
        model.addAttribute("coder", coder);
        model.addAttribute("actionMessage", "Edit");
        model.addAttribute("action", "/updateCoder/" + id);
        return "coderMenuAdd";
    }

    @PostMapping("updateCoder/{id}")//Metodo Post
    //El id que le llega desde el action que le pasamos de forma dinamica en el controlador de vista
    public String updatingCoder(@PathVariable Long id, @ModelAttribute Coder coder)
    {                                               //EL modelo nos da ya actualizado el coder desde el formulario
        Coder coderUpdated = this.coderService.updateCoder(id, coder);

        if (coderUpdated != null) return "redirect:/coderPage";
  
        return "error";
    }

    //Controlador para eliminar que recibira el ID de la URL
    @GetMapping("delete/{id}")
    public String deleteCoder(@PathVariable Long id)
    {
        this.coderService.delete(id);

        return "redirect:/coderPage";
    }
}
