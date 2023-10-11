package sde.virginia.edu.hw3;

public class ApportionmentMethodFactory {
    public ApportionmentMethod getDefaultMethod(){
        return new HuntingtonHillMethod();
    }
    public ApportionmentMethod getDefaultMethod(String method){
        switch (method.toLowerCase()) {
            case "adams" -> {
                return new AdamsMethod();
            }
            case "jefferson" -> {
                return new JeffersonMethod();
            }
            case "huntington" -> {
                return new HuntingtonHillMethod();
            }
        }
        throw new IllegalArgumentException("Please choose one of three valid apportionment methods (adams, jefferson, huntington");
        }
    }

