package com.aca.ecommerce.image.entity;

import com.aca.ecommerce.global.entity.BaseEntity;
import com.aca.ecommerce.image.enums.ImgExtension;
import com.aca.ecommerce.image.enums.ImgUsage;
import com.aca.ecommerce.image.enums.TargetType;
import com.aca.ecommerce.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SuperBuilder
@Table(name = "image", indexes = {
        @Index(name = "idx_image_target_id_and_type", columnList = "target_id, target_type")
})
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    private Member uploader;

    @Column(name = "display_order")
    private Short displayOrder;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;

    @Column(name = "origin_img_name", length = 255)
    private String originImgName;

    @Column(name = "upload_img_name", length = 255)
    private String uploadImgName;

    @Column(name = "img_path", length = 255)
    private String imgPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "img_usage")
    private ImgUsage imgUsage;

    @Enumerated(EnumType.STRING)
    @Column(name = "img_extension")
    private ImgExtension imgExtension;
}
