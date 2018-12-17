package lk.ijse.payrollsystem.validation;

public class Validator {
    public static boolean characterValidation(String name){
        if(name.trim().isEmpty()){
            return false;
        }
        if(!name.matches("[A-z .]+")){
            return false;
        }
        return true;
    }
    public static boolean nicValidation(String nic){
        if(nic.trim().isEmpty()){
            return false;
        }
        if(!nic.matches("[0-9]{9}[Vv]")){
            return false;
        }
        return true;
    }
    public static boolean yearValidation(String nic){
        if(nic.trim().isEmpty()){
            return false;
        }
        if(!nic.matches("[0-9]{4}")){
            return false;
        }
        return true;
    }
    public static boolean doubleValueValidation(String value){
        if(value.trim().isEmpty()){
            return false;
        }
        if(!value.matches("\\d+(\\.\\d{2}|\\.\\d{1})?")){
            return false;
        }
        return true;
    }
    public static boolean intValueValidation(String value){
        if(value.trim().isEmpty()){
            return false;
        }
        if(!value.matches("[0-9]+")){
            return false;
        }
        return true;
    }
    public static boolean employeeIdValidation(String id){
        if(id.trim().isEmpty()){
            return false;
        }
        if(!id.matches("[e]+[0-9]{3}")){
            return false;
        }
        return true;
    }
    public static boolean designationIdValidation(String id){
        if(id.trim().isEmpty()){
            return false;
        }
        if(!id.matches("[d]+[0-9]{3}")){
            return false;
        }
        return true;
    }
}
