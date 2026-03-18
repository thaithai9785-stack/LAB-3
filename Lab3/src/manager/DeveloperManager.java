
package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import model.Developers;
import tools.Acceptable;
import tools.Inputter;

public class DeveloperManager extends ArrayList<Developers>{
    private Inputter inputter ;
    private String pathFile = "developers.txt";

    public DeveloperManager() {
    }

    
    public DeveloperManager(Inputter inputter) {
        super();
        this.inputter = inputter;
        this.readFromFile();
    }
    
    public void printHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-25s | %-30s | %-8s |\n", "Dev ID", "Full Name", "Languages", "Salary");
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    
    public void readFromFile() {
        this.clear();
        File f = new File(pathFile);
        if (!f.exists()) {
            System.err.println("Cảnh báo: Không tìm thấy file " + pathFile);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.startsWith("\uFEFF")) line = line.substring(1); 
                
                Developers d = textToDeveloper(line);
                if (d != null) this.add(d);
            }
            System.out.println("-> Loaded " + this.size() + " developers successfully!");
        } catch (Exception ex) {
            Logger.getLogger(DeveloperManager.class.getName()).log(Level.SEVERE, "Lỗi nạp file Developer", ex);
        }
    }

    private Developers textToDeveloper(String line) {
        try {
            int startBracket = line.indexOf('[');
            int endBracket = line.indexOf(']');

            if (startBracket != -1 && endBracket != -1) {
                String[] idAndName = line.substring(0, startBracket).split(",");
                String id = idAndName[0].trim();
                String name = idAndName[1].trim();
                String lang = line.substring(startBracket + 1, endBracket).trim();
                String salaryStr = line.substring(endBracket + 1).replace(",", "").trim();
                int salary = Integer.parseInt(salaryStr);

                return new Developers(id, name, lang, salary);
            }
        } catch (Exception e) {
            System.err.println("Bỏ qua dòng dữ liệu Developer lỗi: " + line);
        }
        return null;
    }

    public void saveToFile() {
        if (this.isEmpty()) {
            System.out.println("-> Kho Developer trống, không có gì để lưu.");
            return;
        }
        try (PrintWriter pw = new PrintWriter(new File(pathFile))) {
            for (Developers d : this) {
                pw.println(d.getDevID() + ", " + d.getDevName() + ", [" + d.getProgammingLanguage() + "], " + d.getSalary());
            }
            System.out.println("-> Saved developer data to file successfully!");
        } catch (Exception e) {
            System.out.println("-> Lỗi không thể lưu file: " + e.getMessage());
        }
    }
    
    
    //x? lí
    public Developers searchIDDev(String devID){
        for (Developers d : this) {
            if(d.getDevID().equalsIgnoreCase(devID.trim()))
                return d;
        }
        return null;
    } 
    
   //case 1
    public void ListDevelopers(){
       System.out.println("List all Developers");
       printHeader();
       if(this.isEmpty()){
           System.out.println("the list is empty");
           return;
       }
        for (Developers d : this) {
            System.out.println(d.toString());
        }
    }
    
    
    //case2
    public void addDev(){
         Developers d = inputter.getDeveloperInfo();
        if(searchIDDev(d.getDevID()) != null){
            System.out.println("Dev is already exist");
            return;
        }
        this.add(d);
        System.out.println("Add done");
        
    }
    
 //case 3
    public void searchDevByID(){
        String ID = inputter.getString("ID to search: ");
        Developers d = searchIDDev(ID);
        
        if (d == null) {
            System.out.println("Developer ID does not exits ");
            return;
        } else {
            System.out.println("Dev Found: ");
            printHeader();
            System.out.println(d.toString());
        }
    }
    
     //case 4
    public void updateSalaryByID(){
        String ID = inputter.getString("ID to search: ");
        Developers d = searchIDDev(ID);
        
        if (d == null) {
            System.out.println("Developer ID does not exits ");
            return;
        }
        
        int salary = Integer.parseInt(inputter.inputAndLoop("salary", Acceptable.SALARY_VALID, true));
        d.setSalary(salary);
        System.out.println("update thanh cong");
    }
    
    //case5: can test lai

    public ArrayList<Developers> searchDevByLanguage1(String searchLang) {
        ArrayList<Developers> resultList = new ArrayList<>();
        
        for (Developers d : this) {
            // Lấy chuỗi ngôn ngữ gốc (ví dụ: "Java, C++, JavaScript")
            String fullLangs = d.getProgammingLanguage();
            
            // Băm nhỏ chuỗi thành một mảng các từ, cắt bằng dấu phẩy
            String[] langArray = fullLangs.split(",");
            
            // Duyệt qua từng từ trong mảng
            for (String lang : langArray) {
                // Trim() để xóa khoảng trắng dư thừa (ví dụ " C++" thành "C++")
                // Rồi dùng equalsIgnoreCase để so sánh TẤT TAY với từ khóa
                if (lang.trim().equalsIgnoreCase(searchLang.trim())) {
                    resultList.add(d);
                    break; // Tìm thấy rồi thì dừng vòng lặp mảng này, qua xét người tiếp theo luôn
                }
            }
        }
        return resultList;
    }
    
    
    public ArrayList<Developers> searhLang(String lang){
     ArrayList<Developers> result = new ArrayList<>();
        for (Developers d : this) {
            if(d.getProgammingLanguage().toLowerCase().contains(lang.toLowerCase()))
                result.add(d);
        }
        return result;
    }
    
    
    public void ListDevByLanguage(){
       String lang = inputter.getString("Lang");
       ArrayList<Developers> result = searhLang(lang);
       if(result.isEmpty()){
           System.out.println("ko co ngon ngu");
           return;
       }
       
        for (Developers d : result) {
            System.out.println(d.toString());
        }
       
       
    }
    
    
   //case 10
    public void sortDevBySalary(){
       if (this.isEmpty()) {
            System.out.println("-> Kho Developer trống, không có gì để sắp xếp.");
            return;
        }

        this.sort((d1, d2) -> Integer.compare(d2.getSalary(), d1.getSalary()));
        
        this.sort((d1,d2)-> Integer.compare(d1.getSalary(), d2.getSalary()));

        System.out.println("-> Đã sắp xếp danh sách Developer theo lương tang dần!");
        
        this.ListDevelopers();
    }

    
    
}
