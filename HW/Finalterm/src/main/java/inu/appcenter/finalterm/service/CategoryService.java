package inu.appcenter.finalterm.service;

import inu.appcenter.finalterm.domain.Category;
import inu.appcenter.finalterm.domain.Post;
import inu.appcenter.finalterm.exception.CategoryException;
import inu.appcenter.finalterm.model.category.CategoryPatchRequest;
import inu.appcenter.finalterm.model.category.CategorySaveRequest;
import inu.appcenter.finalterm.repository.CategoryRepository;
import inu.appcenter.finalterm.repository.PostRepository;
import inu.appcenter.finalterm.repository.query.CommentQueryRepository;
import inu.appcenter.finalterm.repository.query.ImageQueryRepository;
import inu.appcenter.finalterm.repository.query.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final PostQueryRepository postQueryRepository;

    public List<Category> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList;
    }

    @Transactional
    public void saveCategory(CategorySaveRequest categorySaveRequest) {

        checkAlreadyExists(categorySaveRequest.getName());

        Category savedCategory = Category.createCategory(categorySaveRequest.getName());

        categoryRepository.save(savedCategory);
    }

    @Transactional
    public void patchCategory(CategoryPatchRequest categoryPatchRequest, Long categoryId) {

        checkAlreadyExists(categoryPatchRequest.getName());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("No Category Exists!"));

        category.patch(categoryPatchRequest.getName());
    }

    @Transactional
    public void deleteCategory(Long categoryId) {

        List<Post> postList = postQueryRepository.findPostsByCategoryId(categoryId);

        for (Post post : postList) {
            commentQueryRepository.deleteCommentsByPostId(post.getId());
            imageQueryRepository.deleteImagesByPostId(post.getId());
            postRepository.delete(post);
        }

        categoryRepository.deleteById(categoryId);
    }

    private void checkAlreadyExists(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new CategoryException("Category Already Exists!");
        }
    }
}
