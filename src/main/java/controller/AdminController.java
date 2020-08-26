package controller;


import com.google.gson.Gson;
import model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AdminService;

import java.util.List;

@RestController
@CrossOrigin //enable CORS
@RequestMapping(path = "/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //get list of admin
    @RequestMapping(path = "admin", method = RequestMethod.GET)
    public List<Admin> getAllAdmin(){
        return adminService.getAllAdmin();
    }


    //add new admin path
    @RequestMapping(path = "post/admin", method =  RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addAdmin (@RequestBody Admin admin){
        String result = "";
        Gson g = new Gson();
        HttpStatus httpStatus;
        try {
            if (!adminService.checkUsername(admin) && !adminService.checkPassword(admin)) {
                result = "Create account successfully!";
                httpStatus = HttpStatus.OK;
                adminService.saveAdmin(admin);
            }
            else{
                result = "Username or password is invalid!";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception ex){
            result = "Server error!";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return  new ResponseEntity<>(g.toJson(result), httpStatus);
    }

    @RequestMapping(path = "login/admin", method =  RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addAdmin (@RequestBody Admin admin){
        String result = "";
        Gson g = new Gson();
        HttpStatus httpStatus;
        try {
            if (!adminService.checkUsername(admin) && !adminService.checkPassword(admin)) {
                result = "Login successfully!";
                httpStatus = HttpStatus.OK;
            }
            else{
                result = "Username or password is invalid!";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception ex){
            result = "Server error!";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return  new ResponseEntity<>(g.toJson(result), httpStatus);
    }

    //get admin by name path
    @RequestMapping(path = "admin/{name}", method = RequestMethod.GET)
    public void findAdmin(@PathVariable String name) {
        adminService.findAdminByName(name);
    }

    //update path
    @RequestMapping(path = "admin", method = RequestMethod.PUT)
    public void updateAdmin(@RequestBody Admin admin){
        adminService.updateAdmin(admin);
    }

    //delete admin path
    @RequestMapping(path = "admin/{id}", method = RequestMethod.DELETE)
    public void deleteAdmin (@PathVariable int id){
        adminService.deleteAdmin(id);
    }
}