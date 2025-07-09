package com.sirade.SIRADEAPI.DTO;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class EvaluacionGoldDTO {

    @Min(1) @Max(100)
    private int fev1Percent;

    @Min(0) @Max(40)
    private int catScore;

    @Min(0)
    private int exacerbacionesLeves;

    @Min(0)
    private int exacerbacionesGraves;
}
