import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CSVReader {
    String line;
    boolean hasNext = true;

    CSVReader(String line){
        this.line = line;
    }

    public String next() throws EndOfStringException {
        if (!hasNext){
            throw new EndOfStringException("There is no more text to read");
        }
        String ret;
        if (line.contains(",")){
            ret = line.substring(0, line.indexOf(','));
            line = line.substring(line.indexOf(',')+1);
        } else {
            ret = "";
            hasNext = false;
        }
        return ret;
    }

    public int nextInt() throws EndOfStringException, NumberFormatException {
        String ret = next();
        return ret.length() > 0 ? Integer.parseInt(ret): -1;
    }

    public Time nextTime() throws EndOfStringException, ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return new Time(formatter.parse(next()).getTime());
    }

    public long nextLong() throws EndOfStringException {
        String ret = next();
        return ret.length() > 0 ? Long.parseLong(ret): -1;
    }

    public double nextDouble() throws EndOfStringException {
        String ret = next();
        return ret.length() > 0 ? Double.parseDouble(ret): -1;
    }

    public class EndOfStringException extends Exception{
        public EndOfStringException(String errorMessage){
            super(errorMessage);
        }
    }
}
