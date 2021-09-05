package com.cookbook.cookbookbackend.repositories;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.models.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select distinct r from Recipe r inner join r.ingredientList i " +
            "where ((r.name like %?1%) or (?2 = r.category) or (?3 = r.cuisine) or (?4 member of r.hashTagList) or (?5 = r.difficultyGrade) or (i.name like %?1%)) and " +
            "(((?6 = r.category) or (?6 is null)) and ((?7= r.difficultyGrade) or (?7 is null)) and ((r.rating >= ?8) or (?8 is null)) and ((r.preparationTime <= ?9) or (?9 is null)) and ((?10= r.cuisine)or (?10 is null)))")
    List<Recipe> findAllRecipesByInputAndFilter
            (String inputText,
             Category category,
             Cuisine cuisineMapped,
             HashTag hashTagMapped,
             DifficultyGrade difficultyGradeMapped,
             Category filterCategory,
             DifficultyGrade filterDifficultyGrade,
             Double filterRating,
             Integer filterPreparationTime,
             Cuisine filterCuisineMapped,
             Pageable pageNumberWithElementsProPage);

    @Query("select count(distinct r.id) from Recipe r inner join r.ingredientList i " +
            "where ((r.name like %?1%) or (?2 = r.category) or (?3 = r.cuisine) or (?4 member of r.hashTagList) or (?5 = r.difficultyGrade) or (i.name like %?1%)) and " +
            "(((?6 = r.category) or (?6 is null)) and ((?7= r.difficultyGrade) or (?7 is null)) and ((r.rating >= ?8) or (?8 is null)) and ((r.preparationTime <= ?9) or (?9 is null)) and ((?10= r.cuisine)or (?10 is null)))")
    Integer countAllRecipesByInputAndFilter
            (String inputText,
             Category categoryMapped,
             Cuisine cuisineMapped,
             HashTag hashTagMapped,
             DifficultyGrade difficultyGradeMapped,
             Category filterCategory,
             DifficultyGrade filterDifficultyGrade,
             Double filterRating,
             Integer filterPreparationTime,
             Cuisine filterCuisineMapped);

    List<Recipe> findTop3RecipesByOrderByRatingDesc();

    List<Recipe> findTop5RecipesByOrderByDateOfPostAsc();
}
