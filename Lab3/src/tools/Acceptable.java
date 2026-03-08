
package tools;

public interface Acceptable {
    public final String DEV_ID_VALID = "^DEV\\d{3}$";
    public final String NAME_VALID = "^\\S+(\\s+\\S+)+$";
    public final String LANGUAGE_VALID = "^.*\\S+.*$";
    public final String SALARY_VALID = "^[1-9]\\d{3,}$";
    
    public final String PROJECTID_VALID = "^.*\\S+.*$";
    public final String PROJECT_NAME= "^.*\\S+.*$";
    public final String DURATION_VALID = "^[1-9]\\d*$";
    public final String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";
 
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
