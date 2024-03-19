package net.ugurkartal.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ugurkartal.backend.models.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetAllResponse {
    private String id;
    private String name;
    private Category category;
    private boolean isActive;
}