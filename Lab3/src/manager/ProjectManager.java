package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Developers;
import model.Projects;
import tools.Acceptable;
import tools.Inputter;

public class ProjectManager extends ArrayList<Projects> {

    private String pathFile = "projects.txt";
    private Inputter inputter;
    private DeveloperManager devManager;

    public ProjectManager() {
    }

    public ProjectManager(Inputter inputter, DeveloperManager devManager) {
        super();
        this.inputter = inputter;
        this.readFromFile();
        this.devManager = devManager;
    }

    private void printProjectHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-25s | %-10s | %-12s |\n", "Project ID", "Dev ID", "Project Name", "Duration", "Start Date");
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    public void readFromFile() {
        this.clear();
        File f = new File(pathFile);
        if (!f.exists()) {
            System.err.println("Cảnh báo: Không tìm thấy file " + pathFile);
            return;
        }
        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1); // Giữ nguyên pha lọc BOM cực xịn của bạn
                }
                Projects p = textToProject(line);
                if (p != null) {
                    this.add(p);
                }
            }
            System.out.println("-> Loaded " + this.size() + " projects successfully!");
        } catch (Exception ex) {
            Logger.getLogger(ProjectManager.class.getName()).log(Level.SEVERE, "Lỗi nạp file Project", ex);
        }
    }
    
    private Projects textToProject(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String proID = parts[0].trim();
                String devID = parts[1].trim();
                String name = parts[2].trim();
                String durationStr = parts[3].trim();
                String dateStr = parts[4].trim();
                // 1 check duration
                if (!Acceptable.isValid(durationStr, Acceptable.DURATION_VALID)) {
                    System.out.println("-> CẢNH BÁO: Bỏ qua Project [" + proID + "] do Duration <1 month: (" + durationStr + " month)");
                    return null;
                }
                // 2 check date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDate = LocalDate.parse(dateStr, dtf);
                if (!startDate.isAfter(LocalDate.now())) {
                    System.out.println("-> CẢNH BÁO: Bỏ qua Project [" + proID + "] do ngày nằm trong quá khứ (" + dateStr + ")");
                    return null;
                }
                // Nếu vượt qua hết bẫy thì mới tạo Object
                return new Projects(
                        proID,
                        devID,
                        name,
                        Integer.parseInt(durationStr),
                        dateStr
                );
            }
        } catch (Exception e) {
            System.out.println("Bỏ qua dòng dữ liệu Project lỗi: " + line);
        }
        return null;
    }

    private Projects textToProject1(String line) {
        try {
            String[] parts = line.split(",");
            // Cắt 1 nhát ra 5 khúc, siêu đơn giản!
            if (parts.length >= 5) {
                return new Projects(
                        parts[0].trim(), // projectID
                        parts[1].trim(), // devID
                        parts[2].trim(), // projectName
                        Integer.parseInt(parts[3].trim()), // duration (ép sang số)
                        parts[4].trim() // startDate
                );
            }
        } catch (Exception e) {
            System.err.println("Bỏ qua dòng dữ liệu Project lỗi: " + line);
        }
        return null;
    }

    public void saveToFile() {
        if (this.isEmpty()) {
            System.out.println("-> Kho Project trống, không có gì để lưu.");
            return;
        }
        try ( PrintWriter pw = new PrintWriter(new File(pathFile))) {
            for (Projects p : this) {
                pw.println(p.getProjectID() + ", " + p.getDevID() + ", "
                        + p.getProjectName() + ", " + p.getDuration_Month() + ", " + p.getStart_date());
            }
            System.out.println("-> Saved project data to file successfully!");
        } catch (Exception e) {
            System.out.println("-> Lỗi không thể lưu file: " + e.getMessage());
        }
    }
    
    
//xử lí
 public Projects searchProjectById(String proID){
        for (Projects p : this) {
            if(p.getProjectID().trim().equalsIgnoreCase(proID.trim()))
                return p;
        }
        return null;
    }

    //case 6
    public void addProject() {
        devManager.ListDevelopers();
        String id = inputter.getString("Enter Dev ID to assign this project: ");
        
        while (true) {
            if (devManager.searchIDDev(id) == null) {
                System.out.println("Developer ID does not exist! Please try again");
                return;
            }
            else
                break;
        }
        
        Projects p = inputter.getProjectInfo(id);
        
        if(searchProjectById(p.getProjectID()) != null){
            System.out.println("Project ID already exists");
            return;
        }
        
        this.add(p);
        System.out.println("Add project successfully");
        
    }
    
    //case 7
    public void listAllProjects() {
        System.out.println("\n--- LIST OF ALL PROJECTS ---");
        if (this.isEmpty()) {
            System.out.println("-> The Project list is empty!");
            return;
        }

        for (Developers d : this.devManager) {
            boolean hasProject = false;
            System.out.println("\nDev: " + d.getDevName());

            for (Projects p : this) {
                if (p.getDevID().trim().equalsIgnoreCase(d.getDevID().trim())) {

                    System.out.printf("\tProject ID: %-8s | Duration: %-2d month | Start Date: %-10s\n",p.getProjectID(), p.getDuration_Month(), p.getStart_date());
                    hasProject = true;
                }
            }

            if (!hasProject) {
                System.out.println("Dev don't have Project");
            }

        }

    }
    
    
    //case 8
    public void totalExperience(){
       String id = inputter.getString("id");
       Developers d = devManager.searchIDDev(id);
       if(d==null){
           System.out.println("ko tim thay dev");
           return;
       }
       int total=0;
        for (Projects p : this) {
            if(p.getDevID().trim().equalsIgnoreCase(id.trim())){
                total +=p.getDuration_Month();
            }
        }
        System.out.println("kinh nghiem: "+total);
    }
    
    
    
    //case9
    public boolean inProject(String devID) {
    return true;
    }
    
    public void deleteDeveloper(){
      
    }

}
