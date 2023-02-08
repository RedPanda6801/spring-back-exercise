package com.study.demo.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class ProductImage {
    //@Column(nullable = false)
    private String fileName;

    //@Column(nullable = false)
    private String fileOriName;

    //@Column(nullable = false)
    private String fileUrl;
}
