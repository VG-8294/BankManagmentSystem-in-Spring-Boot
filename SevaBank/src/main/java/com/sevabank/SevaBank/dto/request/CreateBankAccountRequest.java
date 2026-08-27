package com.sevabank.SevaBank.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBankAccountRequest {

    @Schema(
            description = "User id for which account has to created",
            example = "12"
    )
    private Long userId;
    @Schema(
            description = "Initial balance with which need to be opened",
            example = "13000"
    )
    private double balance;
    @Schema(
            description = "Account type you want to create",
            example = "SAVING"
    )
    private String accountType;
}
