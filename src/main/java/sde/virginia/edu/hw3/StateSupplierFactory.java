package sde.virginia.edu.hw3;

public class StateSupplierFactory {
    public StateSupplier getStateSupplier(String filename){
        if (filename.endsWith(".csv")){
            return new CSVStateReader(filename);
        }
        if (filename.endsWith(".xlsx")) {
            return new SpreadsheetStateReader(filename);
        }
        throw new UnsupportedFileFormatException(filename);
    }

}
