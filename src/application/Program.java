package application;

import model.entities.Contract;
import model.entities.InternshipContract;
import model.entities.PermanentContract;
import model.entities.TemporaryContract;
import model.enums.ContractType;
import model.exceptions.ContractException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        List<Contract> contractList = new ArrayList<>();


        int option;

        do {
            menu();
            option = scanner.nextInt();
            System.out.println();

            switch (option) {
                case 1 -> registerContracts(scanner, contractList);
                case 2 -> searchModule(scanner, contractList);
                case 0 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida, tente outra.");
            }
        } while (option != 0);

        scanner.close();
    }

    public static void menu() {
        System.out.println(" ___________________________ ");
        System.out.println("|     Menu de Contratos     |");
        System.out.println(" ___________________________ ");
        System.out.println("1 - Cadastrar Contratos");
        System.out.println("2 - Listar Contratos por Tipo");
        System.out.println("0 - Sair");
        System.out.print("Selecione uma opção: ");
    }

    public static void registerContracts(Scanner scanner, List<Contract> contractList) {
        int previousSize = contractList.size();
        try {
            System.out.print("Informe a quantidade de funcionários a serem cadastrados: ");
            int quantity = scanner.nextInt();
            System.out.println();

            for (int i = 0; i < quantity; i++) {
                System.out.println("Funcionário #" + (i + 1));
                System.out.print("Tipo de contrato (1-Efetivo, 2-Temporário, 3-Estágio): ");
                int optionContract = scanner.nextInt();

                while (optionContract < 1 || optionContract > 3) {
                    System.out.println("Entrada inválida. Insira uma opção correta.");
                    System.out.print("Tipo de contrato (1-Efetivo, 2-Temporário, 3-Estágio): ");
                    optionContract = scanner.nextInt();
                }

                scanner.nextLine();
                System.out.print("Nome do funcionário: ");
                String employeeName = scanner.nextLine();
                System.out.print("Data de início (dd/MM/yyyy): ");
                LocalDate startDate = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.print("Data de término (dd/MM/yyyy): ");
                LocalDate endDate = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.print("Salário mensal: ");
                double salary = scanner.nextDouble();

                if (optionContract == 1) {
                    contractList.add(new PermanentContract(employeeName, startDate, endDate, ContractType.EFETIVO, salary));
                }
                else if (optionContract == 2) {
                    contractList.add(new TemporaryContract(employeeName, startDate, endDate, ContractType.TEMPORARIO, salary));
                }
                else {
                    contractList.add(new InternshipContract(employeeName, startDate, endDate, ContractType.ESTAGIO, salary));
                }
                System.out.println();
            }
        }
        catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
        }
        catch (ContractException e) {
            System.out.print("Erro no contrato: " + e.getMessage());
        }
        catch (RuntimeException e) {
            System.out.println("Falha ao processar a operação. Verifique os dados e tente novamente.");
        }
        finally {

            for (int i = previousSize; i < contractList.size(); i++) {
                System.out.println("Contrato criado: \n" + contractList.get(i));
            }
        }
    }

    public static void searchModule(Scanner scanner, List<Contract> contractList) {
        List<Contract> typeContractList = null;
        int selectedContractOption = 0;
        if (contractList.isEmpty()) {
            System.out.println("Ainda não foi cadastrado nenhum contrato.");
            System.out.println();
            return;
        }

        try {
            System.out.print("Tipo de contrato (1-Efetivo, 2-Temporário, 3-Estágio): ");
            int optionContract = scanner.nextInt();
            selectedContractOption = optionContract;

            typeContractList = contractList.stream().filter(x -> x.getContractType().equals(ContractType.values()[optionContract - 1])).toList();

            if (typeContractList.isEmpty()) {
                System.out.println();
                System.out.println("Não foi localizado nenhum contrato do tipo " + ContractType.values()[selectedContractOption - 1] + ".");
            }

        } catch (RuntimeException e) {
            System.out.println("Falha ao processar a operação. Verifique os dados e tente novamente.");
        } finally {
            System.out.println();

            if (typeContractList != null) {
                System.out.println("Contratos do tipo: " + ContractType.values()[selectedContractOption - 1]);
                for (Contract c : typeContractList) {
                    System.out.println(c);
                }
            }
        }
    }
}