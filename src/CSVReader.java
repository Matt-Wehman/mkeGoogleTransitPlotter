public class CSVReader {
    String line;

    CSVReader(String line){
        this.line = line;
    }

    public String next() throws EndOfStringException {
        if (line.length() == 0){
            throw new EndOfStringException("There is no more text to read");
        }
        String ret = line.substring(0, line.indexOf(','));
        line = line.substring(line.indexOf(',')+1);
        return ret;
    }

    public int nextInt() throws EndOfStringException, NumberFormatException {
        return Integer.parseInt(next());
    }

    public class EndOfStringException extends Exception{
        public EndOfStringException(String errorMessage){
            super(errorMessage);
        }
    }
}
