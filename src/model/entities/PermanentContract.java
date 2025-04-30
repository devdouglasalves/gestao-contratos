package model.entities;

import model.enums.ContractType;

import java.time.LocalDate;

public class PermanentContract extends Contract{

    public PermanentContract() {
        super();
    }

    public PermanentContract(String employeeName, LocalDate startDate, LocalDate endDate, ContractType contractType, Double salary) {
        super(employeeName, startDate, endDate, contractType, salary);
    }

    @Override
    public void validate() {
        validateSimple();
    }

}
