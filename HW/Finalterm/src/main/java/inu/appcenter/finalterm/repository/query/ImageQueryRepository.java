package inu.appcenter.finalterm.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.appcenter.finalterm.domain.Image;
import inu.appcenter.finalterm.domain.QImage;
import inu.appcenter.finalterm.domain.QPost;
import inu.appcenter.finalterm.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageQueryRepository {

    private final ImageRepository imageRepository;
    private final JPAQueryFactory queryFactory;

    public void deleteImagesByPostId(Long postId) {

        List<Image> images = queryFactory
                .select(QImage.image).distinct()
                .from(QImage.image)
                .leftJoin(QImage.image.post, QPost.post).fetchJoin()
                .where(QImage.image.post.id.eq(postId))
                .fetch();

        for (Image image : images) {
            imageRepository.delete(image);
        }
    }
}
