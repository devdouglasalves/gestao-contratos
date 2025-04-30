package model.entities;

import model.enums.ContractType;
import model.exceptions.ContractException;

import java.time.LocalDate;

public class TemporaryContract extends Contract{

    public TemporaryContract() {
        super();
    }

    public TemporaryContract(String employeeName, LocalDate startDate, LocalDate endDate, ContractType contractType, Double salary) {
        super(employeeName, startDate, endDate, contractType, salary);
    }

    @Override
    public void validate() {
        validateSimple();
        if (durationInMonths() > 24) {
            throw new ContractException("Contrato temporário não pode exceder 24 meses.");
        }
    }
}
