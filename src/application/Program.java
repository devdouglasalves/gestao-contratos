package application;

import model.entities.Contract;
import model.entities.InternshipContract;
import model.entities.PermanentContract;
import model.entities.TemporaryContract;
import model.enums.ContractType;
import model.exceptions.ContractException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        List<Contract> contractList = new ArrayList<>();

        int option = 0;

        do {
            try {
                menu();
                option = scanner.nextInt();
                System.out.println();

                switch (option) {
                    case 1 -> registerContracts(scanner, contractList);
                    case 2 -> searchModule(scanner, contractList);
                    case 3 -> exportContracts(scanner, contractList);
                    case 0 -> System.out.println("Encerrando o programa...");
                    default -> System.out.println("Opção inválida, tente outra.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                scanner.nextLine();
            }
        } while (option != 0);
        scanner.close();
    }

    // ================== MÉTODOS DE MENU ==================
    public static void menu() {
        System.out.println(" ___________________________ ");
        System.out.println("|     MENU DE CONTRATOS     |");
        System.out.println(" ___________________________ ");
        System.out.println("1 - Cadastrar Contratos");
        System.out.println("2 - Listar Contratos por Tipo");
        System.out.println("3 - Exportar contratos");
        System.out.println("0 - Sair");
        System.out.print("Selecione uma opção: ");
    }

    // ================== REGISTRO DE CONTRATOS ==================
    public static void registerContracts(Scanner scanner, List<Contract> contractList) {
        int previousSize = contractList.size();

        try {
            System.out.print("Informe a quantidade de funcionários a serem cadastrados: ");
            int quantity = scanner.nextInt();
            System.out.println();

            for (int i = 0; i < quantity; i++) {

                // Solicita ao usuário que informe o tipo de contrato desejado (1 - Efetivo, 2 - Temporário, 3 - Estágio)
                int optionContract = inputContractType(scanner);

                // Coleta os dados do funcionário e instancia o tipo de contrato adequado (Efetivo, Temporário ou Estágio)
                Contract contract = inputEmployeeData(scanner, optionContract);

                System.out.println();
                // Adiciona na lista os contratos.
                contractList.add(contract);
            }
        } catch (ContractException e) {
            System.out.println("Erro no contrato: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Digite um número inteiro.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Falha ao processar a operação. Verifique os dados e tente novamente.");
        }

        // exibição dos novos contratos.
        for (int i = previousSize; i < contractList.size(); i++) {
            System.out.println("Contrato criado: \n" + contractList.get(i));
        }
    }

    // ================== BUSCA E FILTRO ==================
    public static void searchModule(Scanner scanner, List<Contract> contractList) {

        // Verifica se a lista está vazia
        if (isListEmpty(contractList, "contratos")) return;

        List<Contract> typeContractList = null;
        int selectedContractOption = 0;

        try {
            int optionContract = inputContractType(scanner);
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

    // ================== EXPORTAÇÃO DE DADOS ==================
    public static void exportContracts(Scanner scanner, List<Contract> contractList) {

        // Verifica se a lista está vazia
        if (isListEmpty(contractList, "contratos")) return;

        // Cria um arquivo CSV ou TXT
        File contractsExported = generateExportFile(scanner);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(contractsExported))) {

            bw.write(Contract.titleCsv());
            bw.newLine();

            for (Contract obj : contractList) {
                bw.write(obj.exportCsv());
                bw.newLine();
            }

            System.out.println();
            System.out.println("Contratos exportados com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }

    // Criar arquivo CSV ou TXT
    public static File generateExportFile(Scanner scanner) {

        System.out.print("Deseja exportar para formato txt ou csv: ");
        String formatFile = scanner.next().trim();

        while (!formatFile.equals("txt") && !formatFile.equals("csv")) {
            System.out.println();
            System.out.println("Formato não permitido!");
            System.out.print("Deseja exportar para formato txt ou csv: ");
            formatFile = scanner.next().trim();
        }

        String strFolders = (System.getProperty("user.dir") + File.separator + "contratosDeTrabalhos");
        File folders = new File(strFolders + "/data");
        boolean created = folders.mkdir();

        return new File(folders.getPath() + "/contracts." + formatFile);
    }

    // ================== ENTRADAS E VALIDAÇÕES ==================

    // Tipo do contrato
    public static int inputContractType(Scanner scanner) {
        System.out.print("Tipo de contrato (1-Efetivo, 2-Temporário, 3-Estágio): ");
        int optionContract = scanner.nextInt();

        while (optionContract < 1 || optionContract > 3) {
            System.out.println("Entrada inválida. Insira uma opção correta.");
            System.out.print("Tipo de contrato (1-Efetivo, 2-Temporário, 3-Estágio): ");
            optionContract = scanner.nextInt();
        }
        return optionContract;
    }

    // Dados do funcionário
    public static Contract inputEmployeeData(Scanner scanner, int contractType) {
        scanner.nextLine();

        System.out.print("Nome do funcionário: ");
        String employeeName = scanner.nextLine();

        System.out.print("Data de início (dd/MM/yyyy): ");
        LocalDate startDate = inputValidDate(scanner);

        System.out.print("Data de término (dd/MM/yyyy): ");
        LocalDate endDate = inputValidDate(scanner);

        System.out.print("Salário mensal: ");
        double salary = scanner.nextDouble();

        return contractValidationCreation(employeeName, startDate, endDate, salary, contractType);
    }

    // Entrada, formatação e validação de datas
    public static LocalDate inputValidDate(Scanner scanner) {
        LocalDate date = null;

        while (date == null) {
            try {
                String dates = scanner.next();
                date = LocalDate.parse(dates, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
            }
        }
        return date;
    }

    // Verificar se a lista está vazia
    public static boolean isListEmpty(List<Contract> list, String listName) {
        if (list.isEmpty()) {
            System.out.println("Ainda não há " + listName + " cadastrados.");
            System.out.println();
            return true;
        }
        return false;
    }

    // ================== CRIAÇÃO DE CONTRATOS ==================
    public static Contract contractValidationCreation(String employeeName, LocalDate startDate, LocalDate endDate, double salary, int contractType) {
        return switch (contractType) {
            case 1 -> new PermanentContract(employeeName, startDate, endDate, ContractType.EFETIVO, salary);
            case 2 -> new TemporaryContract(employeeName, startDate, endDate, ContractType.TEMPORARIO, salary);
            case 3 -> new InternshipContract(employeeName, startDate, endDate, ContractType.ESTAGIO, salary);
            default -> throw new IllegalArgumentException("Tipo de contrato inválido.");
        };
    }
}