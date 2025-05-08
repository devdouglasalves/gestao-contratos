package model.entities;

import model.enums.ContractType;
import model.exceptions.ContractException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class Contract {

    private static final Double BONUS = 500.00;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType contractType;
    private Double salary;

    public Contract() {
    }

    public Contract(String employeeName, LocalDate startDate, LocalDate endDate, ContractType contractType, Double salary) {
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.salary = salary;
        validate();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public long durationInMonths() {
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        return months == 0 ? 1 : months;
    }

    public double totalValue() {

        if (durationInMonths() >= 12) {
            return (salary + BONUS) * durationInMonths();
        }
        else  {
            return salary * durationInMonths();
        }
    }

    public void validateSimple() {
        LocalDate dataAgora = LocalDate.now();

        if (getStartDate().isBefore(dataAgora)) {
            throw new ContractException("A data de início deve ser hoje ou uma data futura.");
        }

        if (getEndDate().isBefore(getStartDate())) {
            throw new ContractException("A data de fim deve ser posterior à data de início.");
        }

        if (getSalary() <= 0) {
            throw new ContractException("O salário informado é inválido. O valor deve ser positivo e maior que zero.");
        }
    }

    public abstract void validate();

    public static String titleCsv() {
        return "Nome,Tipo,Data inicio,Data fim,Salario";
    }

    public String exportCsv() {
        return getEmployeeName() + "," + getContractType()+ "," + dtf.format(getStartDate()) + "," + dtf.format(getEndDate()) + "," + String.format("%.2f",getSalary());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getEmployeeName() + "\n");
        sb.append("Tipo: " + getContractType() + "\n");
        sb.append("Duração: " + durationInMonths() + " meses\n");
        sb.append("Início: " + dtf.format(getStartDate()) + "\n");
        sb.append("Término: " + dtf.format(getEndDate()) + "\n");

        if (durationInMonths() >= 12) {
            sb.append("Bônus (Tempo de empresa): R$ " +String.format("%.2f%n",BONUS));
            sb.append("Salário mensal: R$ " + String.format("%.2f", getSalary() + BONUS)+ "\n");
        }
        else {
            sb.append("Salário mensal: R$ " + String.format("%.2f", getSalary())+ "\n");
        }
        sb.append("Valor total do contrato: R$ " + String.format("%.2f", totalValue())+ "\n");

        return sb.toString();
    }
}
