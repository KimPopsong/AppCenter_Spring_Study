package inu.appcenter.finalterm.model.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryPatchRequest {

    @NotBlank
    private String name;
}
