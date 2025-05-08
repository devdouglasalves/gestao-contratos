
# Sistema de Gerenciamento de Contratos de Trabalho

Este é um sistema em Java para gerenciamento de contratos de trabalho. Ele permite cadastrar, validar, listar e exportar contratos do tipo Efetivo, Temporário e Estágio. Cada tipo de contrato possui suas próprias regras de validação e cálculo de salário total.

## Funcionalidades

- Cadastro de contratos com nome, tipo, data de início, data de término e salário.
- Validação automática conforme o tipo de contrato.
- Cálculo de duração do contrato em meses.
- Cálculo do valor total a receber, com bônus aplicado quando aplicável.
- Listagem de contratos filtrando por tipo.
- Exportação de contratos para arquivo .csv ou .txt.

## Regras de validação

- Todos os contratos devem ter data de início igual ou posterior à data atual.
- A data de término deve ser posterior à data de início.
- O salário deve ser maior que zero.
- Contrato Efetivo: sem restrição de duração.
- Contrato Temporário: duração máxima de 24 meses.
- Contrato de Estágio: duração máxima de 12 meses.

## Cálculo do valor total do contrato

- Para contratos com duração inferior a 12 meses: salário mensal * número de meses.
- Para contratos com duração igual ou superior a 12 meses: (salário mensal + bônus) * número de meses.
- O bônus aplicado é de R$ 500,00 por mês.

## Exportação

- Os contratos podem ser exportados para um arquivo chamado `contracts.csv` ou `contracts.txt`, salvo no diretório `/contratosDeTrabalhos/data`.
- O formato do arquivo exportado é: `Nome,Tipo,Data inicio,Data fim,Salario`

## Exemplo do menu principal do programa

```
 ___________________________
|     MENU DE CONTRATOS     |
 ___________________________
1 - Cadastrar Contratos
2 - Listar Contratos por Tipo
3 - Exportar contratos
0 - Sair
```

## Tecnologias utilizadas

- Java 17 ou superior
- Programação orientada a objetos (POO)
- Manipulação de datas com `java.time` (`LocalDate`, `DateTimeFormatter`, `ChronoUnit`)
- Leitura e escrita de arquivos com `File` e `BufferedWriter`
- Uso de `Listas` e `Streams`

## Para executar o programa

1. Compile e execute a classe `Program.java`.
2. Siga as instruções do menu.
3. Cadastre contratos, liste por tipo e exporte para arquivo quando desejar.
