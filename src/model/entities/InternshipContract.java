package model.entities;

import model.enums.ContractType;
import model.exceptions.ContractException;

import java.time.LocalDate;

public class InternshipContract extends Contract{

    public InternshipContract() {
        super();
    }

    public InternshipContract(String employeeName, LocalDate startDate, LocalDate endDate, ContractType contractType, Double salary) {
        super(employeeName, startDate, endDate, contractType, salary);
    }

    @Override
    public void validate() {
        validateSimple();
        if (durationInMonths() > 12) {
            throw new ContractException("Contrato de Estágio não pode exceder 12 meses.");
        }
    }
}
