package com.sirade.SIRADEAPI.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

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
