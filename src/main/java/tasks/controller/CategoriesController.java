package tasks.controller;

import tasks.model.CategoriesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import javax.validation.Valid;

import tasks.repository.CategoriesRepository;
import tasks.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

  @Autowired
  private CategoriesRepository categoriesRepository;

  @GetMapping
  public CategoriesList getCategoriesListById(@SessionAttribute User user) {
      return categoriesRepository.findByUserId(user.getSub()).orElse(new CategoriesList());
  }

  @PutMapping
  @ResponseBody
  public void modifyCategoriesList(@SessionAttribute User user, @Valid @RequestBody CategoriesList categoriesList) {
      categoriesRepository.save(categoriesList);
  }

}
