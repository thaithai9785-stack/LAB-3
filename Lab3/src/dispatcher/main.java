package dispatcher;

import manager.DeveloperManager;
import manager.ProjectManager;
import tools.Inputter;

public class main {

    public static void main(String[] args) {
        Inputter inputter = new Inputter();
        DeveloperManager devManager = new DeveloperManager(inputter);
        ProjectManager projManager = new ProjectManager(inputter, devManager);
           int choice = 0 ;

        do {
 
            System.out.println("\n=======================================================");
            System.out.println("====== DEVELOPER & PROJECT MANAGEMENT SYSTEM ======");
            System.out.println("=======================================================");
            System.out.println("1.  List all Developers");
            System.out.println("2.  Add a new Developer");
            System.out.println("3.  Search Developer by ID");
            System.out.println("4.  Update Developer Salary");
            System.out.println("5.  List Developers by Language");
            System.out.println("6.  Add a new Project");
            System.out.println("7.  List all Projects by Developer (Grouped)");
            System.out.println("8.  Calculate Total Experience of a Developer");
            System.out.println("9.  Delete a Developer");
            System.out.println("10. Sort Developers by Salary");
            System.out.println("11. Save data to file");
            System.out.println("12. Quit");

            try {
                choice = Integer.parseInt(inputter.getString("Please choose an option (1-12): ").trim());
            } catch (Exception e) {
                System.out.println("-> Lỗi: Vui lòng nhập số từ 1 đến 12!");
                continue; 
            }

            switch (choice) {
                case 1:
                    devManager.ListDevelopers(); 
                    break;
                case 2:
                    devManager.addDev();
                    break;
                case 3:
                    devManager.searchDevByID();
                    break;
                case 4:
                    devManager.updateSalaryByID();
                    break;
                case 5:
                    devManager.ListDevByLanguage();
                    break;
                case 6:
                    projManager.addProject(); 
                    break;
                case 7:
                    projManager.listAllProjects(); 
                    break;
                case 8:
                    projManager.totalExperience(); 
                    break;
                case 9:
                    projManager.deleteDeveloper(); 
                    break;
                case 10:
                    devManager.sortDevBySalary();
                    break;
                case 11:
                    System.out.println("\n--- SAVING DATA... ---");
                    devManager.saveToFile();
                    projManager.saveToFile();
                    break;
                case 12:
                    System.out.println("\n--- THOÁT CHƯƠNG TRÌNH ---");
                    String confirm = inputter.getString("Bạn có muốn Lưu dữ liệu trước khi thoát không? (Y/N): ");
                    if (confirm.equalsIgnoreCase("Y")) {
                        devManager.saveToFile();
                        projManager.saveToFile();
                    }
                    System.out.println("-> Tạm biệt! Hẹn gặp lại.");
                    break;
                default:
                    System.out.println("-> Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 12.");
            }
        } while (choice != 12);
        
        
    }

}
