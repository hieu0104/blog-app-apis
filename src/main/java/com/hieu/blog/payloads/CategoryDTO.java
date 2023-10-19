package com.hieu.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

	private Integer categoryId;

	@NotBlank
	@Size(min = 4,message ="Min size of category title is 4")
	private String categoryTitle;

	@NotBlank
	@Size(min = 10,message ="Min size of category title is 10")
	private String categoryDescription;
}
