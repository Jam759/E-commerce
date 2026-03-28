SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `product_sales`;
DROP TABLE IF EXISTS `product_order_refund`;
DROP TABLE IF EXISTS `payment_history`;
DROP TABLE IF EXISTS `payment`;
DROP TABLE IF EXISTS `product_review`;
DROP TABLE IF EXISTS `image`;
DROP TABLE IF EXISTS `product_order`;
DROP TABLE IF EXISTS `product_category`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `common_group_detail`;
DROP TABLE IF EXISTS `common_group`;
DROP TABLE IF EXISTS `member`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `member` (
                          `member_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '회원 PK',
                          `email` VARCHAR(150) NULL COMMENT '회원 이메일',
                          `oauth_id` TEXT NULL COMMENT 'OAuth 제공자 측 사용자 식별값',
                          `oauth_type` ENUM('LOCAL', 'GOOGLE', 'KAKAO', 'NAVER', 'GITHUB') NULL COMMENT '로그인 방식',
                          `identity_id` CHAR(36) NULL COMMENT '내부 식별 UUID',
                          `role` ENUM('USER', 'SELLER', 'ADMIN') NOT NULL DEFAULT 'USER' COMMENT '회원 역할',
                          `profile_img_path` VARCHAR(255) NULL COMMENT '프로필 이미지 경로',
                          `local_login_id` VARCHAR(100) NULL COMMENT '로컬 로그인 아이디',
                          `local_login_password` TEXT NULL COMMENT '로컬 로그인 비밀번호 해시',
                          `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                          `created_by` BIGINT NULL COMMENT '생성자 회원 ID',
                          `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
                          `updated_by` BIGINT NULL COMMENT '수정자 회원 ID',
                          `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                          `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                          PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원 정보';

CREATE INDEX `idx_identity_id` ON `member` (`identity_id`);

CREATE TABLE `common_group` (
                                `cg_id` VARCHAR(50) NOT NULL COMMENT '공통코드 그룹 ID',
                                `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                                `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                                `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                                `description` VARCHAR(255) NULL COMMENT '공통코드 그룹 설명',
                                PRIMARY KEY (`cg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='공통코드 그룹';

CREATE TABLE `common_group_detail` (
                                       `cgd_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '공통코드상세 PK',
                                       `code` VARCHAR(50) NOT NULL COMMENT '공통코드 값',
                                       `common_group_id` VARCHAR(50) NOT NULL COMMENT '소속 공통코드 그룹 ID',
                                       `description` VARCHAR(255) NULL COMMENT '공통코드 설명',
                                       `is_use` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '사용 여부',
                                       `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                       `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                                       `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                                       `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                                       `ref_1` VARCHAR(255) NULL COMMENT '예비 참조값 1',
                                       `ref_2` VARCHAR(255) NULL COMMENT '예비 참조값 2',
                                       `ref_3` VARCHAR(255) NULL COMMENT '예비 참조값 3',
                                       PRIMARY KEY (`cgd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='공통코드 상세';

CREATE INDEX `idx_common_group_code_detail` ON `common_group_detail` (`common_group_id`, `code`);

CREATE TABLE `product` (
                           `product_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 PK',
                           `seller_id` BIGINT NOT NULL COMMENT '판매자 회원 ID',
                           `description` TEXT NULL COMMENT '상품 설명',
                           `amount` DECIMAL(15,2) NOT NULL COMMENT '상품 판매 금액',
                           `quantity` BIGINT NOT NULL DEFAULT 0 COMMENT '재고 수량',
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                           `created_by` BIGINT NULL COMMENT '생성자 회원 ID',
                           `updated_at` DATETIME NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
                           `updated_by` BIGINT NULL COMMENT '수정자 회원 ID',
                           `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                           `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                           `product_status` ENUM('DRAFT', 'SELLING', 'SOLD_OUT', 'DELETED') NOT NULL DEFAULT 'DRAFT' COMMENT '상품 상태',
                           `currency` ENUM('KRW', 'USD', 'JPY', 'EUR') NOT NULL DEFAULT 'KRW' COMMENT '통화 코드',
                           PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 정보';

CREATE INDEX `idx_product_seller` ON `product` (`seller_id`);

CREATE TABLE `product_category` (
                                    `category_id` INT NOT NULL AUTO_INCREMENT COMMENT '상품 카테고리 PK',
                                    `product_id` BIGINT NOT NULL COMMENT '상품 ID',
                                    `product_category_name` BIGINT NOT NULL COMMENT '상품 카테고리명 공통코드상세 ID',
                                    `product_category_level` BIGINT NOT NULL COMMENT '상품 카테고리 레벨 공통코드상세 ID',
                                    PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 카테고리 매핑';

CREATE INDEX `idx_product_category_product_id` ON `product_category` (`product_id`);

CREATE TABLE `product_order` (
                                 `po_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 주문 PK',
                                 `buyer_id` BIGINT NOT NULL COMMENT '구매자 회원 ID',
                                 `product_id` BIGINT NOT NULL COMMENT '상품 ID',
                                 `ref_order_id` BIGINT NULL COMMENT '원주문 ID 또는 참조 주문 ID',
                                 `order_status` ENUM('PENDING', 'PAID', 'CONFIRMED', 'CANCELLED', 'REFUND_REQUESTED', 'REFUNDED') NOT NULL DEFAULT 'PENDING' COMMENT '주문 상태',
                                 `origin_amount` DECIMAL(15,2) NOT NULL COMMENT '원주문 금액',
                                 `paid_amount` DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '실결제 금액',
                                 `item_amount` DECIMAL(15,2) NOT NULL COMMENT '상품 합계 금액',
                                 `quantity` INT NOT NULL COMMENT '주문 수량',
                                 `confirmed_at` DATETIME NULL COMMENT '구매 확정 일시',
                                 `confirmed_by` ENUM('SYS', 'USER') NULL COMMENT '구매 확정 주체',
                                 `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                 `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                                 `paid_at` DATETIME NULL COMMENT '결제 완료 일시',
                                 PRIMARY KEY (`po_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 주문';

CREATE INDEX `idx_po_buyer_id` ON `product_order` (`buyer_id`);
CREATE INDEX `idx_po_product_id` ON `product_order` (`product_id`);

CREATE TABLE `image` (
                         `image_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '이미지 PK',
                         `uploader_id` BIGINT NOT NULL COMMENT '업로더 회원 ID',
                         `display_order` SMALLINT NULL COMMENT '노출 순서',
                         `file_size` BIGINT NULL COMMENT '파일 크기(byte)',
                         `target_id` BIGINT NULL COMMENT '연결 대상 ID',
                         `target_type` ENUM('PRODUCT', 'REVIEW', 'PROFILE', 'ETC') NULL COMMENT '연결 대상 타입',
                         `origin_img_name` VARCHAR(255) NULL COMMENT '원본 파일명',
                         `upload_img_name` VARCHAR(255) NULL COMMENT '저장 파일명',
                         `img_path` VARCHAR(255) NULL COMMENT '이미지 저장 경로',
                         `img_usage` ENUM('THUMBNAIL', 'DETAIL', 'CONTENT', 'PROFILE') NULL COMMENT '이미지 용도',
                         `img_extension` ENUM('JPG', 'JPEG', 'PNG', 'WEBP', 'GIF') NULL COMMENT '이미지 확장자',
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                         `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                         `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
                         `updated_by` BIGINT NULL COMMENT '수정자 회원 ID',
                         `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                         `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                         PRIMARY KEY (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='이미지 메타 정보';

CREATE INDEX `idx_image_target_id_and_type` ON `image` (`target_id`, `target_type`);

CREATE TABLE `product_review` (
                                  `pr_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 리뷰 PK',
                                  `writer_id` BIGINT NOT NULL COMMENT '리뷰 작성자 회원 ID',
                                  `product_order_id` BIGINT NOT NULL COMMENT '주문 ID',
                                  `product_id` BIGINT NOT NULL COMMENT '상품 ID',
                                  `content` VARCHAR(500) NULL COMMENT '리뷰 내용',
                                  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                  `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                                  `deleted_at` DATETIME NULL COMMENT '삭제 일시',
                                  `deleted_by` BIGINT NULL COMMENT '삭제자 회원 ID',
                                  PRIMARY KEY (`pr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 리뷰';

CREATE INDEX `idx_pr_writer_id` ON `product_review` (`writer_id`);
CREATE INDEX `idx_pr_product_id` ON `product_review` (`product_id`);

CREATE TABLE `payment` (
                           `payment_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '결제 PK',
                           `po_id` BIGINT NOT NULL COMMENT '상품 주문 ID',
                           `payment_key` VARCHAR(100) NULL COMMENT 'PG 또는 외부 결제 키',
                           `amount` DECIMAL(15,2) NOT NULL COMMENT '결제 금액',
                           `paid_at` DATETIME NULL COMMENT '결제 완료 일시',
                           `payment_status` ENUM('READY', 'IN_PROGRESS', 'DONE', 'CANCELLED', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'READY' COMMENT '결제 상태',
                           PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='결제 정보';

CREATE INDEX `idx_payment_order_id` ON `payment` (`po_id`);

CREATE TABLE `payment_history` (
                                   `payment_history_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '결제 상태 이력 PK',
                                   `payment_id` BIGINT NOT NULL COMMENT '결제 ID',
                                   `previous_status` ENUM('READY', 'IN_PROGRESS', 'DONE', 'CANCELLED', 'FAILED', 'REFUNDED') NULL COMMENT '변경 전 결제 상태',
                                   `changed_status` ENUM('READY', 'IN_PROGRESS', 'DONE', 'CANCELLED', 'FAILED', 'REFUNDED') NOT NULL COMMENT '변경 후 결제 상태',
                                   `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '상태 변경 일시',
                                   `change_reason` BIGINT NOT NULL COMMENT '상태 변경 사유 공통코드상세 ID',
                                   PRIMARY KEY (`payment_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='결제 상태 변경 이력';

CREATE INDEX `idx_ph_payment_id` ON `payment_history` (`payment_id`);

CREATE TABLE `product_order_refund` (
                                        `por_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '주문 환불 PK',
                                        `product_order_id` BIGINT NOT NULL COMMENT '상품 주문 ID',
                                        `refund_quantity` INT NULL COMMENT '환불 수량',
                                        `refund_amount` DECIMAL(15,2) NULL COMMENT '환불 금액',
                                        `refund_status` ENUM('REQUESTED', 'APPROVED', 'REJECTED', 'COMPLETED') NOT NULL DEFAULT 'REQUESTED' COMMENT '환불 상태',
                                        `orr_reason_common_detail_id` BIGINT NOT NULL COMMENT '주문 환불 사유 공통코드상세 ID',
                                        `etc_reason` VARCHAR(256) NULL COMMENT '기타 환불 사유 상세 내용',
                                        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                        `created_by` BIGINT NOT NULL COMMENT '생성자 회원 ID',
                                        `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
                                        `updated_by` BIGINT NULL COMMENT '수정자 회원 ID',
                                        PRIMARY KEY (`por_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='주문 환불 정보';

CREATE INDEX `idx_por_order_id` ON `product_order_refund` (`product_order_id`);

CREATE TABLE `product_sales` (
                                 `ps_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 매출 집계 PK',
                                 `product_id` BIGINT NOT NULL COMMENT '상품 ID',
                                 `quantity_sum` BIGINT NOT NULL DEFAULT 0 COMMENT '누적 판매 수량',
                                 `order_count` INT NOT NULL DEFAULT 0 COMMENT '누적 주문 건수',
                                 `gross_amount` BIGINT NOT NULL DEFAULT 0 COMMENT '총 매출 금액',
                                 `net_amount` BIGINT NOT NULL DEFAULT 0 COMMENT '순 매출 금액',
                                 `cancel_amount` BIGINT NOT NULL DEFAULT 0 COMMENT '취소 금액 합계',
                                 `refund_amount` BIGINT NOT NULL DEFAULT 0 COMMENT '환불 금액 합계',
                                 `sales_date` DATETIME NOT NULL COMMENT '매출 집계 기준 일시',
                                 `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
                                 `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
                                 `updated_by` BIGINT NULL COMMENT '수정자 회원 ID',
                                 PRIMARY KEY (`ps_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품 매출 집계';

CREATE INDEX `idx_ps_product_id` ON `product_sales` (`product_id`);