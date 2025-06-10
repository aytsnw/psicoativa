package com.psicoativa.util;

public class CpfValidator implements CredentialValidator{
    @Override
    public boolean validate(String cpf){
        StringBuilder sb = new StringBuilder(cpf);
        sb.delete(9, 11);
        String validationCpf = sb.toString();

        int firstDigit = 0;
        int secondDigit = 0;

        int sum = 0;
        for (int i = 0; i < validationCpf.length(); i++){
            sum = sum + Character.getNumericValue(validationCpf.charAt(i)) * (10 - i);
        }

        int remainder = sum % 11;
        if (remainder < 2) firstDigit = 0;
        else firstDigit = 11 - remainder;

        validationCpf += firstDigit;
        
        sum = 0;
        for (int i = 0; i < validationCpf.length(); i++){
            sum = sum + Character.getNumericValue(validationCpf.charAt(i)) * (11 - i);
        }

        remainder = sum % 11;

        if (remainder < 2) secondDigit = 0;
        else secondDigit = 11 - remainder;

        validationCpf += secondDigit;

        return validationCpf.equals(cpf);
    }
    
}
