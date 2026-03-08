
package tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.Developers;
import model.Projects;

public class Inputter {
    private Scanner ndl ;

    public Inputter() {
        this.ndl= new Scanner(System.in);
    }

    public Inputter(Scanner ndl) {
        this.ndl = ndl;
    }

    public Scanner getNdl() {
        return ndl;
    }

    public void setNdl(Scanner ndl) {
        this.ndl = ndl;
    }
    
    public String getString(String mess) {
        System.out.println(mess);
        return this.ndl.nextLine();
    }

    public String inputAndLoop(String mess, String pattern, boolean isLoop) {
        boolean more = true;
        String result = "";
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);

            if (more) {
                System.out.println("Data is incorrect");
            }
        } while (more && isLoop);
        return result;
    }

    public String inputFutureDate(String mess) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            String dateStr = getString(mess);
            if (!Acceptable.isValid(dateStr, Acceptable.DATE_VALID)) {
                System.out.println("Invalid format (dd/MM/yyyy)");
                continue;
            }

            //check ngày
            try {
                LocalDate inputDate = LocalDate.parse(dateStr, dtf);

                if (inputDate.isAfter(LocalDate.now())) {
                    return dateStr;
                } else {
                    System.out.println("Date must be on future");
                }
            } catch (Exception e) {
                System.out.println("ngày tháng ko t?n t?i trên h? th?ng");
            }

        }
    }
    
    public Developers getDeveloperInfo(){
        Developers d = new Developers();

        d.setDevID(inputAndLoop("Input Dev ID (ex: DEV001): ", Acceptable.DEV_ID_VALID, true));
        d.setDevName(inputAndLoop("Input Full Name (>= 2 words): ", Acceptable.NAME_VALID, true));
        d.setProgammingLanguage(inputAndLoop("Input Programming Languages: ", Acceptable.LANGUAGE_VALID, true));
        d.setSalary(Integer.parseInt(inputAndLoop("Input Salary (USD, >= 1000): ", Acceptable.SALARY_VALID, true)));
        return d;
    }

        public Projects getProjectInfo(String DevID) {
        Projects p = new Projects();
        
        p.setProjectID(inputAndLoop("Input Project ID: ", Acceptable.PROJECTID_VALID, true));
        p.setDevID(DevID);
        p.setProjectName(inputAndLoop("Input Project Name: ", Acceptable.PROJECT_NAME, true));
        p.setDuration_Month(Integer.parseInt(inputAndLoop("Input Duration (months, >= 1): ", Acceptable.DURATION_VALID, true)));
        p.setStart_date(inputFutureDate("Input Start Date (dd/MM/yyyy): "));
        return p;
    }
    
}
