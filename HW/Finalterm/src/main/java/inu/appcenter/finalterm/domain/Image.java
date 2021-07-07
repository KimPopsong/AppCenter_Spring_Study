package inu.appcenter.finalterm.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String storeName;

    private String imageUrl;

    private String thumbnailImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Image create(String storeName, String imageUrl, String thumbnailImageUrl , Post post) {

        Image image = new Image();

        image.storeName = storeName;
        image.imageUrl = imageUrl;
        image.thumbnailImageUrl = thumbnailImageUrl;
        image.post = post;

        return image;
    }
}
