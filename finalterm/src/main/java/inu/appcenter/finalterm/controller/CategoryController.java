package inu.appcenter.finalterm.controller;

import inu.appcenter.finalterm.domain.Category;
import inu.appcenter.finalterm.model.category.CategoryPatchRequest;
import inu.appcenter.finalterm.model.category.CategorySaveRequest;
import inu.appcenter.finalterm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리의 게시글 리스트 조회
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Void> findCategory(@PathVariable Long categoryId) {

        return null;
    }

    /**
     * 카테고리 리스트 조회 OK
     *
     * @return
     */
    @GetMapping("/category")
    public List<Category> findAllCategory() {

        return categoryService.findAllCategory();
    }

    /**
     * 카테고리 생성 OK
     * ADMIN ONLY
     *
     * @param categorySaveRequest name must not blank
     * @return CREATED
     */
    @PostMapping("/ADMIN/category")
    public ResponseEntity<Void> saveCategory(@RequestBody @Valid CategorySaveRequest categorySaveRequest) {

        categoryService.saveCategory(categorySaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 카테고리 수정 OK
     * ADMIN ONLY
     *
     * @param categoryPatchRequest name must not blank
     * @param categoryId           Long
     * @return OK
     */
    @PatchMapping("/ADMIN/category/{categoryId}")
    public ResponseEntity<Void> patchCategory(@RequestBody @Valid CategoryPatchRequest categoryPatchRequest, @PathVariable Long categoryId) {

        categoryService.patchCategory(categoryPatchRequest, categoryId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 카테고리 삭제 NOT OK... N + 1문제 ㅠㅠ MultipleBagFetchException...
     * ADMIN ONLY
     * <p>
     * Delete category and category's posts, images and comments.
     *
     * @param categoryId Long
     * @return OK
     */
    @DeleteMapping("/ADMIN/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
