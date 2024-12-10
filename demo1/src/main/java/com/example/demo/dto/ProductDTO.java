package com.example.demo.dto;

import com.example.demo.model.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "không được để trống")
    @Size(min = 5, max = 50, message = "Tên sản phẩm phải từ 5 đến 50 ký tự.")
    private String name;
    @NotNull(message = "không được để trống")
    @Min(value = 100000, message = "Giá sản phẩm phải lớn hơn hoặc bằng 100000.")
    private double price;
    @NotBlank(message = "không được để trống")
    private String status;
    @NotNull(message = "không được để trống")
    private Category productCategory;
}
