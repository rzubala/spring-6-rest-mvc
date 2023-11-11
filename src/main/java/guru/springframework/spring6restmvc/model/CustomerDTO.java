package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID id;
    @NotBlank
    @NotNull
    private String customerName;
    private LocalDateTime createdDate;
    private Integer version;
    private LocalDateTime lastModifiedDate;
}
