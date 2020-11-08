package com.example.recipeapp.controllers;

import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IndexControllerTest {

    IndexController indexController;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPageTest() {
        //given
        Set<Recipe> recipes = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        recipes.add(recipe1);
        recipes.add(recipe2);

        List<Recipe> recipesList = new ArrayList<>();
        recipesList.add(new Recipe());
        recipesList.add(new Recipe());

        //when
        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        List<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }


}